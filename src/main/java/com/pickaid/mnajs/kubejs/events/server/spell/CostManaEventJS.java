package com.pickaid.mnajs.kubejs.events.server.spell;

import com.mna.api.events.CalculatingManaCostEvent;
import com.mna.api.spells.base.ISpellDefinition;
import dev.latvian.mods.kubejs.player.PlayerEventJS;
import net.minecraft.world.entity.player.Player;

public class CostManaEventJS extends PlayerEventJS {
    private final CalculatingManaCostEvent event;

    public CostManaEventJS(CalculatingManaCostEvent event) {
        this.event = event;
    }

    @Override
    public Player getEntity() {
        return (Player) event.getCaster();
    }

    public float getCost() {
        return event.getManaCost();
    }

    public ISpellDefinition getSpell() {
        return event.getSpell();
    }

    public void setCost(float amount) {
        this.event.setManaCost(amount);
    }
}
