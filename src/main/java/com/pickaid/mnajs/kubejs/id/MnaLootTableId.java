package com.pickaid.mnajs.kubejs.id;

import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public record MnaLootTableId(ResourceLocation location) implements MnaTypedId {
    public MnaLootTableId {
        Objects.requireNonNull(location, "location");
        location = MnaIds.normalize(location, "minecraft", null);
    }

    public static MnaLootTableId of(ResourceLocation location) {
        return new MnaLootTableId(location);
    }

    public static MnaLootTableId parse(Object value) {
        if (value instanceof MnaLootTableId id) {
            return id;
        }
        return new MnaLootTableId(MnaIds.parse(value, "lootTableId", "minecraft", null));
    }

    @Override
    public String toString() {
        return id();
    }
}
