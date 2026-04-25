package com.pickaid.mnajs.kubejs.id;

import com.pickaid.mnajs.util.KubeJSCompat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public record MnaItemId(ResourceLocation location) implements MnaTypedId {
    public MnaItemId {
        Objects.requireNonNull(location, "location");
        location = MnaIds.normalize(location, "minecraft", null);
    }

    public static MnaItemId of(ResourceLocation location) {
        return new MnaItemId(location);
    }

    public static MnaItemId parse(Object value) {
        if (value instanceof MnaItemId id) {
            return id;
        }
        if (value instanceof ItemStack stack) {
            return of(KubeJSCompat.itemId(stack));
        }
        if (value instanceof Item item) {
            return of(KubeJSCompat.itemId(item));
        }
        if (value instanceof CharSequence chars && chars.toString().trim().startsWith("#")) {
            throw new IllegalArgumentException("itemId does not accept tags: " + chars);
        }
        return new MnaItemId(MnaIds.parse(value, "itemId", "minecraft", null));
    }

    @Override
    public String toString() {
        return id();
    }
}
