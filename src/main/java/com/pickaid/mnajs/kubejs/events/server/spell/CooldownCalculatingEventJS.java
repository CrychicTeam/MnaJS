package com.pickaid.mnajs.kubejs.events.server.spell;

import com.mna.api.events.SpellCooldownCalculatingEvent;
import com.mna.api.spells.base.ISpellDefinition;
import dev.latvian.mods.kubejs.player.PlayerEventJS;
import net.minecraft.world.entity.player.Player;

public class CooldownCalculatingEventJS extends PlayerEventJS {
    SpellCooldownCalculatingEvent event;

    public CooldownCalculatingEventJS(SpellCooldownCalculatingEvent event) {
        this.event = event;
    }

    public ISpellDefinition getSpell() {
        return this.event.getSpell();
    }

    public int getCooldown() {
        return this.event.getCooldown();
    }

    public void setCooldown(int value) {
        this.event.setCooldown(value);
    }

    @Override
    public Player getEntity() {
        return this.event.getCaster();
    }
}
