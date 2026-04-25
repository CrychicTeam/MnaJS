package com.pickaid.mnajs.kubejs.texture;

import com.pickaid.mnajs.kubejs.id.MnaIds;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public record MnaTexture(ResourceLocation location) {
    public MnaTexture {
        Objects.requireNonNull(location, "location");
    }

    public static MnaTexture of(ResourceLocation location) {
        return new MnaTexture(location);
    }

    public static MnaTexture parse(Object value) {
        if (value instanceof MnaTexture texture) {
            return texture;
        }
        return new MnaTexture(MnaIds.parse(value, "texture"));
    }

    public String id() {
        return location.toString();
    }

    @Override
    public String toString() {
        return id();
    }
}
