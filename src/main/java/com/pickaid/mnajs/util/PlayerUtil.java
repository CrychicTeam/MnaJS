package com.pickaid.mnajs.util;

import com.mna.api.capabilities.IPlayerMagic;
import com.mna.api.capabilities.IPlayerProgression;
import com.mna.capabilities.playerdata.magic.PlayerMagicProvider;
import com.mna.capabilities.playerdata.progression.PlayerProgressionProvider;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.HideFromJS;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

public final class PlayerUtil {
    private PlayerUtil() {
    }

    @Nullable
    @HideFromJS
    public static IPlayerMagic raw(Player player) {
        return player.getCapability(PlayerMagicProvider.MAGIC).resolve().orElse(null);
    }

    @Nullable
    @HideFromJS
    public static IPlayerMagic get(Player player) {
        return raw(player);
    }

    @Nullable
    @Info("Return the typed player magic facade for this player.")
    public static PlayerMagicState of(Player player) {
        IPlayerMagic magic = raw(player);
        return magic == null ? null : new PlayerMagicState(player, magic);
    }

    public static float getMana(Player player) {
        return requireMagic(player).getCastingResource().getAmount();
    }

    public static void setMana(Player player, float amount) {
        requireMagic(player).getCastingResource().setAmount(amount);
    }

    public static void addMana(Player player, float amount) {
        IPlayerMagic magic = requireMagic(player);
        magic.getCastingResource().setAmount(Math.min(getMana(player) + amount, magic.getCastingResource().getMaxAmount()));
    }

    public static void subtractMana(Player player, float amount) {
        requireMagic(player).getCastingResource().setAmount(Math.max(getMana(player) - amount, 0));
    }

    @Nullable
    @HideFromJS
    public static IPlayerProgression rawProgression(Player player) {
        return player.getCapability(PlayerProgressionProvider.PROGRESSION).resolve().orElse(null);
    }

    @Nullable
    @Info("Return the typed player progression facade for this player.")
    public static PlayerProgressionState progressionOf(Player player) {
        IPlayerProgression progression = rawProgression(player);
        return progression == null ? null : new PlayerProgressionState(player, progression);
    }

    @Nullable
    @HideFromJS
    public static IPlayerProgression getProgressionCao(Player player) {
        return rawProgression(player);
    }

    private static IPlayerMagic requireMagic(Player player) {
        IPlayerMagic magic = raw(player);
        if (magic == null) {
            throw new IllegalStateException("Player magic capability is not available for " + player.getScoreboardName());
        }
        return magic;
    }
}
