package com.crychicteam.mnajs.util;

import com.mna.api.capabilities.IPlayerMagic;
import com.mna.capabilities.playerdata.magic.PlayerMagicProvider;
import net.minecraft.world.entity.player.Player;

public class PlayerMagic {

    public static IPlayerMagic getPlayerMagic(Player player) {
        return player.getCapability(PlayerMagicProvider.MAGIC).resolve().get();
    }
}
