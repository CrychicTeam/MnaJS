package com.pickaid.mnajs.kubejs.id;

import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public record MnaSoundId(ResourceLocation location) implements MnaTypedId {
    public MnaSoundId {
        Objects.requireNonNull(location, "location");
        location = MnaIds.normalize(location, "minecraft", null);
    }

    public static MnaSoundId of(ResourceLocation location) {
        return new MnaSoundId(location);
    }

    public static MnaSoundId parse(Object value) {
        if (value instanceof MnaSoundId id) {
            return id;
        }
        return new MnaSoundId(MnaIds.parse(value, "soundId", "minecraft", null));
    }

    @Override
    public String toString() {
        return id();
    }
}
