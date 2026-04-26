package com.pickaid.mnajs.kubejs.id;

import com.mna.api.spells.parts.Modifier;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public record MnaModifierId(ResourceLocation location) implements MnaTypedId {
    public MnaModifierId {
        Objects.requireNonNull(location, "location");
        location = MnaIds.normalize(location, "mna", null);
    }

    public static MnaModifierId of(ResourceLocation location) {
        return new MnaModifierId(location);
    }

    public static MnaModifierId parse(Object value) {
        if (value instanceof MnaModifierId id) {
            return id;
        }
        if (value instanceof Modifier modifier) {
            try {
                MnaModifierId wrapped = MnaTypedIdLookups.wrapModifier(modifier);
                if (wrapped != null) {
                    return wrapped;
                }
            } catch (NullPointerException ignored) {
            }
        }
        return new MnaModifierId(MnaIds.parse(value, "modifierId", "mna", null));
    }

    @Override
    public String toString() {
        return id();
    }
}
