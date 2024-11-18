package com.crychicteam.mnajs.content;

import com.crychicteam.mnajs.kubejs.MnaJSPlugin;
import com.mna.api.faction.BaseFaction;
import com.mna.api.faction.IFaction;
import dev.latvian.mods.kubejs.generator.AssetJsonGenerator;
import dev.latvian.mods.kubejs.registry.BuilderBase;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class CustomFaction extends BaseFaction {
    private final ItemStack factionGrimoire;
    private final Item tokenItem;
    private final SoundEvent raidSound;
    private final SoundEvent hornSound;
    private final Component occulusTaskPrompt;
    private final ResourceLocation factionIcon;
    private final int[] manaweaveRGB;
    private final ChatFormatting tornJournalPageFactionColor;

    public CustomFaction(Builder builder) {
        this.factionGrimoire = builder.factionGrimoire;
        this.tokenItem = builder.tokenItem;
        this.raidSound = builder.raidSound;
        this.hornSound = builder.hornSound;
        this.occulusTaskPrompt = builder.occulusTaskPrompt;
        this.factionIcon = builder.factionIcon;
        this.manaweaveRGB = builder.manaweaveRGB;
        this.tornJournalPageFactionColor = builder.tornJournalPageFactionColor;
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
    public Component getOcculusTaskPrompt(int i) {
        return this.occulusTaskPrompt;
    }

    @Override
    public ResourceLocation getFactionIcon() {
        return this.factionIcon;
    }

    @Override
    public @Nullable int[] getManaweaveRGB() {
        return this.manaweaveRGB;
    }

    @Override
    public ChatFormatting getTornJournalPageFactionColor() {
        return this.tornJournalPageFactionColor;
    }

    public static class Builder extends BuilderBase<CustomFaction> {
        public ItemStack factionGrimoire;
        public Item tokenItem;
        public SoundEvent raidSound;
        public SoundEvent hornSound;
        public Component occulusTaskPrompt;
        public ResourceLocation factionIcon;
        public int[] manaweaveRGB;
        public ChatFormatting tornJournalPageFactionColor;

        public Builder(ResourceLocation i) {
            super(i);
        }

        public void setFactionGrimoire(ItemStack factionGrimoire) {
            this.factionGrimoire = factionGrimoire;
        }

        public void setTokenItem(Item tokenItem) {
            this.tokenItem = tokenItem;
        }

        public void setFactionIcon(ResourceLocation factionIcon) {
            this.factionIcon = factionIcon;
        }

        public void setHornSound(SoundEvent hornSound) {
            this.hornSound = hornSound;
        }

        public void setManaweaveRGB(int[] manaweaveRGB) {
            this.manaweaveRGB = manaweaveRGB;
        }

        public void setOcculusTaskPrompt(Component occulusTaskPrompt) {
            this.occulusTaskPrompt = occulusTaskPrompt;
        }

        public void setRaidSound(SoundEvent raidSound) {
            this.raidSound = raidSound;
        }

        public void setTornJournalPageFactionColor(ChatFormatting tornJournalPageFactionColor) {
            this.tornJournalPageFactionColor = tornJournalPageFactionColor;
        }

        @Override
        public RegistryInfo<IFaction> getRegistryType() {
            return MnaJSPlugin.FACTION_REGISTRY;
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
