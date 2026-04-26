package com.pickaid.mnajs.kubejs.id;

import com.mna.api.entities.construct.ai.ConstructTask;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public record MnaConstructTaskId(ResourceLocation location) implements MnaTypedId {
    public MnaConstructTaskId {
        Objects.requireNonNull(location, "location");
        location = MnaIds.normalize(location, "mna", null);
    }

    public static MnaConstructTaskId of(ResourceLocation location) {
        return new MnaConstructTaskId(location);
    }

    public static MnaConstructTaskId parse(Object value) {
        if (value instanceof MnaConstructTaskId id) {
            return id;
        }
        if (value instanceof ConstructTask task) {
            try {
                MnaConstructTaskId wrapped = MnaTypedIdLookups.wrapConstructTask(task);
                if (wrapped != null) {
                    return wrapped;
                }
            } catch (NullPointerException ignored) {
            }
        }
        return new MnaConstructTaskId(MnaIds.parse(value, "constructTaskId", "mna", null));
    }

    @Override
    public String toString() {
        return id();
    }
}
