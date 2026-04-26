package com.pickaid.mnajs.recipes.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.pickaid.mnajs.util.KubeJSCompat;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;

public interface ItemComponent {
    RecipeComponent<Item> ITEM = new RecipeComponent<Item>() {
        @Override
        public Class<?> componentClass() {
            return Item.class;
        }

        @Override
        public JsonElement write(RecipeJS recipe, Item value) {
            return new JsonPrimitive(KubeJSCompat.itemId(value).toString());
        }

        @Override
        public Item read(RecipeJS recipe, Object from) {
            if (from instanceof JsonElement element) {
                if (element.isJsonPrimitive()) {
                    return read(recipe, element.getAsString());
                }
                if (element.isJsonObject()) {
                    JsonObject object = element.getAsJsonObject();
                    if (object.has("item")) {
                        return read(recipe, object.get("item").getAsString());
                    }
                }
            }
            if (from instanceof String string) {
                return ForgeRegistries.ITEMS.getValue(ResourceLocation.parse(string)) == null ? ItemStack.EMPTY.getItem() : ForgeRegistries.ITEMS.getValue(ResourceLocation.parse(string));
            } else if (from instanceof ItemStack stack) {
                return stack.getItem();
            } else if (from instanceof Ingredient ingredient) {
                return Arrays.stream(ingredient.getItems()).findFirst().get().getItem();
            }
            return ItemStack.EMPTY.getItem();
        }
    };
}
