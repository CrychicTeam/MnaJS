package com.pickaid.mnajs.kubejs.events.startup;

import com.mna.api.cantrips.ICantrip;
import com.mna.api.sound.SFX;
import com.mna.api.timing.DelayedEventQueue;
import com.mna.api.timing.TimedDelayedEvent;
import com.mna.cantrips.Cantrip;
import com.mna.cantrips.CantripRegistry;
import com.pickaid.mnajs.MnaJS;
import dev.latvian.mods.kubejs.event.EventJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.Triple;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

public class CantripRegistrationEventJS extends EventJS {

    public CantripBuilder create(String id) {
        return new CantripBuilder(new ResourceLocation(id));
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

        public CantripBuilder tier(int tier) {
            this.tier = tier;
            return this;
        }

        public CantripBuilder delay(int delay) {
            this.delay = delay;
            return this;
        }

        public CantripBuilder requiredAdvancement(String advancement) {
            this.requiredAdvancement = new ResourceLocation(advancement);
            return this;
        }

        public CantripBuilder sound(SoundEvent sound) {
            this.soundEffect = sound;
            return this;
        }

        public CantripBuilder icon(String icon) {
            this.icon = new ResourceLocation(icon);
            return this;
        }

        public CantripBuilder addPattern(String pattern) {
            this.pattern.add(new ResourceLocation(pattern));
            return this;
        }

        public CantripBuilder pattern(ResourceLocation... patterns) {
            this.pattern = Arrays.asList(patterns);
            return this;
        }

        public CantripBuilder spellStack(ItemStack spellStack) {
            this.spellStack = spellStack;
            return this;
        }

        public CantripBuilder dynamicItem(@Nullable Item item) {
            if (item == null) {
                this.dynamicItemProvider = null;
            } else {
                this.dynamicItemProvider = (player) -> new ItemStack(item);
            }
            return this;
        }

        public CantripBuilder dynamicItemProvider(Function<Player, ItemStack> provider) {
            this.dynamicItemProvider = provider;
            return this;
        }

        public CantripBuilder effect(CantripEffector effector) {
            this.effectorHandler = new EffectorHandler(effector, null, false);
            return this;
        }

        public CantripBuilder delayedEffect(DelayedCantripEffector delayedEffector) {
            this.effectorHandler = new EffectorHandler(null, delayedEffector, false);
            return this;
        }

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

        public ICantrip register() {
            if (icon == null) {
                MnaJS.LOGGER.warn("Cantrip " + id + " has no icon set. Using default.");
                icon = new ResourceLocation("mna:textures/gui/cantrips/default.png");
            }

            if (pattern.isEmpty()) {
                MnaJS.LOGGER.warn("Cantrip " + id + " has no pattern set. Using default pattern.");
                pattern.add(new ResourceLocation("mna:manaweave_patterns/circle"));
                pattern.add(new ResourceLocation("mna:manaweave_patterns/square"));
            }

            ResourceLocation[] patternArray = pattern.toArray(new ResourceLocation[0]);

            final EffectorHandler finalHandler = effectorHandler != null ?
                    effectorHandler :
                    new EffectorHandler(CantripRegistry::applySpellAtTargetOnDelay, null, true);

            org.apache.logging.log4j.util.TriConsumer<Player, ICantrip, InteractionHand> finalEffector =
                    (player, cantrip, hand) -> {
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

                try {
                    if (cantrip instanceof Cantrip cantripImpl) {
                        java.lang.reflect.Field providerField = Cantrip.class.getDeclaredField("dynamicItemProvider");
                        providerField.setAccessible(true);
                        providerField.set(cantripImpl, dynamicItemProvider);
                    }
                } catch (Exception e) {
                    MnaJS.LOGGER.error("Failed to set dynamic item provider for cantrip: " + id, e);
                }
            }

            MnaJS.LOGGER.info("Registered custom cantrip: " + id);
            return cantrip;
        }
    }

    public boolean removeCantrip(String cantripId) {
        try {
            Field cantripsField = CantripRegistry.class.getDeclaredField("cantrips");
            cantripsField.setAccessible(true);

            @SuppressWarnings("unchecked")
            List<ICantrip> cantrips = (List<ICantrip>) cantripsField.get(CantripRegistry.INSTANCE);

            Optional<ICantrip> target = CantripRegistry.INSTANCE.getCantrip(new ResourceLocation(cantripId));
            if (!target.isPresent()) {
                MnaJS.LOGGER.warn("Cannot remove cantrip " + cantripId + ": not found in registry");
                return false;
            }

            boolean removed = false;
            Iterator<ICantrip> iterator = cantrips.iterator();
            while (iterator.hasNext()) {
                ICantrip cantrip = iterator.next();
                if (cantrip.getId().equals(new ResourceLocation(cantripId))) {
                    iterator.remove();
                    removed = true;
                    break;
                }
            }

            if (removed) {
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
    public interface CantripEffector {
        void accept(Player player, ICantrip cantrip, InteractionHand hand);
    }

    @FunctionalInterface
    public interface DelayedCantripEffector {
        void apply(String id, Triple<Player, ICantrip, InteractionHand> data);
    }
}