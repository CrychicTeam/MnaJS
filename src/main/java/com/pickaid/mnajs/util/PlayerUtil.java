package com.pickaid.mnajs.util;

import com.mna.api.capabilities.IPlayerMagic;
import com.mna.api.capabilities.IPlayerProgression;
import com.mna.capabilities.playerdata.magic.PlayerMagicProvider;
import com.mna.capabilities.playerdata.progression.PlayerProgressionProvider;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicReference;

public class PlayerUtil {
    public static IPlayerMagic get(Player player) {
        return player.getCapability(PlayerMagicProvider.MAGIC).resolve().get();
    }

    public static float getMana(Player player) {
        return get(player).getCastingResource().getAmount();
    }

    public static void setMana(Player player, float amount) {
        get(player).getCastingResource().setAmount(amount);
    }

    public static void addMana(Player player, float amount) {
        get(player).getCastingResource().setAmount(Math.min(getMana(player) + amount, get(player).getCastingResource().getMaxAmount()));
    }

    public static void subtractMana(Player player, float amount) {
        get(player).getCastingResource().setAmount(Math.max(getMana(player) - amount, 0));
    }

    @Nullable
    public static IPlayerProgression getProgressionCao(Player player) {
        AtomicReference<IPlayerProgression> progression = null;
        player.getCapability(PlayerProgressionProvider.PROGRESSION).ifPresent(progression::set);
        return progression.get();
    }
}