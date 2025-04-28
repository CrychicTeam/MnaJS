package com.pickaid.mnajs.recipes.component;

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

public class ItemOrTagComponent {
    public static RecipeComponent<Either<TagKey<Item>, Item>> ITEM_OR_TAG_COMPONENT = new RecipeComponent<>() {

        @Override
        public Class<?> componentClass() {
            return Ingredient.class;
        }

        @Override
        public JsonElement write(RecipeJS recipe, Either<TagKey<Item>, Item> value) {
            return value.map(
                    tagKey -> new JsonPrimitive(tagKey.location().toString()),
                    item -> new JsonPrimitive(item.kjs$getIdLocation().toString())
            );
        }

        @Override
        public Either<TagKey<Item>, Item> read(RecipeJS recipe, Object from) {
            ResourceLocation resourceLocation = null;

            if (from instanceof String string) {
                resourceLocation = new ResourceLocation(string);
            } else if (from instanceof ResourceLocation location) {
                resourceLocation = location;
            } else if (from instanceof Item item) {
                return Either.right(item);
            }

            if (resourceLocation != null) {
                var tagItems = MATags.smartLookupItem(resourceLocation);
                if (tagItems == null) return null;

                return tagItems.size() > 1
                        ? Either.left(TagKey.create(Registries.ITEM, resourceLocation))
                        : Either.right(ForgeRegistries.ITEMS.getValue(resourceLocation));
            }

            return null;
        }
    };
}
