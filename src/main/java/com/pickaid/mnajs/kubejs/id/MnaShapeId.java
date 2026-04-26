package com.pickaid.mnajs.kubejs.id;

import com.mna.api.spells.parts.Shape;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public record MnaShapeId(ResourceLocation location) implements MnaTypedId {
    public MnaShapeId {
        Objects.requireNonNull(location, "location");
        location = MnaIds.normalize(location, "mna", null);
    }

    public static MnaShapeId of(ResourceLocation location) {
        return new MnaShapeId(location);
    }

    public static MnaShapeId parse(Object value) {
        if (value instanceof MnaShapeId id) {
            return id;
        }
        if (value instanceof Shape shape) {
            try {
                MnaShapeId wrapped = MnaTypedIdLookups.wrapShape(shape);
                if (wrapped != null) {
                    return wrapped;
                }
            } catch (NullPointerException ignored) {
            }
        }
        return new MnaShapeId(MnaIds.parse(value, "shapeId", "mna", null));
    }

    @Override
    public String toString() {
        return id();
    }
}
