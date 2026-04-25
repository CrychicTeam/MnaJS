package com.pickaid.mnajs.kubejs.id;

import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public record MnaCantripId(ResourceLocation location) implements MnaTypedId {
    public MnaCantripId {
        Objects.requireNonNull(location, "location");
        location = MnaIds.normalize(location, "mna", null);
    }

    public static MnaCantripId of(ResourceLocation location) {
        return new MnaCantripId(location);
    }

    public static MnaCantripId parse(Object value) {
        if (value instanceof MnaCantripId id) {
            return id;
        }
        return new MnaCantripId(MnaIds.parse(value, "cantripId", "mna", null));
    }

    @Override
    public String toString() {
        return id();
    }
}
