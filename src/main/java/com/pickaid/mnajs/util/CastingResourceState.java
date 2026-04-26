package com.pickaid.mnajs.util;

import com.mna.api.capabilities.resource.ICastingResource;
import com.mna.api.capabilities.resource.SyncStatus;
import com.pickaid.mnajs.kubejs.id.MnaCastingResourceId;
import com.pickaid.mnajs.kubejs.id.MnaTypedIdLookups;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

public final class CastingResourceState {
    private final Player player;
    private final ICastingResource castingResource;

    CastingResourceState(Player player, ICastingResource castingResource) {
        this.player = Objects.requireNonNull(player, "player");
        this.castingResource = Objects.requireNonNull(castingResource, "castingResource");
    }

    @Info("Get the typed id of the current casting resource.")
    public MnaCastingResourceId getId() {
        return MnaTypedIdLookups.wrapCastingResource(castingResource.getRegistryName());
    }

    @Info("Get the current amount stored in this casting resource.")
    public float getAmount() {
        return castingResource.getAmount();
    }

    @Info(value = "Set the current amount stored in this casting resource.", params = {
            @Param(name = "amount", value = "New amount to store after clamping to valid bounds.")
    })
    public void setAmount(float amount) {
        castingResource.setAmount(Math.max(0.0F, Math.min(amount, getMaxAmount())));
    }

    @Info("Get the current maximum amount of this casting resource.")
    public float getMaxAmount() {
        return castingResource.getMaxAmount();
    }

    @Info(value = "Set the maximum amount of this casting resource.", params = {
            @Param(name = "amount", value = "New maximum amount.")
    })
    public void setMaxAmount(float amount) {
        castingResource.setMaxAmount(Math.max(0.0F, amount));
        setAmount(getAmount());
    }

    @Info(value = "Restore an amount into this casting resource.", params = {
            @Param(name = "amount", value = "Amount to restore.")
    })
    public void restore(float amount) {
        castingResource.restore(amount);
    }

    @Info(value = "Consume an amount from this casting resource using the bound player.", params = {
            @Param(name = "amount", value = "Amount to consume.")
    })
    public void consume(float amount) {
        castingResource.consume(player, amount);
    }

    @Info(value = "Check whether the bound player has enough of this resource.", params = {
            @Param(name = "amount", value = "Amount required.")
    })
    public boolean hasEnough(float amount) {
        return castingResource.hasEnough(player, amount);
    }

    @Info("Get the current sync status for this casting resource.")
    public SyncStatus getSyncStatus() {
        return castingResource.getSyncStatus();
    }

    @Info("Get the regeneration rate of this casting resource for the bound player.")
    public int getRegenerationRate() {
        return castingResource.getRegenerationRate(player);
    }

    @Info(value = "Set the regeneration rate of this casting resource.", params = {
            @Param(name = "rate", value = "New regeneration rate.")
    })
    public void setRegenerationRate(int rate) {
        castingResource.setRegenerationRate(rate);
    }
}
