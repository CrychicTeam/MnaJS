package com.pickaid.mnajs.kubejs.id;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.datafixers.util.Either;
import com.pickaid.mnajs.util.KubeJSCompat;
import dev.latvian.mods.rhino.Wrapper;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public record MnaItemOrTag(Either<TagKey<Item>, Item> value) {
    public MnaItemOrTag {
        Objects.requireNonNull(value, "value");
    }

    public static MnaItemOrTag item(Item value) {
        Objects.requireNonNull(value, "value");
        return new MnaItemOrTag(Either.right(value));
    }

    public static MnaItemOrTag tag(TagKey<Item> value) {
        Objects.requireNonNull(value, "value");
        return new MnaItemOrTag(Either.left(value));
    }

    public static MnaItemOrTag parse(Object rawValue) {
        Object value = Wrapper.unwrapped(rawValue);

        if (value instanceof JsonElement element) {
            if (element.isJsonNull()) {
                throw new IllegalArgumentException("itemOrTag can't be null");
            }

            if (element.isJsonPrimitive()) {
                JsonPrimitive primitive = element.getAsJsonPrimitive();
                if (primitive.isString() || primitive.isNumber() || primitive.isBoolean()) {
                    return parse(primitive.getAsString());
                }
            }

            if (element.isJsonObject()) {
                JsonObject object = element.getAsJsonObject();
                if (object.has("item")) {
                    return parse(object.get("item"));
                }
                if (object.has("tag")) {
                    return parse("#" + object.get("tag").getAsString());
                }
                if (object.has("id")) {
                    return parse(object.get("id"));
                }
            }
        }

        if (value instanceof MnaItemOrTag itemOrTag) {
            return itemOrTag;
        }
        if (value instanceof ItemStack stack) {
            return item(stack.getItem());
        }
        if (value instanceof Item item) {
            return item(item);
        }
        if (value instanceof Ingredient ingredient && ingredient.getItems().length > 0) {
            return item(ingredient.getItems()[0].getItem());
        }
        if (value instanceof TagKey<?> rawTag) {
            @SuppressWarnings("unchecked")
            TagKey<Item> itemTag = (TagKey<Item>) rawTag;
            return tag(itemTag);
        }
        if (value instanceof Either<?, ?> either) {
            Object left = either.left().orElse(null);
            if (left instanceof TagKey<?> rawTag) {
                @SuppressWarnings("unchecked")
                TagKey<Item> itemTag = (TagKey<Item>) rawTag;
                return tag(itemTag);
            }

            Object right = either.right().orElse(null);
            if (right instanceof Item item) {
                return item(item);
            }
        }
        if (value instanceof CharSequence chars) {
            String text = chars.toString().trim();
            if (text.isEmpty()) {
                throw new IllegalArgumentException("itemOrTag can't be empty");
            }
            if (text.startsWith("#")) {
                return tag(TagKey.create(Registries.ITEM, parseTagLocation(text)));
            }
            return item(resolveItem(MnaIds.parse(text, "itemOrTag", "minecraft", null)));
        }
        if (value instanceof ResourceLocation location) {
            return item(resolveItem(MnaIds.normalize(location, "minecraft", null)));
        }

        throw new IllegalArgumentException("Unsupported itemOrTag value: " + value);
    }

    public String id() {
        return value.map(tag -> "#" + tag.location(), KubeJSCompat::itemIdString);
    }

    public String recipeValue() {
        return value.map(tag -> tag.location().toString(), KubeJSCompat::itemIdString);
    }

    public String scriptValue() {
        return id();
    }

    @Override
    public String toString() {
        return id();
    }

    private static ResourceLocation parseTagLocation(String raw) {
        String text = raw.substring(1).trim();
        if (text.isEmpty()) {
            throw new IllegalArgumentException("Tag id can't be empty");
        }

        String normalized = text.contains(":") ? text : "minecraft:" + text;
        ResourceLocation location = ResourceLocation.tryParse(normalized);
        if (location == null) {
            throw new IllegalArgumentException("Invalid item tag id: " + raw);
        }
        return location;
    }

    private static Item resolveItem(ResourceLocation location) {
        Item item = ForgeRegistries.ITEMS.getValue(location);
        if (item == null) {
            throw new IllegalArgumentException("Unknown item id: " + location);
        }
        return item;
    }
}
