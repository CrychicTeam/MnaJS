package com.pickaid.mnajs.kubejs.id;

import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public record MnaManaweavePatternId(ResourceLocation location) implements MnaTypedId {
    public MnaManaweavePatternId {
        Objects.requireNonNull(location, "location");
        location = MnaIds.normalize(location, "mna", "manaweave_patterns");
    }

    public static MnaManaweavePatternId of(ResourceLocation location) {
        return new MnaManaweavePatternId(location);
    }

    public static MnaManaweavePatternId parse(Object value) {
        if (value instanceof MnaManaweavePatternId id) {
            return id;
        }
        return new MnaManaweavePatternId(MnaIds.parse(value, "manaweavePatternId", "mna", "manaweave_patterns"));
    }

    @Override
    public String toString() {
        return id();
    }
}
