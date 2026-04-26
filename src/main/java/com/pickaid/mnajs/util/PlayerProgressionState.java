package com.pickaid.mnajs.util;

import com.mna.api.capabilities.IPlayerProgression;
import com.pickaid.mnajs.kubejs.id.MnaFactionId;
import com.pickaid.mnajs.kubejs.id.MnaProgressionEventId;
import com.pickaid.mnajs.kubejs.id.MnaTypedIdLookups;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class PlayerProgressionState {
    private final Player player;
    private final IPlayerProgression progression;

    PlayerProgressionState(Player player, IPlayerProgression progression) {
        this.player = Objects.requireNonNull(player, "player");
        this.progression = Objects.requireNonNull(progression, "progression");
    }

    @Info("Get the player's current progression tier.")
    public int getTier() {
        return progression.getTier();
    }

    @Info(value = "Set the player's progression tier using the default M&A update path.", params = {
            @Param(name = "tier", value = "Target progression tier.")
    })
    public void setTier(int tier) {
        progression.setTier(Math.max(0, tier), player);
    }

    @Info("Get the player's current tier progress in their current level.")
    public float getTierProgress() {
        return progression.getTierProgress(player.level());
    }

    @Info("Get the completed progression step ids for this player.")
    public List<MnaProgressionEventId> getCompletedSteps() {
        return Arrays.stream(MnaTypedIdLookups.wrapProgressionEvents(progression.getCompletedProgressionSteps()))
                .toList();
    }

    @Info(value = "Return whether the given progression step has already been completed.", params = {
            @Param(name = "stepId", value = "Progression event id such as mna:open_cache.")
    })
    public boolean hasCompletedStep(MnaProgressionEventId stepId) {
        return progression.getCompletedProgressionSteps().contains(stepId.location());
    }

    @Info(value = "Mark a progression step as completed for this player.", params = {
            @Param(name = "stepId", value = "Progression event id such as mna:open_cache.")
    })
    public void addCompletedStep(MnaProgressionEventId stepId) {
        progression.addTierProgressionComplete(stepId.location());
    }

    @Nullable
    @Info("Get the allied faction id for this player, if one is set.")
    public MnaFactionId getAlliedFactionId() {
        if (!progression.hasAlliedFaction()) {
            return null;
        }
        return MnaTypedIdLookups.wrapFaction(progression.getAlliedFaction());
    }

    @Info(value = "Set the allied faction for this player.", params = {
            @Param(name = "factionId", value = "Faction id such as mna:council.")
    })
    public void setAlliedFaction(MnaFactionId factionId) {
        progression.setAlliedFaction(MnaTypedIdLookups.requireFaction(factionId, "factionId"), player);
    }

    @Info("Get the player's current faction standing.")
    public int getFactionStanding() {
        return progression.getFactionStanding();
    }

    @Info(value = "Set the player's faction standing.", params = {
            @Param(name = "standing", value = "New faction standing value.")
    })
    public void setFactionStanding(int standing) {
        progression.setFactionStanding(standing);
    }

    @Info(value = "Increase the player's faction standing.", params = {
            @Param(name = "amount", value = "Amount to add.")
    })
    public void increaseFactionStanding(int amount) {
        progression.increaseFactionStanding(amount);
    }

    @Info("Return whether this player can currently be raided.")
    public boolean canBeRaided() {
        return progression.canBeRaided(player);
    }

    @Info(value = "Return whether this player can be raided by the given faction.", params = {
            @Param(name = "factionId", value = "Faction id such as mna:demon.")
    })
    public boolean canBeRaidedBy(MnaFactionId factionId) {
        return progression.canBeRaided(MnaTypedIdLookups.requireFaction(factionId, "factionId"), player);
    }

    @Info(value = "Get the relative raid strength for a specific faction.", params = {
            @Param(name = "factionId", value = "Faction id such as mna:undead.")
    })
    public int getRelativeRaidStrength(MnaFactionId factionId) {
        return progression.getRelativeRaidStrength(MnaTypedIdLookups.requireFaction(factionId, "factionId"), player);
    }

    @Info(value = "Get the current raid chance for a faction.", params = {
            @Param(name = "factionId", value = "Faction id such as mna:fey.")
    })
    public double getRaidChance(MnaFactionId factionId) {
        return progression.getRaidChance(MnaTypedIdLookups.requireFaction(factionId, "factionId"));
    }

    @Info(value = "Set the raid chance for a faction.", params = {
            @Param(name = "factionId", value = "Faction id such as mna:fey."),
            @Param(name = "chance", value = "Raid chance value to store.")
    })
    public void setRaidChance(MnaFactionId factionId, double chance) {
        progression.setRaidChance(MnaTypedIdLookups.requireFaction(factionId, "factionId"), chance);
    }

    @Info(value = "Force an immediate raid from the given faction.", params = {
            @Param(name = "factionId", value = "Faction id such as mna:council.")
    })
    public void forceRaid(MnaFactionId factionId) {
        progression.raidImmediate(MnaTypedIdLookups.requireFaction(factionId, "factionId"));
    }

    @Info("Return whether a forced raid is currently queued.")
    public boolean hasForcedRaid() {
        return progression.hasForceRaid();
    }

    @Nullable
    @Info("Get the faction id of the current forced raid, if present.")
    public MnaFactionId getForcedRaidFactionId() {
        return MnaTypedIdLookups.wrapFaction(progression.getForceRaid());
    }

    @Info("Clear the current forced raid.")
    public void clearForceRaid() {
        progression.clearForceRaid();
    }
}
