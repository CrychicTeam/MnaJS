package com.pickaid.mnajs.kubejs.events.server;

import com.mna.api.affinity.Affinity;
import com.mna.api.events.AffinityChangedEvent;
import dev.latvian.mods.kubejs.player.PlayerEventJS;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class AffinityChangedEventJS extends PlayerEventJS {
    private final Player player;
    private final Affinity affinity;
    private final float currentAmount;
    private float shift;

    public AffinityChangedEventJS(AffinityChangedEvent event) {
        this.player = event.getPlayer();
        this.affinity = event.getAffinity();
        this.currentAmount = event.getCurrentAmount();
        this.shift = event .getShift();
    }

    @Override
    @Nullable
    public Player getEntity() {
        return player;
    }

    public Affinity getAffinity() {
        return affinity;
    }

    public float getCurrentAmount() {
        return currentAmount;
    }

    public float getShift() {
        return shift;
    }
}
