package com.pickaid.mnajs.kubejs.id;

import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public record MnaSpellEffectId(ResourceLocation location) implements MnaTypedId {
    public MnaSpellEffectId {
        Objects.requireNonNull(location, "location");
        location = MnaIds.normalize(location, "mna", null);
    }

    public static MnaSpellEffectId of(ResourceLocation location) {
        return new MnaSpellEffectId(location);
    }

    public static MnaSpellEffectId parse(Object value) {
        if (value instanceof MnaSpellEffectId id) {
            return id;
        }
        return new MnaSpellEffectId(MnaIds.parse(value, "spellEffectId", "mna", null));
    }

    @Override
    public String toString() {
        return id();
    }
}
