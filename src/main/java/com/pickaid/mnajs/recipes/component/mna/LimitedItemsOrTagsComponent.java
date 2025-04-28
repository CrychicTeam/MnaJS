package com.pickaid.mnajs.recipes.component.mna;

import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Either;
import com.pickaid.mnajs.recipes.component.ItemsOrTagsComponent;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.stream.Collectors;

public class LimitedItemsOrTagsComponent {

    public static RecipeComponent<List<Either<TagKey<Item>, Item>>> createWithMaxSize(int maxSize) {
        return new RecipeComponent<>() {
            @Override
            public Class<?> componentClass() {
                return ItemsOrTagsComponent.ITEMS_OR_TAGS_COMPONENT.componentClass();
            }

            @Override
            public JsonElement write(RecipeJS recipe, List<Either<TagKey<Item>, Item>> values) {
                List<Either<TagKey<Item>, Item>> limitedValues = values.stream()
                        .limit(maxSize)
                        .collect(Collectors.toList());
                if (limitedValues.size() < values.size()) {
                    System.out.println("Warning: Item list exceeded maximum size of " + maxSize +
                            ". Truncated from " + values.size() + " to " + limitedValues.size() + " items.");
                }
                return ItemsOrTagsComponent.ITEMS_OR_TAGS_COMPONENT.write(recipe, limitedValues);
            }

            @Override
            public List<Either<TagKey<Item>, Item>> read(RecipeJS recipe, Object from) {
                List<Either<TagKey<Item>, Item>> result = ItemsOrTagsComponent.ITEMS_OR_TAGS_COMPONENT.read(recipe, from);
                if (result.size() > maxSize) {
                    List<Either<TagKey<Item>, Item>> limitedResult = result.stream()
                            .limit(maxSize)
                            .collect(Collectors.toList());

                    System.out.println("Warning: Read " + result.size() +
                            " items but limited to " + limitedResult.size() +
                            " due to maximum size restriction.");

                    return limitedResult;
                }

                return result;
            }
        };
    }

    public static final RecipeComponent<List<Either<TagKey<Item>, Item>>> MAX_9_ITEMS = createWithMaxSize(9);
}