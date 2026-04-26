package com.pickaid.mnajs.kubejs.id;

import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public record MnaAdvancementId(ResourceLocation location) implements MnaTypedId {
    public MnaAdvancementId {
        Objects.requireNonNull(location, "location");
        location = MnaIds.normalize(location, "minecraft", null);
    }

    public static MnaAdvancementId of(ResourceLocation location) {
        return new MnaAdvancementId(location);
    }

    public static MnaAdvancementId parse(Object value) {
        if (value instanceof MnaAdvancementId id) {
            return id;
        }
        return new MnaAdvancementId(MnaIds.parse(value, "advancementId", "minecraft", null));
    }

    @Override
    public String toString() {
        return id();
    }
}
