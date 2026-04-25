package com.pickaid.mnajs.util;

import dev.latvian.mods.kubejs.core.ItemKJS;
import dev.latvian.mods.kubejs.core.ItemStackKJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class KubeJSCompat {
    private KubeJSCompat() {
    }

    public static ResourceLocation itemId(Item item) {
        return ((ItemKJS) (Object) item).kjs$getIdLocation();
    }

    public static String itemIdString(Item item) {
        return ((ItemKJS) (Object) item).kjs$getId();
    }

    public static ResourceLocation itemId(ItemStack stack) {
        return ((ItemStackKJS) (Object) stack).kjs$getIdLocation();
    }

    public static String itemIdString(ItemStack stack) {
        return ((ItemStackKJS) (Object) stack).kjs$getId();
    }
}
