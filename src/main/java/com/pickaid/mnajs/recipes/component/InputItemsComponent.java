package com.pickaid.mnajs.recipes.component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

public class InputItemsComponent {
    public static RecipeComponent<Item[]> INPUT_ITEMS = new RecipeComponent<>() {

        @Override
        public Class<?> componentClass() {
            return Item[].class;
        }

        @Override
        public JsonElement write(RecipeJS recipe, Item[] value) {
            var json = new JsonArray();
            for (Item item : value) {
                json.add(item.getDescriptionId());
            }
            return json;
        }

        @Override
        public Item[] read(RecipeJS recipe, Object from) {
            if (from instanceof String[] strings) {
                var items = new Item[strings.length];
                for (int i = 0; i < strings.length; i++) {
                    items[i] = ForgeRegistries.ITEMS.getValue(new ResourceLocation(strings[i])) == null ? ItemStack.EMPTY.getItem() : ForgeRegistries.ITEMS.getValue(new ResourceLocation(strings[i]));
                    if (items[i] == null) {
                        items[i] = ItemStack.EMPTY.getItem();
                    }
                }
                return items;
            } else if (from instanceof ItemStack stack) {
                return new Item[]{stack.getItem()};
            } else if (from instanceof Ingredient ingredient) {
                if (ingredient.getItems().length > 0) {
                    return new Item[]{ingredient.getItems()[0].getItem()};
                }
                return new Item[0];
            } else if (from instanceof Item item) {
                return new Item[]{item};
            }
            return new Item[]{ItemStack.EMPTY.getItem()};
        }
    };

}
