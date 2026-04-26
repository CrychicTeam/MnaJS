package com.pickaid.mnajs.kubejs.events.server.player;

import com.mna.api.events.GenericProgressionEvent;
import com.pickaid.mnajs.kubejs.id.MnaProgressionEventId;
import dev.latvian.mods.kubejs.player.PlayerEventJS;
import dev.latvian.mods.rhino.util.HideFromJS;
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

    public MnaProgressionEventId getId() {
        return MnaProgressionEventId.of(this.event.getEventType());
    }

    @HideFromJS
    public ResourceLocation getRawId() {
        return this.event.getEventType();
    }
}
