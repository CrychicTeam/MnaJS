package com.pickaid.mnajs.util;

import com.mna.api.affinity.Affinity;
import com.mna.api.capabilities.IWorldMagic;
import com.mna.capabilities.worlddata.WorldMagicProvider;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicReference;

public final class WorldMagic {
    private WorldMagic() {
    }

    @Nullable
    @HideFromJS
    public static IWorldMagic get(Level level) {
        AtomicReference<IWorldMagic> magic = new AtomicReference<>();
        level.getCapability(WorldMagicProvider.MAGIC).ifPresent(magic::set);
        return magic.get();
    }

    @Nullable
    @Info("Return the typed world magic facade for this level.")
    public static WorldMagicState of(Level level) {
        IWorldMagic magic = get(level);
        return magic == null ? null : new WorldMagicState(level, magic);
    }

    @Info("boolean value is whether to force adding a Wellspring Node on the position.")
    public static boolean addNode(Level level, BlockPos pos, Affinity affinity, float strength, boolean force) {
        WorldMagicState state = of(level);
        return state != null && state.addNode(pos, affinity, strength, force);
    }

    @Nullable
    public static Float getPower(Level level, Player player, Affinity affinity) {
        WorldMagicState state = of(level);
        return state == null ? null : state.getPower(player, affinity);
    }

    public static void addPower(Level level, Player player, Affinity affinity, float amount) {
        WorldMagicState state = of(level);
        if (state != null) {
            state.addPower(player, affinity, amount);
        }
    }

    public static void subtractPower(Level level, Player player, Affinity affinity, float amount) {
        WorldMagicState state = of(level);
        if (state != null) {
            state.subtractPower(player, affinity, amount);
        }
    }

    public static void setPower(Level level, Player player, Affinity affinity, float amount) {
        WorldMagicState state = of(level);
        if (state != null) {
            state.setPower(player, affinity, amount);
        }
    }
}
