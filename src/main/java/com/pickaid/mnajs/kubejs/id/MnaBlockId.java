package com.pickaid.mnajs.kubejs.id;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public record MnaBlockId(ResourceLocation location) implements MnaTypedId {
    public MnaBlockId {
        Objects.requireNonNull(location, "location");
        location = MnaIds.normalize(location, "minecraft", null);
    }

    public static MnaBlockId of(ResourceLocation location) {
        return new MnaBlockId(location);
    }

    public static MnaBlockId parse(Object value) {
        if (value instanceof MnaBlockId id) {
            return id;
        }
        if (value instanceof Block block) {
            ResourceLocation id = ForgeRegistries.BLOCKS.getKey(block);
            if (id == null) {
                throw new IllegalArgumentException("Unknown block registry id for " + block);
            }
            return of(id);
        }
        if (value instanceof CharSequence chars && chars.toString().trim().startsWith("#")) {
            throw new IllegalArgumentException("blockId does not accept tags: " + chars);
        }
        return new MnaBlockId(MnaIds.parse(value, "blockId", "minecraft", null));
    }

    @Override
    public String toString() {
        return id();
    }
}
