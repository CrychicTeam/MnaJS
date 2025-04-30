package com.pickaid.mnajs.kubejs.events.server.player;

import com.mna.api.events.GenericProgressionEvent;
import dev.latvian.mods.kubejs.player.PlayerEventJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class GenericProgressionEventJS extends PlayerEventJS {
    GenericProgressionEvent event;

    public GenericProgressionEventJS(GenericProgressionEvent event) {
        this.event = event;
    }

    @Override
    public Player getEntity() {
        return this.event.getPlayer();
    }

    public ResourceLocation getId() {
        return this.event.getEventType();
    }
}
