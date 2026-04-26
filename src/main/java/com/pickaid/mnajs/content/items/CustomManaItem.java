package com.pickaid.mnajs.content.items;

import com.mna.api.faction.IFaction;
import com.mna.api.items.IFactionSpecific;
import com.mna.api.items.IShowHud;
import com.mna.api.items.TieredItem;
import com.mna.items.base.IManaRepairable;
import com.pickaid.mnajs.kubejs.MnaJSPlugin;
import com.pickaid.mnajs.kubejs.id.MnaFactionId;
import com.pickaid.mnajs.kubejs.id.MnaTypedIdLookups;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.registry.BuilderBase;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Custom Mana Item implementation for KubeJS integration with Mana and Artifice.
 * This item implements TieredItem, IShowHud, and IFactionSpecific.
 */
public class CustomManaItem extends TieredItem implements IShowHud, IFactionSpecific, IManaRepairable {
    private final Builder builder;

    public CustomManaItem(Builder builder) {
        super(builder.createItemProperties());
        this.builder = builder;
        if (builder.sneakBypass) {
            this.setSneakBypass();
        }
        if (builder.tier >= 0) {
            this.setCachedTier(builder.tier);
        }
    }

    @Override
    public IFaction getFaction() {
        return MnaTypedIdLookups.findFaction(builder.faction);
    }

    @Override
    public float getMinIre() {
        return builder.minIre;
    }

    @Override
    public float getMaxIre() {
        return builder.maxIre;
    }

    @Override
    public void usedByPlayer(Player player) {
        if (builder.usedByPlayerCallback != null) {
            builder.usedByPlayerCallback.accept(player);
            return;
        }
        IFactionSpecific.super.usedByPlayer(player);
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, LevelReader level, BlockPos pos, Player player) {
        if (builder.doesSneakBypassUseCallback != null) {
            return builder.doesSneakBypassUseCallback.apply(stack, level, pos, player);
        }
        return super.doesSneakBypassUse(stack, level, pos, player);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        if (builder.appendHoverTextCallback != null) {
            builder.appendHoverTextCallback.accept(stack, level, tooltip, flag);
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }

    @Info("Callback interface for rendering the HUD")
    public interface RenderHudCallback {
        void accept(Player player, ItemStack stack, float partialTicks);
    }

    @Info("Callback interface for determining if the HUD should be shown")
    public interface DoesShowHudCallback {
        boolean apply(Player player, ItemStack stack);
    }

    @Info("Callback interface for determining if sneak bypasses use")
    public interface DoesSneakBypassUseCallback {
        boolean apply(ItemStack stack, LevelReader level, BlockPos pos, Player player);
    }

    @Info("Callback interface for adding hover text")
    public interface AppendHoverTextCallback {
        void accept(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag);
    }

    @Info("Callback interface for used by player")
    public interface UsedByPlayerCallback {
        void accept(Player player);
    }

    @Info("Callback interface for getting mana stored")
    public interface GetManaStoredCallback {
        int apply(ItemStack stack);
    }

    @Info("Callback interface for setting mana stored")
    public interface SetManaStoredCallback {
        void accept(ItemStack stack, int amount);
    }

    @Info("Callback interface for filling mana")
    public interface FillManaCallback {
        int apply(ItemStack stack, int amount, boolean simulate);
    }

    @Info("Callback interface for checking if has max mana")
    public interface HasMaxManaCallback {
        boolean apply(ItemStack stack);
    }

    public static class Builder extends ItemBuilder {
        private boolean sneakBypass = false;
        private int tier = -1;
        private MnaFactionId faction = null;
        private float minIre = 0.0F;
        private float maxIre = 0.005F;

        private DoesSneakBypassUseCallback doesSneakBypassUseCallback;
        private AppendHoverTextCallback appendHoverTextCallback;
        private UsedByPlayerCallback usedByPlayerCallback;

        public Builder(ResourceLocation id) {
            super(id);
        }

        @Info("Sets whether sneak bypasses use")
        public Builder sneakBypass(boolean bypass) {
            this.sneakBypass = bypass;
            return this;
        }

        @Info("Sets the tier of the item")
        public Builder tier(int tier) {
            this.tier = tier;
            return this;
        }

        @Info(value = "Set the faction tied to this item.", params = {
                @Param(name = "faction", value = "Faction id such as mna:council.")
        })
        public Builder faction(MnaFactionId faction) {
            this.faction = faction;
            return this;
        }

        @Info("Sets the min IRE value for this item")
        public Builder minIre(float minIre) {
            this.minIre = minIre;
            return this;
        }

        @Info("Sets the max IRE value for this item")
        public Builder maxIre(float maxIre) {
            this.maxIre = maxIre;
            return this;
        }

        @Info("Sets the callback for determining if sneak bypasses use")
        public Builder doesSneakBypassUse(DoesSneakBypassUseCallback callback) {
            this.doesSneakBypassUseCallback = callback;
            return this;
        }

        @Info("Sets the callback for adding hover text")
        public Builder appendHoverText(AppendHoverTextCallback callback) {
            this.appendHoverTextCallback = callback;
            return this;
        }

        @Info("Sets the callback for when an item is used by a player")
        public Builder usedByPlayer(UsedByPlayerCallback callback) {
            this.usedByPlayerCallback = callback;
            return this;
        }

        @Override
        public CustomManaItem createObject() {
            return new CustomManaItem(this);
        }
    }
}
