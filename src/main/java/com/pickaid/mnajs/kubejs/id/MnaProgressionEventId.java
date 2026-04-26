package com.pickaid.mnajs.kubejs.id;

import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public record MnaProgressionEventId(ResourceLocation location) implements MnaTypedId {
    public MnaProgressionEventId {
        Objects.requireNonNull(location, "location");
        location = MnaIds.normalize(location, "mna", null);
    }

    public static MnaProgressionEventId of(ResourceLocation location) {
        return new MnaProgressionEventId(location);
    }

    public static MnaProgressionEventId parse(Object value) {
        if (value instanceof MnaProgressionEventId id) {
            return id;
        }
        return new MnaProgressionEventId(MnaIds.parse(value, "progressionEventId", "mna", null));
    }

    @Override
    public String toString() {
        return id();
    }
}
