package com.pickaid.mnajs.content;

import com.mna.api.faction.BaseFaction;
import com.mna.api.faction.IFaction;
import com.mna.api.spells.attributes.Attribute;
import com.pickaid.mnajs.kubejs.MnaJSPlugin;
import dev.latvian.mods.kubejs.generator.AssetJsonGenerator;
import dev.latvian.mods.kubejs.registry.BuilderBase;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.typings.Info;
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
            return resourceSelectorCallback.apply(player, castingResources);
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
        ResourceLocation apply(Player player, ResourceLocation[] availableResources);
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

        @Info("Sets the grimoire item for this faction")
        public Builder factionGrimoire(ItemStack grimoire) {
            this.factionGrimoire = grimoire;
            return this;
        }

        @Info("Sets the token item for this faction")
        public Builder tokenItem(Item token) {
            this.tokenItem = token;
            return this;
        }

        @Info("Sets the raid sound for this faction")
        public Builder raidSound(SoundEvent sound) {
            this.raidSound = sound;
            return this;
        }

        @Info("Sets the horn sound for this faction")
        public Builder hornSound(SoundEvent sound) {
            this.hornSound = sound;
            return this;
        }

        @Info("Sets the occulus task prompt component for this faction")
        public Builder occulusTaskPrompt(Component prompt) {
            this.occulusTaskPrompt = prompt;
            return this;
        }

        @Info("Sets the faction icon resource location")
        public Builder factionIcon(ResourceLocation icon) {
            this.factionIcon = icon;
            return this;
        }

        @Info("Sets the RGB values for manaweave associated with this faction")
        public Builder manaweaveRGB(int[] rgb) {
            this.manaweaveRGB = rgb;
            return this;
        }

        @Info("Sets the color formatting for torn journal pages associated with this faction")
        public Builder tornJournalPageFactionColor(ChatFormatting color) {
            this.tornJournalPageFactionColor = color;
            return this;
        }

        @Info("Sets the sanctum structure resource location for this faction")
        public Builder sanctumStructure(ResourceLocation structure) {
            this.sanctumStructure = structure;
            return this;
        }

        @Info("Sets the casting resources for this faction")
        public Builder castingResources(ResourceLocation... resources) {
            this.castingResources = resources;
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

        @Info("Sets a callback for selecting which casting resource to use based on the player")
        public Builder resourceSelector(ResourceSelectorCallback callback) {
            this.resourceSelectorCallback = callback;
            return this;
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