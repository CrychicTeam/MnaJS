package com.pickaid.mnajs.content;

import com.mna.api.faction.BaseFaction;
import com.mna.api.faction.IFaction;
import com.mna.api.spells.attributes.Attribute;
import com.pickaid.mnajs.kubejs.MnaJSPlugin;
import com.pickaid.mnajs.kubejs.id.MnaCastingResourceId;
import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.kubejs.id.MnaSoundId;
import com.pickaid.mnajs.kubejs.id.MnaStructureId;
import com.pickaid.mnajs.kubejs.id.MnaTypedIdLookups;
import com.pickaid.mnajs.kubejs.texture.MnaTexture;
import dev.latvian.mods.kubejs.generator.AssetJsonGenerator;
import dev.latvian.mods.kubejs.registry.BuilderBase;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Custom Faction implementation for KubeJS integration with Mana and Artifice.
 * @author M1hono
 */
public class CustomFaction extends BaseFaction {
    private final ItemStack factionGrimoire;
    private final Item tokenItem;
    private final SoundEvent raidSound;
    private final SoundEvent hornSound;
    private final Component occulusTaskPrompt;
    private final ResourceLocation factionIcon;
    private final int[] manaweaveRGB;
    private final ChatFormatting tornJournalPageFactionColor;
    private final ResourceLocation sanctumStructure;
    private final ResourceLocation[] castingResources;
    private final int factionIconTextureSize;
    private final Map<Attribute, Float> maxModifierBonuses;
    private final Map<Attribute, Float> minModifierBonuses;
    private final ResourceSelectorCallback resourceSelectorCallback;

    public CustomFaction(Builder builder) {
        this.factionGrimoire = builder.factionGrimoire;
        this.tokenItem = builder.tokenItem;
        this.raidSound = builder.raidSound;
        this.hornSound = builder.hornSound;
        this.occulusTaskPrompt = builder.occulusTaskPrompt;
        this.factionIcon = builder.factionIcon;
        this.manaweaveRGB = builder.manaweaveRGB;
        this.tornJournalPageFactionColor = builder.tornJournalPageFactionColor;
        this.sanctumStructure = builder.sanctumStructure;
        this.castingResources = builder.castingResources;
        this.factionIconTextureSize = builder.factionIconTextureSize;
        this.maxModifierBonuses = builder.maxModifierBonuses;
        this.minModifierBonuses = builder.minModifierBonuses;
        this.resourceSelectorCallback = builder.resourceSelectorCallback;
    }

    @Override
    public ItemStack getFactionGrimoire() {
        return this.factionGrimoire;
    }

    @Override
    public Item getTokenItem() {
        return this.tokenItem;
    }

    @Override
    public SoundEvent getRaidSound() {
        return this.raidSound;
    }

    @Override
    public @Nullable SoundEvent getHornSound() {
        return this.hornSound;
    }

    @Override
    public Component getOcculusTaskPrompt(int taskIndex) {
        return this.occulusTaskPrompt;
    }

    @Override
    public ResourceLocation getFactionIcon() {
        return this.factionIcon;
    }

    @Override
    public int getFactionIconTextureSize() {
        return this.factionIconTextureSize;
    }

    @Override
    public @Nullable int[] getManaweaveRGB() {
        return this.manaweaveRGB;
    }

    @Override
    public ChatFormatting getTornJournalPageFactionColor() {
        return this.tornJournalPageFactionColor;
    }

    @Override
    public @Nullable ResourceLocation getSanctumStructure() {
        return this.sanctumStructure;
    }

    @Override
    public ResourceLocation[] getCastingResources() {
        return this.castingResources;
    }

    @Override
    public ResourceLocation getCastingResource(Player player) {
        if (resourceSelectorCallback != null) {
            MnaCastingResourceId selected = resourceSelectorCallback.apply(player, MnaTypedIdLookups.wrapCastingResources(castingResources));
            if (selected != null) {
                return selected.location();
            }
        }
        return castingResources.length > 0 ? castingResources[0] : null;
    }

    @Override
    public float getMaxModifierBonus(Attribute attr) {
        return maxModifierBonuses.getOrDefault(attr, 0.0F);
    }

    @Override
    public float getMinModifierBonus(Attribute attr) {
        return minModifierBonuses.getOrDefault(attr, 0.0F);
    }

    @Info("Callback interface for selecting which casting resource to use for a player")
    public interface ResourceSelectorCallback {
        MnaCastingResourceId apply(Player player, MnaCastingResourceId[] availableResources);
    }

    public static class Builder extends BuilderBase<CustomFaction> {
        private ItemStack factionGrimoire = ItemStack.EMPTY;
        private Item tokenItem;
        private SoundEvent raidSound;
        private SoundEvent hornSound;
        private Component occulusTaskPrompt;
        private ResourceLocation factionIcon;
        private int[] manaweaveRGB;
        private ChatFormatting tornJournalPageFactionColor = ChatFormatting.WHITE;
        private ResourceLocation sanctumStructure;
        private ResourceLocation[] castingResources = new ResourceLocation[0];
        private int factionIconTextureSize = 8;
        private Map<Attribute, Float> maxModifierBonuses = new HashMap<>();
        private Map<Attribute, Float> minModifierBonuses = new HashMap<>();
        private ResourceSelectorCallback resourceSelectorCallback;

        public Builder(ResourceLocation id) {
            super(id);
        }

        @Info(value = "Set the grimoire item used by this faction.", params = {
                @Param(name = "grimoire", value = "Item id such as mna:grimoire_council.")
        })
        public Builder factionGrimoire(MnaItemId grimoire) {
            this.factionGrimoire = MnaTypedIdLookups.stack(grimoire, "factionGrimoire");
            return this;
        }

        @Info(value = "Set the token item used by this faction.", params = {
                @Param(name = "token", value = "Item id such as mna:faction_token.")
        })
        public Builder tokenItem(MnaItemId token) {
            this.tokenItem = MnaTypedIdLookups.requireItem(token, "tokenItem");
            return this;
        }

        @Info(value = "Set the raid sound used by this faction.", params = {
                @Param(name = "sound", value = "Sound id such as mna:cast_arcane.")
        })
        public Builder raidSound(MnaSoundId sound) {
            this.raidSound = MnaTypedIdLookups.requireSound(sound, "raidSound");
            return this;
        }

        @Info(value = "Set the optional horn sound used by this faction.", params = {
                @Param(name = "sound", value = "Sound id such as minecraft:item.goat_horn.sound.0.")
        })
        public Builder hornSound(MnaSoundId sound) {
            this.hornSound = MnaTypedIdLookups.requireSound(sound, "hornSound");
            return this;
        }

        @Info("Sets the occulus task prompt component for this faction")
        public Builder occulusTaskPrompt(Component prompt) {
            this.occulusTaskPrompt = prompt;
            return this;
        }

        @Info(value = "Set the faction icon texture.", params = {
                @Param(name = "icon", value = "Texture id such as mna:textures/gui/guide_book.png.")
        })
        public Builder factionIcon(MnaTexture icon) {
            this.factionIcon = icon.location();
            return this;
        }

        @Info(value = "Set the manaweave color for this faction using a CSS-style RGB value.", params = {
                @Param(name = "color", value = "Frontend color string such as #4ab64f or rgb(74, 182, 79).")
        })
        public Builder manaweaveRGB(String color) {
            return manaweaveRGB(parseCssRgb(color));
        }

        @HideFromJS
        public Builder manaweaveRGB(int[] rgb) {
            this.manaweaveRGB = normalizeRgb(rgb);
            return this;
        }

        @Info("Sets the color formatting for torn journal pages associated with this faction")
        public Builder tornJournalPageFactionColor(ChatFormatting color) {
            this.tornJournalPageFactionColor = color;
            return this;
        }

        @Info(value = "Set the sanctum structure used by this faction.", params = {
                @Param(name = "structure", value = "Structure id such as mna:multiblock/council_circle_of_power.")
        })
        public Builder sanctumStructure(MnaStructureId structure) {
            this.sanctumStructure = structure.location();
            return this;
        }

        @Info(value = "Set the casting resources available to this faction.", params = {
                @Param(name = "resources", value = "Casting resource ids such as mna:mana or mna:brimstone.")
        })
        public Builder castingResources(MnaCastingResourceId... resources) {
            this.castingResources = MnaTypedIdLookups.locations(resources);
            return this;
        }

        @Info("Sets the faction icon texture size (default is 8)")
        public Builder factionIconTextureSize(int size) {
            this.factionIconTextureSize = size;
            return this;
        }

        @Info("Sets the maximum modifier bonus for a specific attribute")
        public Builder maxModifierBonus(Attribute attribute, float value) {
            this.maxModifierBonuses.put(attribute, value);
            return this;
        }

        @Info("Sets the minimum modifier bonus for a specific attribute")
        public Builder minModifierBonus(Attribute attribute, float value) {
            this.minModifierBonuses.put(attribute, value);
            return this;
        }

        @Info(value = "Set a callback for selecting which casting resource to use based on the player.", params = {
                @Param(name = "callback", value = "Callback that receives the player and available casting resource ids.")
        })
        public Builder resourceSelector(ResourceSelectorCallback callback) {
            this.resourceSelectorCallback = callback;
            return this;
        }

        private static int[] normalizeRgb(int[] rgb) {
            Objects.requireNonNull(rgb, "manaweaveRGB can't be null");
            if (rgb.length != 3) {
                throw new IllegalArgumentException("manaweaveRGB must contain exactly 3 values");
            }

            return new int[]{
                    clampChannel(rgb[0]),
                    clampChannel(rgb[1]),
                    clampChannel(rgb[2])
            };
        }

        private static int[] parseCssRgb(String color) {
            Objects.requireNonNull(color, "manaweaveRGB color can't be null");
            String value = color.trim();
            if (value.isEmpty()) {
                throw new IllegalArgumentException("manaweaveRGB color can't be empty");
            }

            if (value.startsWith("#")) {
                String hex = value.substring(1);
                if (hex.length() == 3) {
                    hex = "" + hex.charAt(0) + hex.charAt(0)
                            + hex.charAt(1) + hex.charAt(1)
                            + hex.charAt(2) + hex.charAt(2);
                }
                if (hex.length() != 6) {
                    throw new IllegalArgumentException("manaweaveRGB hex color must be #RGB or #RRGGBB");
                }
                return new int[]{
                        Integer.parseInt(hex.substring(0, 2), 16),
                        Integer.parseInt(hex.substring(2, 4), 16),
                        Integer.parseInt(hex.substring(4, 6), 16)
                };
            }

            String lower = value.toLowerCase();
            if (lower.startsWith("rgb(") && value.endsWith(")")) {
                String inner = value.substring(4, value.length() - 1).trim();
                if (inner.contains("/")) {
                    inner = inner.substring(0, inner.indexOf('/')).trim();
                }

                String[] parts = inner.contains(",")
                        ? inner.split("\\s*,\\s*")
                        : inner.trim().split("\\s+");
                if (parts.length != 3) {
                    throw new IllegalArgumentException("manaweaveRGB rgb() value must contain exactly 3 channels");
                }

                return new int[]{
                        clampChannel(Integer.parseInt(parts[0])),
                        clampChannel(Integer.parseInt(parts[1])),
                        clampChannel(Integer.parseInt(parts[2]))
                };
            }

            throw new IllegalArgumentException("Unsupported manaweaveRGB color format: " + color);
        }

        private static int clampChannel(int value) {
            return Math.max(0, Math.min(255, value));
        }

        @Override
        public RegistryInfo<IFaction> getRegistryType() {
            return MnaJSPlugin.FACTION_REGISTRY.get();
        }

        @Override
        public CustomFaction createObject() {
            return new CustomFaction(this);
        }

        @Override
        public void generateAssetJsons(AssetJsonGenerator generator) {
            super.generateAssetJsons(generator);
        }
    }
}
