package com.pickaid.mnajs.kubejs.id;

import com.mna.api.entities.construct.ConstructMaterial;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public record MnaConstructMaterialId(ResourceLocation location) implements MnaTypedId {
    public MnaConstructMaterialId {
        Objects.requireNonNull(location, "location");
        location = MnaIds.normalize(location, "mna", null);
    }

    public static MnaConstructMaterialId of(ResourceLocation location) {
        return new MnaConstructMaterialId(location);
    }

    public static MnaConstructMaterialId parse(Object value) {
        if (value instanceof MnaConstructMaterialId id) {
            return id;
        }
        if (value instanceof ConstructMaterial material) {
            MnaConstructMaterialId wrapped = MnaTypedIdLookups.wrapConstructMaterial(material);
            if (wrapped != null) {
                return wrapped;
            }
        }
        return new MnaConstructMaterialId(MnaIds.parse(value, "constructMaterialId", "mna", null));
    }

    @Override
    public String toString() {
        return id();
    }
}
