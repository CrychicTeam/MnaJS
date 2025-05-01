package com.pickaid.mnajs.util;

import com.mna.api.affinity.Affinity;
import com.mna.api.capabilities.IWorldMagic;
import com.mna.api.capabilities.WellspringNode;
import com.mna.capabilities.worlddata.WorldMagicProvider;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class WorldMagic {
    @Nullable
    public static IWorldMagic get(Level level) {
        AtomicReference<IWorldMagic> magic = null;
        level.getCapability(WorldMagicProvider.MAGIC).ifPresent(magic::set);
        return magic.get();
    }

    @Info("boolean value is whether to force adding a Wellspring Node on the position.")
    public static boolean addNode(Level level, BlockPos pos, Affinity affinity, float strength, boolean force) {
        if(get(level) != null && get(level).getWellspringRegistry() != null) {
            return get(level).getWellspringRegistry().addNode(level, pos, () -> new WellspringNode(affinity, strength), force);
        }
        return false;
    }

    @Nullable
    public static Float getPower(Level level, Player player, Affinity affinity) {
        if(get(level) != null && get(level).getWellspringRegistry() != null) {
            Map<Affinity,Float> amap = get(level).getWellspringRegistry().getNodeNetworkAmountFor(player);
            return amap.get(affinity);
        }
        return null;
    }

    public static void addPower(Level level, Player player, Affinity affinity, float amount) {
        if(get(level) != null && get(level).getWellspringRegistry() != null && getPower(level, player, affinity) != null) {
            get(level).getWellspringRegistry().insertPower(player.getUUID(), level, affinity, getPower(level, player, affinity) + amount);
        }
    }

    public static void subtractPower(Level level, Player player, Affinity affinity, float amount) {
        if(get(level) != null && get(level).getWellspringRegistry() != null && getPower(level, player, affinity) != null) {
            get(level).getWellspringRegistry().insertPower(player.getUUID(), level, affinity, getPower(level, player, affinity) - amount);
        }
    }

    public static void setPower(Level level, Player player, Affinity affinity, float amount) {
        if(get(level) != null && get(level).getWellspringRegistry() != null && getPower(level, player, affinity) != null) {
            get(level).getWellspringRegistry().insertPower(player.getUUID(), level, affinity, amount);
        }
    }
}