package com.pickaid.mnajs.util;

import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

public final class PlayerProgressionHelper {
    private PlayerProgressionHelper() {
    }

    @Nullable
    @Info("Return the typed player progression facade for this player.")
    public static PlayerProgressionState of(Player player) {
        return PlayerUtil.progressionOf(player);
    }
}
