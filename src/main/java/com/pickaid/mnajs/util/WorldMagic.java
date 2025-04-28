package com.pickaid.mnajs.util;

import com.mna.api.capabilities.IWorldMagic;
import com.mna.capabilities.worlddata.WorldMagicProvider;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicReference;

public class WorldMagic {
    @Nullable
    public static IWorldMagic get(Level level) {
        AtomicReference<IWorldMagic> magic = null;
        level.getCapability(WorldMagicProvider.MAGIC).ifPresent(magic::set);
        return magic.get();
    }
}
