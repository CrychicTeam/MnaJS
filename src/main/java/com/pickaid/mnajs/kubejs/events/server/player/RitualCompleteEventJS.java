package com.pickaid.mnajs.kubejs.events.server.player;

import com.mna.api.events.RitualCompleteEvent;
import com.mna.api.recipes.IRitualRecipe;
import com.mna.api.rituals.RitualEffect;
import dev.latvian.mods.kubejs.player.PlayerEventJS;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class RitualCompleteEventJS extends PlayerEventJS {
    public RitualCompleteEvent event;
    private IRitualRecipe ritual;
    private Player caster;
    private List<ItemStack> collectedReagents;
    private NonNullList<RitualEffect> handlers;
    private BlockPos center;

    public RitualCompleteEventJS(RitualCompleteEvent event) {
        this.ritual = event.getRitual();
        this.caster = event.getCaster();
        this.collectedReagents = event.getCollectedReagents();
        this.handlers = event.getHandlers();
        this.center = event.getCenter();
    }

    @Override
    public Player getEntity() {
        return caster;
    }

    public BlockPos getCenter() {
        return center;
    }

    public IRitualRecipe getRitual() {
        return ritual;
    }

    public List<ItemStack> getCollectedReagents() {
        return collectedReagents;
    }

    public NonNullList<RitualEffect> getHandlers() {
        return handlers;
    }

    public RitualCompleteEvent getEvent() {
        return event;
    }
}
