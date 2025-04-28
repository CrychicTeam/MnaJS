package com.pickaid.mnajs.recipes.component;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mna.tools.NBTUtilities;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemStackComponent {
    public static RecipeComponent<ItemStack> ITEMSTACK = new RecipeComponent<>() {
        @Override
        public Class<?> componentClass() {
            return ItemStack.class;
        }

        @Override
        public JsonElement write(RecipeJS recipe, ItemStack value) {
            if (value.getTag() == null) {
                return new JsonPrimitive(value.getItem().kjs$getId());
            }
            var oldJson = new JsonObject();
            oldJson.addProperty("item", value.kjs$getIdLocation().toString());
            oldJson.addProperty("data", value.getTag().getAsString());
            var json = new JsonObject();
            json.add("output", oldJson);
            return json;
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
}
