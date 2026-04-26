package com.pickaid.mnajs.kubejs.id;

import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public record MnaRitualId(ResourceLocation location) implements MnaTypedId {
    public MnaRitualId {
        Objects.requireNonNull(location, "location");
        location = MnaIds.normalize(location, "mna", null);
    }

    public static MnaRitualId of(ResourceLocation location) {
        return new MnaRitualId(location);
    }

    public static MnaRitualId parse(Object value) {
        if (value instanceof MnaRitualId id) {
            return id;
        }
        return new MnaRitualId(MnaIds.parse(value, "ritualId", "mna", null));
    }

    @Override
    public String toString() {
        return id();
    }
}
