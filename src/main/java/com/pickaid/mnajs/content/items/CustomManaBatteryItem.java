package com.pickaid.mnajs.content.items;

import com.mna.api.ManaAndArtificeMod;
import com.mna.api.capabilities.IPlayerMagic;
import com.mna.api.items.ManaBatteryItem;
import com.mna.api.particles.MAParticleType;
import com.mna.api.particles.ParticleInit;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Custom Mana Battery implementation for KubeJS integration with Mana and Artifice.
 * @author M1hono
 */
public class CustomManaBatteryItem extends ManaBatteryItem {
    private final int manaPerTick;
    private final Builder builder;
    private final float manaPerOperation;

    public CustomManaBatteryItem(Builder builder) {
        super(builder.createItemProperties(), builder.maxMana);
        this.builder = builder;
        this.manaPerTick = builder.manaPerTick;
        this.manaPerOperation = builder.manaPerOperation;
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity living, ItemStack stack, int pRemainingUseDuration) {
        IPlayerMagic magic = (IPlayerMagic)living.getCapability(ManaAndArtificeMod.getMagicCapability()).orElse((IPlayerMagic) null);
        if (magic != null && living instanceof Player) {
            int mana_shift_amount = this.manaPerTick;
            switch (this.getMode(stack)) {
                case 0:
                default:
                    if (living.level().isClientSide()) {
                        living.level().addParticle(new MAParticleType((ParticleType) ParticleInit.BLUE_SPARKLE_GRAVITY.get()), living.getX() - 0.5 + Math.random(), living.getY() + Math.random(), living.getZ() - 0.5 + Math.random(), 0.0, 0.10000000149011612, 0.0);
                    } else {
                        if (!(magic.getCastingResource().getAmount() > (float)mana_shift_amount)) {
                            living.stopUsingItem();
                            return;
                        }

                        if (!(this.refundMana(stack, (float)mana_shift_amount, (Player)living) > 0.0F)) {
                            living.stopUsingItem();
                            return;
                        }
                        if (this.builder.onUseTick != null) {
                            this.builder.onUseTick.apply(living.level(), living, stack, pRemainingUseDuration);
                        }
                        magic.getCastingResource().setAmount(magic.getCastingResource().getAmount() - (float)mana_shift_amount);
                    }
                    break;
                case 1:
                    if (living.level().isClientSide()) {
                        living.level().addParticle(new MAParticleType((ParticleType)ParticleInit.BLUE_SPARKLE_GRAVITY.get()), living.getX() - 0.5 + Math.random(), living.getY() + Math.random(), living.getZ() - 0.5 + Math.random(), 0.0, 0.10000000149011612, 0.0);
                    } else if (magic.getCastingResource().getAmount() < magic.getCastingResource().getMaxAmount()) {
                        if (this.consumeMana(stack, (float)mana_shift_amount, (Player)null)) {
                            magic.getCastingResource().restore((float)mana_shift_amount);
                        } else {
                            living.stopUsingItem();
                        }
                    }
            }
        }
    }

    @Override
    public float manaPerOperation() {
        return this.manaPerOperation;
    }

    @Override
    public boolean beforeCurioTick(LivingEntity entity, int index, ItemStack stack) {
        if (builder.curiosTick != null) {
            return builder.curiosTick.apply(entity, index, stack);
        }
        return super.beforeCurioTick(entity, index, stack);
    }

    @Override
    public boolean tickEffect(ItemStack stack, Player player, Level world, int slot, float mana, boolean selected) {
        if (builder.tickEffect != null && mana >= this.manaPerOperation()) {
            return builder.tickEffect.apply(stack, player, world, slot, mana, selected);
        }
        return super.tickEffect(stack, player, world, slot, mana, selected);
    }

    @Override
    public boolean tickInventory() {
        if (builder.tickEffect != null) {
            return true;
        }
        return  super.tickInventory();
    }

    @Override
    protected boolean tickCurio() {
        if (builder.curiosTick != null) {
            return true;
        }
        return super.tickCurio();
    }

    @Info("""
            Callback interface for handling custom Curios slot tick events.
            This is called before tickEffect and determines whether tickEffect should be triggered.
            
            @param entity The living entity wearing the battery as a curio
            @param index The index of the curios slot where the battery is equipped
            @param stack The battery item stack
            @return True to also trigger tickEffect, false otherwise
            """)
    public interface CuriosTickCallback {
        boolean apply(LivingEntity entity, int index, ItemStack stack);
    }

    @Info("""
            Callback interface for handling custom tick effects.
            This method acts as the primary tick handler for battery functionality.
            
            @param stack The item stack of the battery
            @param player The player who owns the battery
            @param world The current world/dimension
            @param slot The inventory slot containing the battery
            @param mana The current mana stored in the battery
            @param selected Whether the player is currently holding this item
            @return True to consume mana for this operation, false otherwise
            """)
    public interface TickEffectCallback {
        boolean apply(ItemStack stack, Player player, Level world, int slot, float mana, boolean selected);
    }

    @Info("""
            Callback interface for custom behavior during the active use of the battery.
            This callback won't affect the charging logic but allows for triggering additional
            effects during the charging process.
            
            @param pLevel The current world/dimension
            @param living The entity using the battery (typically a player)
            @param stack The battery item stack
            @param pRemainingUseDuration Ticks remaining in the item's use duration
            """)
    public interface onUseTickCallback {
        void apply(Level pLevel, LivingEntity living, ItemStack stack, int pRemainingUseDuration);
    }

    public static class Builder extends ItemBuilder {
        private float maxMana = 100.0F;
        private int manaPerTick = 1;
        private float manaPerOperation = 1.0F;
        private TickEffectCallback tickEffect;
        private CuriosTickCallback curiosTick;
        private onUseTickCallback onUseTick;

        public Builder(ResourceLocation id) {
            super(id);
        }

        @Info("Sets the maximum mana capacity for this battery. This determines the total amount of magical energy the item can store.")
        public Builder maxMana(float maxMana) {
            this.maxMana = maxMana;
            return this;
        }

        @Info("Sets the amount of mana transferred per tick when the player is using this item. This affects how quickly mana is transferred from the player to the battery or vice versa.")
        public Builder manaPerTick(int manaPerTick) {
            this.manaPerTick = manaPerTick;
            return this;
        }

        @Info("Sets the mana cost for each operation performed by the battery. This value is used to determine how much mana is consumed when the battery performs an action in tickEffect() or during curio tick processing.")
        public Builder manaPerOperation(float manaPerOperation) {
            this.manaPerOperation = manaPerOperation;
            return this;
        }

        @Info("Sets a callback that's triggered each tick when the battery is equipped in a curios slot. Returns true if tickEffect should also be triggered, false otherwise.")
        public Builder curiosTick(CuriosTickCallback callback) {
            this.curiosTick = callback;
            return this;
        }

        @Info("Sets a callback that defines the behavior occurring each tick when the item is in inventory or curio slot. Returns true if mana should be consumed, false otherwise.")
        public Builder tickEffect(TickEffectCallback callback) {
            this.tickEffect = callback;
            return this;
        }

        @Info("Sets a callback for custom behavior during the active use of the battery (while right-click is held). This won't affect charging logic but allows triggering additional effects during charging.")
        public Builder onUseTick(onUseTickCallback callback) {
            this.onUseTick = callback;
            return this;
        }

        @Override
        public CustomManaBatteryItem createObject() {
            return new CustomManaBatteryItem(this);
        }
    }
}