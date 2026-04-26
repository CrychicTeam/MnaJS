package com.pickaid.mnajs.util;

import com.mna.api.affinity.Affinity;
import com.mna.api.capabilities.IPlayerMagic;
import com.mna.api.capabilities.IPlayerProgression;
import com.pickaid.mnajs.kubejs.id.MnaCastingResourceId;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.Objects;

public final class PlayerMagicState {
    private final Player player;
    private final IPlayerMagic magic;

    PlayerMagicState(Player player, IPlayerMagic magic) {
        this.player = Objects.requireNonNull(player, "player");
        this.magic = Objects.requireNonNull(magic, "magic");
    }

    @Info("Get the current mana value for this player.")
    public float getMana() {
        return magic.getCastingResource().getAmount();
    }

    @Info(value = "Set the player's mana value.", params = {
            @Param(name = "amount", value = "Mana amount after clamping between zero and max mana.")
    })
    public void setMana(float amount) {
        magic.getCastingResource().setAmount(Math.max(0.0F, Math.min(amount, getMaxMana())));
    }

    @Info(value = "Add mana to this player.", params = {
            @Param(name = "amount", value = "Mana amount to add.")
    })
    public void addMana(float amount) {
        setMana(getMana() + amount);
    }

    @Info(value = "Subtract mana from this player.", params = {
            @Param(name = "amount", value = "Mana amount to subtract.")
    })
    public void subtractMana(float amount) {
        setMana(getMana() - amount);
    }

    @Info("Get the current maximum mana for this player.")
    public float getMaxMana() {
        return magic.getCastingResource().getMaxAmount();
    }

    @Info("Get the player's magic level.")
    public int getMagicLevel() {
        return magic.getMagicLevel();
    }

    @Info(value = "Set the player's magic level.", params = {
            @Param(name = "level", value = "Target magic level.")
    })
    public void setMagicLevel(int level) {
        magic.setMagicLevel(player, Math.max(0, level));
    }

    @Info("Get the player's current magic XP.")
    public int getMagicXP() {
        return magic.getMagicXP();
    }

    @Info(value = "Set the player's current magic XP.", params = {
            @Param(name = "xp", value = "Target XP value.")
    })
    public void setMagicXP(int xp) {
        magic.setMagicXP(Math.max(0, xp));
    }

    @Info(value = "Add magic XP to the player using the progression capability when available.", params = {
            @Param(name = "xp", value = "XP amount to add.")
    })
    public void addMagicXP(int xp) {
        IPlayerProgression progression = PlayerUtil.rawProgression(player);
        if (progression != null) {
            magic.addMagicXP(xp, player, progression);
        } else {
            setMagicXP(getMagicXP() + xp);
        }
    }

    @Info("Return whether magic has been unlocked for this player.")
    public boolean isMagicUnlocked() {
        return magic.isMagicUnlocked();
    }

    @Info("Unlock magic for this player.")
    public void unlockMagic() {
        magic.unlockMagic();
    }

    @Info(value = "Get the affinity depth for this player.", params = {
            @Param(name = "affinity", value = "Affinity to inspect.")
    })
    public float getAffinityDepth(Affinity affinity) {
        return magic.getAffinityDepth(affinity);
    }

    @Info(value = "Set the affinity depth for this player.", params = {
            @Param(name = "affinity", value = "Affinity to modify."),
            @Param(name = "amount", value = "Target affinity depth.")
    })
    public void setAffinityDepth(Affinity affinity, float amount) {
        magic.setAffinityDepth(affinity, amount);
    }

    @Info("Get the typed id of the player's active casting resource.")
    public MnaCastingResourceId getCastingResourceId() {
        return MnaCastingResourceId.of(magic.getCastingResource().getRegistryName());
    }

    @Info(value = "Set the player's active casting resource type.", params = {
            @Param(name = "resourceId", value = "Casting resource id such as mna:mana.")
    })
    public void setCastingResourceId(MnaCastingResourceId resourceId) {
        magic.setCastingResourceType(resourceId.location());
    }

    @Info("Get the typed casting resource facade for this player.")
    public CastingResourceState getCastingResource() {
        return new CastingResourceState(player, magic.getCastingResource());
    }

    @Nullable
    @Info("Get the typed player progression facade for this player.")
    public PlayerProgressionState getProgression() {
        return PlayerUtil.progressionOf(player);
    }
}
