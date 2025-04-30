package com.pickaid.mnajs.kubejs.events.server.spell;

import com.mna.api.events.SpellCastEvent;
import com.mna.api.spells.base.ISpellDefinition;
import com.mna.api.spells.targeting.SpellContext;
import com.mna.api.spells.targeting.SpellSource;
import dev.latvian.mods.kubejs.entity.EntityEventJS;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class CastEventJS extends EntityEventJS {
    SpellCastEvent event;

    public CastEventJS(SpellCastEvent event) {
        this.event = event;
    }

    public ISpellDefinition getSpell() {
        return this.event.getSpell();
    }

    public SpellSource getSource() {
        return this.event.getSource();
    }

    @Nullable
    public SpellContext getContext() {
        return this.event.getContext();
    }

    public ItemStack getStack() {
        return this.event.getStack();
    }

    @Override
    public Entity getEntity() {
        return getSource().getCaster();
    }
}
