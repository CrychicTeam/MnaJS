package com.pickaid.mnajs.kubejs.events.server.spell;

import com.mna.api.events.ComponentApplyingEvent;
import com.mna.api.spells.parts.SpellEffect;
import com.mna.api.spells.targeting.SpellContext;
import com.mna.api.spells.targeting.SpellSource;
import com.mna.api.spells.targeting.SpellTarget;
import dev.latvian.mods.kubejs.event.EventJS;

public class ComponentApplyingEventJS extends EventJS {
    ComponentApplyingEvent event;

    public ComponentApplyingEventJS(ComponentApplyingEvent event) {
        this.event = event;
    }

    public SpellSource getSource() {
        return this.event.getSource();
    }

    public SpellContext getContext() {
        return this.event.getContext();
    }

    public SpellTarget getTarget() {
        return this.event.getTarget();
    }

    public SpellEffect getComponent() {
        return this.event.getComponent();
    }
}
