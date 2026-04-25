package com.pickaid.mnajs.kubejs.id;

import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public record MnaFactionId(ResourceLocation location) implements MnaTypedId {
    public MnaFactionId {
        Objects.requireNonNull(location, "location");
        location = MnaIds.normalize(location, "mna", null);
    }

    public static MnaFactionId of(ResourceLocation location) {
        return new MnaFactionId(location);
    }

    public static MnaFactionId parse(Object value) {
        if (value instanceof MnaFactionId id) {
            return id;
        }
        return new MnaFactionId(MnaIds.parse(value, "factionId", "mna", null));
    }

    @Override
    public String toString() {
        return id();
    }
}
