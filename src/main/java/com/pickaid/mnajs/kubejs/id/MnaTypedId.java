package com.pickaid.mnajs.kubejs.id;

import net.minecraft.resources.ResourceLocation;

public interface MnaTypedId {
    ResourceLocation location();

    default String id() {
        return location().toString();
    }
}
