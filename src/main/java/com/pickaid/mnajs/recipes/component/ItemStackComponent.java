package com.pickaid.mnajs.recipes.component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.pickaid.mnajs.util.KubeJSCompat;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

public interface ItemStackComponent {
    byte TAG_END = 0;
    byte TAG_BYTE = 1;
    byte TAG_SHORT = 2;
    byte TAG_INT = 3;
    byte TAG_LONG = 4;
    byte TAG_FLOAT = 5;
    byte TAG_DOUBLE = 6;
    byte TAG_BYTE_ARRAY = 7;
    byte TAG_STRING = 8;
    byte TAG_LIST = 9;
    byte TAG_COMPOUND = 10;
    byte TAG_INT_ARRAY = 11;
    byte TAG_LONG_ARRAY = 12;

    RecipeComponent<ItemStack> ITEMSTACK = new RecipeComponent<>() {
        @Override
        public Class<?> componentClass() {
            return ItemStack.class;
        }

        @Override
        public JsonElement write(RecipeJS recipe, ItemStack value) {
            if (value.getTag() == null) {
                return new JsonPrimitive(KubeJSCompat.itemIdString(value.getItem()));
            }
            var json = new JsonObject();
            json.addProperty("item", KubeJSCompat.itemId(value).toString());

            JsonObject data = toJSON(value.getTag());
            json.add("data", data);

            return json;
        }

        @Override
        public ItemStack read(RecipeJS recipe, Object from) {
            if (from instanceof JsonObject object) {
                return readFromJsonObject(object);
            }
            if (from instanceof JsonElement element) {
                if (element.isJsonPrimitive()) {
                    return read(recipe, element.getAsString());
                }
                if (element.isJsonObject()) {
                    return readFromJsonObject(element.getAsJsonObject());
                }
            }
            if (from instanceof String string) {
                var item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(string));
                if (item != null) {
                    return new ItemStack(item);
                }
            } else if (from instanceof Map<?, ?> map) {
                return readFromMap(map);
            } else if (from instanceof Item item) {
                return new ItemStack(item);
            } else if (from instanceof ItemStack itemStack) {
                return itemStack.copy();
            }
            return ItemStack.EMPTY;
        }

        private static ItemStack readFromJsonObject(JsonObject object) {
            if (!object.has("item")) {
                return ItemStack.EMPTY;
            }

            var item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(object.get("item").getAsString()));
            if (item == null) {
                return ItemStack.EMPTY;
            }

            ItemStack itemStack = new ItemStack(item);
            if (object.has("data") && object.get("data").isJsonObject()) {
                CompoundTag tag = fromJSON(object.get("data").getAsJsonObject());
                itemStack.setTag(tag);
            }
            if (object.has("count")) {
                itemStack.setCount(object.get("count").getAsInt());
            }
            return itemStack;
        }

        private static ItemStack readFromMap(Map<?, ?> map) {
            Object itemValue = getNamedValue(map, "item");
            if (itemValue == null) {
                return ItemStack.EMPTY;
            }

            Item item;
            if (itemValue instanceof Item directItem) {
                item = directItem;
            } else if (itemValue instanceof ResourceLocation id) {
                item = ForgeRegistries.ITEMS.getValue(id);
            } else {
                item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(String.valueOf(itemValue)));
            }

            if (item == null) {
                return ItemStack.EMPTY;
            }

            ItemStack itemStack = new ItemStack(item);
            Object dataValue = getNamedValue(map, "data");
            if (dataValue instanceof JsonObject dataObject) {
                itemStack.setTag(fromJSON(dataObject));
            } else if (dataValue instanceof Map<?, ?> dataMap) {
                JsonObject json = new JsonObject();
                dataMap.forEach((key, value) -> json.add(String.valueOf(key), toJsonElement(value)));
                itemStack.setTag(fromJSON(json));
            }

            Object countValue = getNamedValue(map, "count");
            if (countValue instanceof Number number && number.intValue() > 0) {
                itemStack.setCount(number.intValue());
            }

            return itemStack;
        }
    };

    private static Object getNamedValue(Map<?, ?> map, String name) {
        Object direct = map.get(name);
        if (direct != null) {
            return direct;
        }

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Object key = entry.getKey();
            if (name.equals(key)) {
                return entry.getValue();
            }
            if (key instanceof RecipeKey<?> recipeKey && recipeKey.names.contains(name)) {
                return entry.getValue();
            }
        }

        return null;
    }

    private static JsonElement toJsonElement(Object value) {
        if (value == null) {
            return new JsonPrimitive("");
        }
        if (value instanceof JsonElement element) {
            return element;
        }
        if (value instanceof Boolean bool) {
            return new JsonPrimitive(bool);
        }
        if (value instanceof Number number) {
            return new JsonPrimitive(number);
        }
        if (value instanceof String string) {
            return new JsonPrimitive(string);
        }
        if (value instanceof Map<?, ?> map) {
            JsonObject object = new JsonObject();
            map.forEach((key, nestedValue) -> object.add(String.valueOf(key), toJsonElement(nestedValue)));
            return object;
        }
        if (value instanceof Iterable<?> iterable) {
            JsonArray array = new JsonArray();
            for (Object element : iterable) {
                array.add(toJsonElement(element));
            }
            return array;
        }
        return new JsonPrimitive(String.valueOf(value));
    }

    private static JsonObject toJSON(CompoundTag tag) {
        JsonObject output = new JsonObject();

        for (String key : tag.getAllKeys()) {
            byte type = tag.getTagType(key);
            switch (type) {
                case TAG_BYTE:
                    if (tag.getByte(key) == 0 || tag.getByte(key) == 1) {
                        output.addProperty(key, tag.getBoolean(key));
                    } else {
                        output.addProperty(key, tag.getByte(key));
                    }
                    break;
                case TAG_SHORT:
                    output.addProperty(key, tag.getShort(key));
                    break;
                case TAG_INT:
                    output.addProperty(key, tag.getInt(key));
                    break;
                case TAG_LONG:
                    output.addProperty(key, tag.getLong(key));
                    break;
                case TAG_FLOAT:
                    output.addProperty(key, tag.getFloat(key));
                    break;
                case TAG_DOUBLE:
                    output.addProperty(key, tag.getDouble(key));
                    break;
                case TAG_BYTE_ARRAY:
                    JsonArray byteArray = new JsonArray();
                    for (byte b : tag.getByteArray(key)) {
                        byteArray.add(b);
                    }
                    output.add(key, byteArray);
                    break;
                case TAG_STRING:
                    output.addProperty(key, tag.getString(key));
                    break;
                case TAG_LIST: {
                    JsonArray listArray = new JsonArray();
                    net.minecraft.nbt.ListTag listTag = tag.getList(key, 0);

                    for (int i = 0; i < listTag.size(); i++) {
                        if (listTag.getElementType() == TAG_COMPOUND) {
                            listArray.add(toJSON(listTag.getCompound(i)));
                        } else if (listTag.getElementType() == TAG_STRING) {
                            listArray.add(listTag.getString(i));
                        } else if (listTag.getElementType() >= TAG_BYTE && listTag.getElementType() <= TAG_DOUBLE) {
                            listArray.add(listTag.get(i).toString());
                        } else {
                            listArray.add(listTag.get(i).toString());
                        }
                    }

                    output.add(key, listArray);
                    break;
                }
                case TAG_COMPOUND:
                    output.add(key, toJSON(tag.getCompound(key)));
                    break;
                case TAG_INT_ARRAY: {
                    JsonArray intArray = new JsonArray();
                    for (int i : tag.getIntArray(key)) {
                        intArray.add(i);
                    }
                    output.add(key, intArray);
                    break;
                }
                case TAG_LONG_ARRAY: {
                    JsonArray longArray = new JsonArray();
                    for (long l : tag.getLongArray(key)) {
                        longArray.add(l);
                    }
                    output.add(key, longArray);
                    break;
                }
                default:
                    output.addProperty(key, tag.get(key).toString());
                    break;
            }
        }

        return output;
    }

    private static CompoundTag fromJSON(JsonObject object) {
        CompoundTag output = new CompoundTag();
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            String key = entry.getKey();
            JsonElement elem = entry.getValue();
            if (elem.isJsonPrimitive()) {
                JsonPrimitive primitive = elem.getAsJsonPrimitive();
                if (primitive.isBoolean()) {
                    output.putBoolean(key, primitive.getAsBoolean());
                } else if (primitive.isNumber()) {
                    Number val = primitive.getAsNumber();
                    if (val instanceof Long) {
                        output.putLong(key, val.longValue());
                    } else if (val instanceof Double || val instanceof Float) {
                        if (val.doubleValue() == val.floatValue()) {
                            output.putFloat(key, val.floatValue());
                        } else {
                            output.putDouble(key, val.doubleValue());
                        }
                    } else {
                        if (val.intValue() == val.byteValue()) {
                            output.putByte(key, val.byteValue());
                        } else if (val.intValue() == val.shortValue()) {
                            output.putShort(key, val.shortValue());
                        } else {
                            output.putInt(key, val.intValue());
                        }
                    }
                } else if (primitive.isString()) {
                    output.putString(key, primitive.getAsString());
                }
            } else if (elem.isJsonObject()) {
                output.put(key, fromJSON(elem.getAsJsonObject()));
            } else if (elem.isJsonArray()) {
                JsonArray array = elem.getAsJsonArray();
                if (array.size() > 0) {
                    JsonElement first = array.get(0);
                    if (first.isJsonObject()) {
                        net.minecraft.nbt.ListTag listTag = new net.minecraft.nbt.ListTag();
                        for (JsonElement e : array) {
                            if (e.isJsonObject()) {
                                listTag.add(fromJSON(e.getAsJsonObject()));
                            }
                        }
                        output.put(key, listTag);
                    } else if (first.isJsonPrimitive()) {
                        JsonPrimitive primitive = first.getAsJsonPrimitive();
                        if (primitive.isNumber()) {
                            boolean allInts = true;
                            boolean allLongs = true;
                            boolean allBytes = true;
                            for (JsonElement e : array) {
                                if (!e.isJsonPrimitive() || !e.getAsJsonPrimitive().isNumber()) {
                                    allInts = allLongs = allBytes = false;
                                    break;
                                }
                                Number n = e.getAsJsonPrimitive().getAsNumber();
                                allInts &= (n.intValue() == n.doubleValue());
                                allLongs &= (n.longValue() == n.doubleValue());
                                allBytes &= (n.byteValue() == n.doubleValue() && n.byteValue() >= Byte.MIN_VALUE && n.byteValue() <= Byte.MAX_VALUE);
                            }
                            if (allBytes) {
                                byte[] byteArray = new byte[array.size()];
                                for (int i = 0; i < array.size(); i++) {
                                    byteArray[i] = array.get(i).getAsJsonPrimitive().getAsByte();
                                }
                                output.putByteArray(key, byteArray);
                            } else if (allInts) {
                                int[] intArray = new int[array.size()];
                                for (int i = 0; i < array.size(); i++) {
                                    intArray[i] = array.get(i).getAsJsonPrimitive().getAsInt();
                                }
                                output.putIntArray(key, intArray);
                            } else if (allLongs) {
                                long[] longArray = new long[array.size()];
                                for (int i = 0; i < array.size(); i++) {
                                    longArray[i] = array.get(i).getAsJsonPrimitive().getAsLong();
                                }
                                output.putLongArray(key, longArray);
                            } else {
                                net.minecraft.nbt.ListTag listTag = new net.minecraft.nbt.ListTag();
                                for (JsonElement e : array) {
                                    Number n = e.getAsJsonPrimitive().getAsNumber();
                                    if (n.doubleValue() != n.floatValue()) {
                                        listTag.add(net.minecraft.nbt.DoubleTag.valueOf(n.doubleValue()));
                                    } else if (n.longValue() != n.intValue()) {
                                        listTag.add(net.minecraft.nbt.LongTag.valueOf(n.longValue()));
                                    } else {
                                        listTag.add(net.minecraft.nbt.IntTag.valueOf(n.intValue()));
                                    }
                                }
                                output.put(key, listTag);
                            }
                        } else if (primitive.isString()) {
                            net.minecraft.nbt.ListTag listTag = new net.minecraft.nbt.ListTag();
                            for (JsonElement e : array) {
                                if (e.isJsonPrimitive() && e.getAsJsonPrimitive().isString()) {
                                    listTag.add(net.minecraft.nbt.StringTag.valueOf(e.getAsString()));
                                }
                            }
                            output.put(key, listTag);
                        } else {
                            net.minecraft.nbt.ListTag listTag = new net.minecraft.nbt.ListTag();
                            for (JsonElement e : array) {
                                listTag.add(net.minecraft.nbt.StringTag.valueOf(e.toString()));
                            }
                            output.put(key, listTag);
                        }
                    } else {
                        net.minecraft.nbt.ListTag listTag = new net.minecraft.nbt.ListTag();
                        for (JsonElement e : array) {
                            listTag.add(net.minecraft.nbt.StringTag.valueOf(e.toString()));
                        }
                        output.put(key, listTag);
                    }
                } else {
                    output.put(key, new net.minecraft.nbt.ListTag());
                }
            }
        }

        return output;
    }
}
