package com.pickaid.mnajs.kubejs.events.server.player;

import com.mna.api.events.MagicXPGainedEvent;
import dev.latvian.mods.kubejs.player.PlayerEventJS;
import net.minecraft.world.entity.player.Player;

public class MagicXPGainedEventJS extends PlayerEventJS {
    MagicXPGainedEvent event;

    public MagicXPGainedEventJS(MagicXPGainedEvent event) {
        this.event = event;
    }

    @Override
    public Player getEntity() {
        return this.event.getPlayer();
    }

    public int getAmount() {
        return this.event.getAmount();
    }

    public void setAmount(int amount) {
        this.event.setAmount(amount);
    }
}
