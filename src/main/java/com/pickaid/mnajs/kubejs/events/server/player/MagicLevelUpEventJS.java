package com.pickaid.mnajs.kubejs.events.server.player;

import com.mna.api.events.PlayerMagicLevelUpEvent;
import dev.latvian.mods.kubejs.player.PlayerEventJS;
import net.minecraft.world.entity.player.Player;

public class MagicLevelUpEventJS extends PlayerEventJS {
    PlayerMagicLevelUpEvent event;

    public MagicLevelUpEventJS (PlayerMagicLevelUpEvent event) {
        this.event = event;
    }

    public int getMagicLevel() {
        return this.event.getMagicLevel();
    }

    @Override
    public Player getEntity() {
        return this.event.getPlayer();
    }
}
