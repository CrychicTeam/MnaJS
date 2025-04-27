package com.pickaid.mnajs.util;

import com.mna.api.capabilities.IPlayerMagic;
import com.mna.capabilities.playerdata.magic.PlayerMagicProvider;
import net.minecraft.world.entity.player.Player;

public class PlayerMagic {
    public static IPlayerMagic getPlayerMagic(Player player) {
        return player.getCapability(PlayerMagicProvider.MAGIC).resolve().get();
    }

    public static float getMana(Player player) {
        return getPlayerMagic(player).getCastingResource().getAmount();
    }

    public static void setMana(Player player, float amount) {
        getPlayerMagic(player).getCastingResource().setAmount(amount);
    }

    public static void addMana(Player player, float amount) {
        getPlayerMagic(player).getCastingResource().setAmount(Math.min(getMana(player) + amount, getPlayerMagic(player).getCastingResource().getMaxAmount()));
    }

    public static void subtractMana(Player player, float amount) {
        getPlayerMagic(player).getCastingResource().setAmount(Math.max(getMana(player) - amount, 0));
    }
}
