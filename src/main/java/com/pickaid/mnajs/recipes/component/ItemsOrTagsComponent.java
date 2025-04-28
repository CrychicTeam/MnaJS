package com.pickaid.mnajs.recipes.component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.mna.api.tools.MATags;
import com.mojang.datafixers.util.Either;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemsOrTagsComponent {
    public static RecipeComponent<List<Either<TagKey<Item>, Item>>> ITEMS_OR_TAGS_COMPONENT = new RecipeComponent<>() {

        @Override
        public Class<?> componentClass() {
            return List.class;
        }

        @Override
        public JsonElement write(RecipeJS recipe, List<Either<TagKey<Item>, Item>> values) {
            var json = new JsonArray();
            for (Either<TagKey<Item>, Item> value : values) {
                JsonElement element = value.map(
                        tagKey -> (JsonElement) new JsonPrimitive(tagKey.location().toString()),
                        item -> (JsonElement) new JsonPrimitive(item.kjs$getIdLocation().toString())
                );
                json.add(element);
            }
            return json;
        }

        @Override
        public List<Either<TagKey<Item>, Item>> read(RecipeJS recipe, Object from) {
            List<Either<TagKey<Item>, Item>> result = new ArrayList<>();
            try {
                System.out.println("Reading items from: " + from + " of type: " + (from != null ? from.getClass().getName() : "null"));

                if (from instanceof Collection<?> collection) {
                    for (Object obj : collection) {
                        Either<TagKey<Item>, Item> item = readSingleItem(obj);
                        if (item != null) {
                            result.add(item);
                        }
                    }
                } else if (from instanceof Object[] array) {
                    for (Object obj : array) {
                        Either<TagKey<Item>, Item> item = readSingleItem(obj);
                        if (item != null) {
                            result.add(item);
                        }
                    }
                } else {
                    Either<TagKey<Item>, Item> item = readSingleItem(from);
                    if (item != null) {
                        result.add(item);
                    }
                }
            } catch (Exception e) {
                System.err.println("Error reading items: " + e.getMessage());
                e.printStackTrace();
            }

            return result;
        }

        private Either<TagKey<Item>, Item> readSingleItem(Object from) {
            if (from == null) return null;

            try {
                if (from instanceof String string) {
                    ResourceLocation resourcelocation = ensureNamespace(string);
                    return processResourceLocation(resourcelocation);
                } else if (from instanceof ResourceLocation resourcelocation) {
                    return processResourceLocation(resourcelocation);
                } else if (from instanceof Item item) {
                    return Either.right(item);
                } else if (from instanceof Ingredient ingredient && ingredient.getItems().length > 0) {
                    return Either.right(ingredient.getItems()[0].getItem());
                }
            } catch (Exception e) {
                System.err.println("Error processing single item " + from + ": " + e.getMessage());
            }

            return null;
        }

        private ResourceLocation ensureNamespace(String string) {
            if (!string.contains(":")) {
                return new ResourceLocation("minecraft", string);
            }
            return new ResourceLocation(string);
        }

        private Either<TagKey<Item>, Item> processResourceLocation(ResourceLocation resourcelocation) {
            var tagItems = MATags.smartLookupItem(resourcelocation);
            if (tagItems == null) {
                Item item = ForgeRegistries.ITEMS.getValue(resourcelocation);
                if (item != null) {
                    return Either.right(item);
                }
                return null;
            }

            if (tagItems.size() > 1) {
                return Either.left(TagKey.create(Registries.ITEM, resourcelocation));
            } else {
                return Either.right(ForgeRegistries.ITEMS.getValue(resourcelocation));
            }
        }
    };
}