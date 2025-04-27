package com.pickaid.mnajs.recipes.component;

import com.mna.api.affinity.Affinity;

public class PowerProvided {
    public final Affinity affinity;
    public final float amount;

    public PowerProvided(Affinity affinity, float amount) {
        this.affinity = affinity;
        this.amount = amount;
    }
}