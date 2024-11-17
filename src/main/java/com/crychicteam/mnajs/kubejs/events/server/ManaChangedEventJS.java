package com.crychicteam.mnajs.kubejs.events.server;

import dev.latvian.mods.kubejs.player.PlayerEventJS;
import net.minecraft.world.entity.player.Player;

public class ManaChangedEventJS extends PlayerEventJS {
    private final Player player;
    private final float value;

    public ManaChangedEventJS(Player player, float amount) {
        this.player = player;
        this.value = amount;
    }

    @Override
    public Player getEntity() {
        return player;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {

    }
}
