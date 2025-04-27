package com.pickaid.mnajs.recipes.schema.Basic;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mna.tools.NBTUtilities;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;

public interface ItemBaseSchema {
    RecipeComponent<Item> ITEM = new RecipeComponent<Item>() {
        @Override
        public Class<?> componentClass() {
            return Item.class;
        }

        @Override
        public JsonElement write(RecipeJS recipe, Item value) {
            return new JsonPrimitive(value.kjs$getIdLocation().toString());
        }

        @Override
        public Item read(RecipeJS recipe, Object from) {
            if (from instanceof String string) {
                return ForgeRegistries.ITEMS.getValue(new ResourceLocation(string)) == null ? ItemStack.EMPTY.getItem() : ForgeRegistries.ITEMS.getValue(new ResourceLocation(string));
            } else if (from instanceof ItemStack stack) {
                return stack.getItem();
            } else if (from instanceof Ingredient ingredient) {
                return Arrays.stream(ingredient.getItems()).findFirst().get().getItem();
            }
            return ItemStack.EMPTY.getItem();
        }
    };

    RecipeKey<Item> INPUT = ITEM.key("input");
    RecipeComponent<ItemStack> ITEMSTACK = new RecipeComponent<>() {
        @Override
        public Class<?> componentClass() {
            return ItemStack.class;
        }

        @Override
        public JsonElement write(RecipeJS recipe, ItemStack value) {
            return new JsonPrimitive(value.getItem().kjs$getId());
        }

        @Override
        public ItemStack read(RecipeJS recipe, Object from) {
            if (from instanceof String string) {
                var item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(string));
                if (item != null) {
                    return new ItemStack(item);
                }
            } else if (from instanceof JsonObject object) {
                if (object.has("item")) {
                    var item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(object.get("item").getAsString()));
                    if (item != null) {
                        ItemStack itemStack = new ItemStack(item);
                        if (object.has("quantity")) {
                            itemStack.setCount(Math.max(1, object.get("quantity").getAsInt()));
                        }
                        if (object.has("data")) {
                            CompoundTag tag = NBTUtilities.fromJSON(object.get("data").getAsJsonObject());
                            itemStack.setTag(tag);
                        }
                        return itemStack;
                    }
                }
            } else if (from instanceof Item item) {
                return new ItemStack(item);
            } else if (from instanceof ItemStack itemStack) {
                return itemStack.copy();
            }
            return ItemStack.EMPTY;
        }
    };
    RecipeKey<ItemStack> OUTPUT = ITEMSTACK.key("output");
    RecipeKey<Integer> QUANTITY = NumberComponent.INT.key("outputQuantity").optional(1).exclude();
}
