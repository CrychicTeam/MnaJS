package com.pickaid.mnajs.kubejs.id;

import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public record MnaCastingResourceId(ResourceLocation location) implements MnaTypedId {
    public MnaCastingResourceId {
        Objects.requireNonNull(location, "location");
        location = MnaIds.normalize(location, "mna", null);
    }

    public static MnaCastingResourceId of(ResourceLocation location) {
        return new MnaCastingResourceId(location);
    }

    public static MnaCastingResourceId parse(Object value) {
        if (value instanceof MnaCastingResourceId id) {
            return id;
        }
        return new MnaCastingResourceId(MnaIds.parse(value, "castingResourceId", "mna", null));
    }

    @Override
    public String toString() {
        return id();
    }
}
