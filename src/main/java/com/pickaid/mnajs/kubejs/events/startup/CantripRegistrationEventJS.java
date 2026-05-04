package com.pickaid.mnajs.kubejs.events.startup;

import com.mna.api.cantrips.ICantrip;
import com.mna.api.capabilities.IPlayerCantrip;
import com.mna.api.capabilities.IPlayerCantrips;
import com.mna.api.capabilities.IPlayerMagic;
import com.mna.api.sound.SFX;
import com.mna.api.timing.DelayedEventQueue;
import com.mna.api.timing.TimedDelayedEvent;
import com.mna.cantrips.CantripRegistry;
import com.pickaid.mnajs.MnaJS;
import com.pickaid.mnajs.kubejs.id.MnaAdvancementId;
import com.pickaid.mnajs.kubejs.id.MnaCantripId;
import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.kubejs.id.MnaManaweavePatternId;
import com.pickaid.mnajs.kubejs.id.MnaSoundId;
import com.pickaid.mnajs.kubejs.id.MnaTypedIdLookups;
import com.pickaid.mnajs.kubejs.texture.MnaTexture;
import com.pickaid.mnajs.util.PlayerUtil;
import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.Triple;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

public class CantripRegistrationEventJS extends EventJS {
    private static final Map<ResourceLocation, Function<Player, ItemStack>> DYNAMIC_ITEM_PROVIDERS = new HashMap<>();

    @Info(value = "Create a cantrip builder for the provided id.", params = {
            @Param(name = "id", value = "Cantrip id such as kubejs:flare_orb.")
    })
    public CantripBuilder create(MnaCantripId id) {
        return new CantripBuilder(id.location());
    }

    public static class CantripBuilder {
        private final ResourceLocation id;
        private int tier = 1;
        private int delay = 60;
        private ResourceLocation requiredAdvancement = null;
        private SoundEvent soundEffect = SFX.Event.Player.MANAWEAVE_PATTERN_MATCH;
        private ResourceLocation icon;
        private List<ResourceLocation> pattern = new ArrayList<>();
        private ItemStack spellStack = ItemStack.EMPTY;
        private Function<Player, ItemStack> dynamicItemProvider = null;
        private EffectorHandler effectorHandler = null;

        public CantripBuilder(ResourceLocation id) {
            this.id = id;
        }

        @Info(value = "Set the cantrip tier shown by Mana and Artifice.", params = {
                @Param(name = "tier", value = "Numeric progression tier for this cantrip.")
        })
        public CantripBuilder tier(int tier) {
            this.tier = tier;
            return this;
        }

        @Info(value = "Set the delay in ticks before the cantrip effect resolves.", params = {
                @Param(name = "delay", value = "Tick delay before the cantrip fires.")
        })
        public CantripBuilder delay(int delay) {
            this.delay = delay;
            return this;
        }

        @Info(value = "Set the advancement required before this cantrip can be used.", params = {
                @Param(name = "advancement", value = "Advancement id such as mna:tier_1/cast_flare_cantrip.")
        })
        public CantripBuilder requiredAdvancement(MnaAdvancementId advancement) {
            this.requiredAdvancement = advancement.location();
            return this;
        }

        @Info(value = "Set the sound played by this cantrip.", params = {
                @Param(name = "sound", value = "Sound id such as mna:cast_arcane.")
        })
        public CantripBuilder sound(MnaSoundId sound) {
            this.soundEffect = MnaTypedIdLookups.requireSound(sound, "sound");
            return this;
        }

        @Info(value = "Set the icon texture used by this cantrip.", params = {
                @Param(name = "icon", value = "Texture id such as mna:textures/gui/guide_book.png.")
        })
        public CantripBuilder icon(MnaTexture icon) {
            this.icon = icon.location();
            return this;
        }

        @Info(value = "Append one manaweave pattern to this cantrip.", params = {
                @Param(name = "pattern", value = "Manaweave pattern recipe id such as built-in mna:manaweave_patterns/circle or custom kubejs:circle.")
        })
        public CantripBuilder addPattern(MnaManaweavePatternId pattern) {
            this.pattern.add(pattern.recipeLocation());
            return this;
        }

        @Info(value = "Replace the full manaweave pattern list for this cantrip.", params = {
                @Param(name = "patterns", value = "One or more manaweave pattern ids in the order MNA expects them.")
        })
        public CantripBuilder pattern(MnaManaweavePatternId... patterns) {
            this.pattern = Arrays.stream(patterns)
                    .map(MnaManaweavePatternId::recipeLocation)
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
            return this;
        }

        @Info(value = "Set the spell item displayed or granted by this cantrip.", params = {
                @Param(name = "spellStack", value = "Item id used as the cantrip's spell stack.")
        })
        public CantripBuilder spellStack(MnaItemId spellStack) {
            this.spellStack = MnaTypedIdLookups.stack(spellStack, "spellStack");
            return this;
        }

        @Info(value = "Set a fixed dynamic item for this cantrip.", params = {
                @Param(name = "item", value = "Item id returned by the cantrip's dynamic item provider.")
        })
        public CantripBuilder dynamicItem(MnaItemId item) {
            Item resolved = MnaTypedIdLookups.requireItem(item, "dynamicItem");
            this.dynamicItemProvider = player -> new ItemStack(resolved);
            return this;
        }

        @Info("Clear the dynamic item provider so this cantrip no longer exposes a dynamic item.")
        public CantripBuilder clearDynamicItem() {
            this.dynamicItemProvider = null;
            return this;
        }

        @Info(value = "Set a fully custom dynamic item provider.", params = {
                @Param(name = "provider", value = "Callback returning the ItemStack shown for this cantrip.")
        })
        public CantripBuilder dynamicItemProvider(Function<Player, ItemStack> provider) {
            this.dynamicItemProvider = provider;
            return this;
        }

        @Info(value = "Set an immediate cantrip effect callback.", params = {
                @Param(name = "effector", value = "Callback executed as soon as the cantrip resolves.")
        })
        public CantripBuilder effect(CantripEffector effector) {
            this.effectorHandler = new EffectorHandler(effector, null, false);
            return this;
        }

        @Info(value = "Set a delayed cantrip effect callback.", params = {
                @Param(name = "delayedEffector", value = "Callback executed by the delayed event queue after the configured delay.")
        })
        public CantripBuilder delayedEffect(DelayedCantripEffector delayedEffector) {
            this.effectorHandler = new EffectorHandler(null, delayedEffector, false);
            return this;
        }

        @Info(value = "Use one of Mana and Artifice's built-in cantrip effects.", params = {
                @Param(name = "name", value = "Built-in name such as firework, gust, ascend, dispel, drought, ward, reveal_ward, summon_grimoire, summon_faction_grimoire, or apply_spell.")
        })
        public CantripBuilder builtInEffect(String name) {
            switch (name.toLowerCase()) {
                case "firework":
                    this.effectorHandler = new EffectorHandler(CantripRegistry::firework, null, true);
                    break;
                case "gust":
                    this.effectorHandler = new EffectorHandler(CantripRegistry::gust, null, true);
                    break;
                case "ascend":
                    this.effectorHandler = new EffectorHandler(CantripRegistry::ascend, null, true);
                    break;
                case "dispel":
                    this.effectorHandler = new EffectorHandler(CantripRegistry::dispel, null, true);
                    break;
                case "drought":
                    this.effectorHandler = new EffectorHandler(CantripRegistry::drought, null, true);
                    break;
                case "ward":
                    this.effectorHandler = new EffectorHandler(CantripRegistry::ward, null, true);
                    break;
                case "reveal_ward":
                    this.effectorHandler = new EffectorHandler(CantripRegistry::reveal_ward, null, true);
                    break;
                case "summon_grimoire":
                    this.effectorHandler = new EffectorHandler(CantripRegistry::summonGrimoire, null, true);
                    break;
                case "summon_faction_grimoire":
                    this.effectorHandler = new EffectorHandler(CantripRegistry::summonFactionGrimoire, null, true);
                    break;
                case "apply_spell":
                    this.effectorHandler = new EffectorHandler(CantripRegistry::applySpellAtTargetOnDelay, null, true);
                    break;
                default:
                    MnaJS.LOGGER.warn("Unknown built-in effect: " + name + ". Using default effect.");
                    this.effectorHandler = new EffectorHandler(CantripRegistry::applySpellAtTargetOnDelay, null, true);
            }
            return this;
        }

        @Info("Register this cantrip with Mana and Artifice and return the created cantrip instance.")
        public ICantrip register() {
            if (icon == null) {
                MnaJS.LOGGER.warn("Cantrip " + id + " has no icon set. Using default.");
                icon = new ResourceLocation("mna", "textures/gui/cantrips/default.png");
            }

            if (pattern.isEmpty()) {
                MnaJS.LOGGER.warn("Cantrip " + id + " has no pattern set. Using default pattern.");
                pattern.add(new ResourceLocation("mna", "manaweave_patterns/circle"));
                pattern.add(new ResourceLocation("mna", "manaweave_patterns/square"));
            }

            ResourceLocation[] patternArray = pattern.toArray(new ResourceLocation[0]);

            final EffectorHandler finalHandler = effectorHandler != null ?
                    effectorHandler :
                    new EffectorHandler(CantripRegistry::applySpellAtTargetOnDelay, null, true);

            org.apache.logging.log4j.util.TriConsumer<Player, ICantrip, InteractionHand> finalEffector =
                    (player, cantrip, hand) -> {
                        syncDynamicItem(player, cantrip);
                        if (finalHandler.isBuiltIn) {
                            if (finalHandler.immediateEffector != null) {
                                finalHandler.immediateEffector.accept(player, cantrip, hand);
                            } else {
                                CantripRegistry.applySpellAtTargetOnDelay(player, cantrip, hand);
                            }
                        } else {
                            if (finalHandler.immediateEffector != null) {
                                finalHandler.immediateEffector.accept(player, cantrip, hand);
                            } else if (finalHandler.delayedEffector != null) {
                                DelayedEventQueue.pushEvent(player.level(),
                                        new TimedDelayedEvent<>(
                                                player.getUUID() + "cantrip",
                                                delay,
                                                Triple.of(player, cantrip, hand),
                                                finalHandler.delayedEffector::apply
                                        )
                                );
                            } else {
                                CantripRegistry.applySpellAtTargetOnDelay(player, cantrip, hand);
                            }
                        }
                    };

            ICantrip cantrip = CantripRegistry.INSTANCE.registerCantrip(
                    id, icon, tier, finalEffector, spellStack, patternArray);

            if (cantrip == null) {
                MnaJS.LOGGER.error("Failed to register cantrip: " + id);
                return null;
            }

            cantrip.setDelay(delay);
            cantrip.setSound(soundEffect);

            if (requiredAdvancement != null) {
                cantrip.setRequiredAdvancement(requiredAdvancement);
            }

            if (dynamicItemProvider != null) {
                cantrip.dynamicItem(null);
                DYNAMIC_ITEM_PROVIDERS.put(id, dynamicItemProvider);
            } else {
                DYNAMIC_ITEM_PROVIDERS.remove(id);
            }

            MnaJS.LOGGER.info("Registered custom cantrip: " + id);
            return cantrip;
        }
    }

    @Info(value = "Remove an existing cantrip from the registry.", params = {
            @Param(name = "cantripId", value = "Cantrip id to remove.")
    })
    public boolean removeCantrip(MnaCantripId cantripId) {
        try {
            ResourceLocation targetId = cantripId.location();
            java.lang.reflect.Field cantripsField = CantripRegistry.class.getDeclaredField("cantrips");
            cantripsField.setAccessible(true);

            @SuppressWarnings("unchecked")
            List<ICantrip> cantrips = (List<ICantrip>) cantripsField.get(CantripRegistry.INSTANCE);

            Optional<ICantrip> target = CantripRegistry.INSTANCE.getCantrip(targetId);
            if (!target.isPresent()) {
                MnaJS.LOGGER.warn("Cannot remove cantrip " + cantripId + ": not found in registry");
                return false;
            }

            boolean removed = false;
            Iterator<ICantrip> iterator = cantrips.iterator();
            while (iterator.hasNext()) {
                ICantrip cantrip = iterator.next();
                if (cantrip.getId().equals(targetId)) {
                    iterator.remove();
                    removed = true;
                    break;
                }
            }

            if (removed) {
                DYNAMIC_ITEM_PROVIDERS.remove(targetId);
                MnaJS.LOGGER.info("Successfully removed cantrip: " + cantripId);
                return true;
            } else {
                MnaJS.LOGGER.warn("Failed to remove cantrip " + cantripId + " (not found during iteration)");
                return false;
            }
        } catch (Exception e) {
            MnaJS.LOGGER.error("Error removing cantrip " + cantripId, e);
            return false;
        }
    }

    private static class EffectorHandler {
        final CantripEffector immediateEffector;
        final DelayedCantripEffector delayedEffector;
        final boolean isBuiltIn;

        EffectorHandler(CantripEffector immediateEffector, DelayedCantripEffector delayedEffector, boolean isBuiltIn) {
            this.immediateEffector = immediateEffector;
            this.delayedEffector = delayedEffector;
            this.isBuiltIn = isBuiltIn;
        }
    }

    @FunctionalInterface
    @Info("Immediate cantrip callback receiving the player, registered cantrip, and hand used.")
    public interface CantripEffector {
        void accept(Player player, ICantrip cantrip, InteractionHand hand);
    }

    @FunctionalInterface
    @Info("Delayed cantrip callback receiving the queued id and the stored player/cantrip/hand triple.")
    public interface DelayedCantripEffector {
        void apply(String id, Triple<Player, ICantrip, InteractionHand> data);
    }

    private static void syncDynamicItem(Player player, ICantrip cantrip) {
        if (player == null || cantrip == null) {
            return;
        }

        Function<Player, ItemStack> provider = DYNAMIC_ITEM_PROVIDERS.get(cantrip.getId());
        if (provider == null) {
            return;
        }

        ItemStack stack;
        try {
            stack = provider.apply(player);
        } catch (RuntimeException exception) {
            MnaJS.LOGGER.error("Dynamic item provider failed for cantrip {}", cantrip.getId(), exception);
            return;
        }

        if (stack == null) {
            stack = ItemStack.EMPTY;
        } else {
            stack = stack.copy();
        }

        IPlayerMagic magic = PlayerUtil.raw(player);
        if (magic == null) {
            return;
        }

        IPlayerCantrips cantrips = magic.getCantripData();
        if (cantrips == null) {
            return;
        }

        Optional<IPlayerCantrip> playerCantrip = cantrips.getCantrip(cantrip.getId());
        if (playerCantrip.isEmpty()) {
            return;
        }

        playerCantrip.get().setStack(stack);
        cantrips.setNeedsSync();
        magic.setSyncGrimoire();
    }
}
