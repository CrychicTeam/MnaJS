package com.crychicteam.mnajs.content.items;

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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * @author M1hono
 */
public class CustomManaBatteryItem extends ManaBatteryItem {
    private final int manaPerTick;
    private final Builder builder;

    public CustomManaBatteryItem(Builder builder) {
        super(new Item.Properties(), builder.maxMana);
        this.builder = builder;
        this.manaPerTick = builder.manaPerTick;
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
    protected boolean tickCurio() {
        if (builder.curiosTick != null) {
            return builder.curiosTick.tick();
        }
        return super.tickCurio();
    }

    @Override
    protected boolean tickEffect(ItemStack stack, Player player, Level world, int slot, float mana, boolean selected) {
        if (builder.tickEffect != null) {
            return builder.tickEffect.tickEffect(stack, player, world, slot, mana, selected);
        }
        return super.tickEffect(stack, player, world, slot, mana, selected);
    }

    @Info("""
            Not sure if it's working or not.
            """)
    public interface CuriosTickCallback {
        boolean tick();
    }

    @Info("""
            Not sure if it's working or not.
            """)
    public interface TickEffectCallback {
        boolean tickEffect(ItemStack stack, Player player, Level world, int slot, float mana, boolean selected);
    }

    public static class Builder extends ItemBuilder {
        private float maxMana = 100.0F;
        private int manaPerTick = 1;
        private TickEffectCallback tickEffect;
        private CuriosTickCallback curiosTick;

        public Builder(ResourceLocation id) {
            super(id);
        }

        public Builder maxMana(float maxMana) {
            this.maxMana = maxMana;
            return this;
        }

        public Builder manaPerTick(int manaPerTick) {
            this.manaPerTick = manaPerTick;
            return this;
        }

        public Builder curiosTick(CuriosTickCallback callback) {
            this.curiosTick = callback;
            return this;
        }

        public Builder tickEffect(TickEffectCallback callback) {
            this.tickEffect = callback;
            return this;
        }

        @Override
        public CustomManaBatteryItem createObject() {
            return new CustomManaBatteryItem(this);
        }
    }
}