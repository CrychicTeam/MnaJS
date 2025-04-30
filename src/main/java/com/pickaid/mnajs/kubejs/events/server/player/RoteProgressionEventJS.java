package com.pickaid.mnajs.kubejs.events.server.player;

import com.mna.api.events.RoteProgressGainedEvent;
import com.mna.api.spells.base.ISpellComponent;
import dev.latvian.mods.kubejs.player.PlayerEventJS;
import net.minecraft.world.entity.player.Player;

public class RoteProgressionEventJS extends PlayerEventJS {
    RoteProgressGainedEvent event;

    public RoteProgressionEventJS(RoteProgressGainedEvent event) {
        this.event = event;
    }

    @Override
    public Player getEntity() {
        return this.event.getPlayer();
    }

    public ISpellComponent getPart() {
        return this.event.getPart();
    }

    public float getAmount() {
        return this.event.getAmount();
    }

    public void setAmount(float amount) {
        this.event.setAmount(amount);
    }
}
