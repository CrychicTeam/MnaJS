package com.pickaid.mnajs.kubejs.events.server.runforge;

import com.mna.api.events.RunicAnvilItemUsedEvent;
import dev.latvian.mods.kubejs.player.PlayerEventJS;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class RunicAnvilItemUsedEventJS extends PlayerEventJS {
    RunicAnvilItemUsedEvent event;

    public RunicAnvilItemUsedEventJS(RunicAnvilItemUsedEvent event) {
        this.event = event;
    }

    public ItemStack getPattern(){
        return this.event.pattern;
    }

    public ItemStack getMaterial(){
        return this.event.material;
    }

    public ItemStack getCatalyst(){
        return this.event.catalyst;
    }


    @Override
    public Player getEntity() {
        return this.event.getPlayer();
    }
}
