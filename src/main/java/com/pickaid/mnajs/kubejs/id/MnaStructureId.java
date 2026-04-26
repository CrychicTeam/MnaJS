package com.pickaid.mnajs.kubejs.id;

import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public record MnaStructureId(ResourceLocation location) implements MnaTypedId {
    public MnaStructureId {
        Objects.requireNonNull(location, "location");
        location = MnaIds.normalize(location, "mna", null);
    }

    public static MnaStructureId of(ResourceLocation location) {
        return new MnaStructureId(location);
    }

    public static MnaStructureId parse(Object value) {
        if (value instanceof MnaStructureId id) {
            return id;
        }
        return new MnaStructureId(MnaIds.parse(value, "structureId", "mna", null));
    }

    @Override
    public String toString() {
        return id();
    }
}
