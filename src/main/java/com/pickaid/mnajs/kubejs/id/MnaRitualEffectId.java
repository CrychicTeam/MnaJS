package com.pickaid.mnajs.kubejs.id;

import com.mna.api.rituals.RitualEffect;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public record MnaRitualEffectId(ResourceLocation location) implements MnaTypedId {
    public MnaRitualEffectId {
        Objects.requireNonNull(location, "location");
        location = MnaIds.normalize(location, "mna", null);
    }

    public static MnaRitualEffectId of(ResourceLocation location) {
        return new MnaRitualEffectId(location);
    }

    public static MnaRitualEffectId parse(Object value) {
        if (value instanceof MnaRitualEffectId id) {
            return id;
        }
        if (value instanceof RitualEffect effect) {
            try {
                MnaRitualEffectId wrapped = MnaTypedIdLookups.wrapRitualEffect(effect);
                if (wrapped != null) {
                    return wrapped;
                }
            } catch (NullPointerException ignored) {
            }
        }
        return new MnaRitualEffectId(MnaIds.parse(value, "ritualEffectId", "mna", null));
    }

    @Override
    public String toString() {
        return id();
    }
}
