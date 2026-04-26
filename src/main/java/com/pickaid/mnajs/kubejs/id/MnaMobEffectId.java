package com.pickaid.mnajs.kubejs.id;

import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public record MnaMobEffectId(ResourceLocation location) implements MnaTypedId {
    public MnaMobEffectId {
        Objects.requireNonNull(location, "location");
        location = MnaIds.normalize(location, "minecraft", null);
    }

    public static MnaMobEffectId of(ResourceLocation location) {
        return new MnaMobEffectId(location);
    }

    public static MnaMobEffectId parse(Object value) {
        if (value instanceof MnaMobEffectId id) {
            return id;
        }
        return new MnaMobEffectId(MnaIds.parse(value, "mobEffectId", "minecraft", null));
    }

    @Override
    public String toString() {
        return id();
    }
}
