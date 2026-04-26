package com.pickaid.mnajs.util;

import com.mna.api.affinity.Affinity;
import com.mna.api.capabilities.IWorldMagic;
import com.mna.api.capabilities.WellspringNode;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;

public final class WorldMagicState {
    private final Level level;
    private final IWorldMagic worldMagic;

    WorldMagicState(Level level, IWorldMagic worldMagic) {
        this.level = Objects.requireNonNull(level, "level");
        this.worldMagic = Objects.requireNonNull(worldMagic, "worldMagic");
    }

    @Info("Return whether the world magic capability currently has a wellspring registry.")
    public boolean hasWellspringRegistry() {
        return worldMagic.getWellspringRegistry() != null;
    }

    @Info(value = "Add a wellspring node at the given position.", params = {
            @Param(name = "pos", value = "Target block position."),
            @Param(name = "affinity", value = "Affinity for the new node."),
            @Param(name = "strength", value = "Node strength."),
            @Param(name = "force", value = "Whether to force adding the node.")
    })
    public boolean addNode(BlockPos pos, Affinity affinity, float strength, boolean force) {
        if (!hasWellspringRegistry()) {
            return false;
        }
        return worldMagic.getWellspringRegistry().addNode(level, pos, () -> new WellspringNode(affinity, strength), force);
    }

    @Nullable
    @Info(value = "Get the current wellspring power for a player and affinity.", params = {
            @Param(name = "player", value = "Target player."),
            @Param(name = "affinity", value = "Affinity to inspect.")
    })
    public Float getPower(Player player, Affinity affinity) {
        if (!hasWellspringRegistry()) {
            return null;
        }
        Map<Affinity, Float> amounts = worldMagic.getWellspringRegistry().getNodeNetworkAmountFor(player);
        return amounts.get(affinity);
    }

    @Info(value = "Add wellspring power for a player and affinity.", params = {
            @Param(name = "player", value = "Target player."),
            @Param(name = "affinity", value = "Affinity to modify."),
            @Param(name = "amount", value = "Amount to add.")
    })
    public void addPower(Player player, Affinity affinity, float amount) {
        if (hasWellspringRegistry() && getPower(player, affinity) != null) {
            worldMagic.getWellspringRegistry().insertPower(player.getUUID(), level, affinity, amount);
        }
    }

    @Info(value = "Subtract wellspring power for a player and affinity.", params = {
            @Param(name = "player", value = "Target player."),
            @Param(name = "affinity", value = "Affinity to modify."),
            @Param(name = "amount", value = "Amount to consume.")
    })
    public void subtractPower(Player player, Affinity affinity, float amount) {
        if (hasWellspringRegistry() && getPower(player, affinity) != null) {
            worldMagic.getWellspringRegistry().consumePower(player.getUUID(), level, affinity, amount);
        }
    }

    @Info(value = "Set the wellspring power for a player and affinity.", params = {
            @Param(name = "player", value = "Target player."),
            @Param(name = "affinity", value = "Affinity to modify."),
            @Param(name = "amount", value = "Target amount to set.")
    })
    public void setPower(Player player, Affinity affinity, float amount) {
        if (hasWellspringRegistry() && getPower(player, affinity) != null && player instanceof ServerPlayer serverPlayer) {
            worldMagic.getWellspringRegistry().setWellspringPower(serverPlayer, affinity, amount);
        }
    }
}
