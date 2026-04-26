package com.pickaid.mnajs.kubejs.recipe;

import com.mna.api.affinity.Affinity;
import com.mojang.datafixers.util.Either;
import com.pickaid.mnajs.kubejs.id.MnaItemOrTag;
import com.pickaid.mnajs.kubejs.id.MnaFactionId;
import com.pickaid.mnajs.kubejs.id.MnaTypedId;
import com.pickaid.mnajs.util.KubeJSCompat;
import dev.latvian.mods.kubejs.recipe.RecipeExceptionJS;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentBuilderMap;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import dev.latvian.mods.kubejs.util.TinyMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class MnaBaseRecipeJS<T extends MnaBaseRecipeJS<T>> extends RecipeJS {
    @SuppressWarnings("unchecked")
    protected final T self() {
        return (T) this;
    }

    protected final T setKey(String key, Object value) {
        set(key, value);
        return self();
    }

    protected final Object currentValue(String key) {
        try {
            return get(key);
        } catch (RuntimeException ignored) {
            return null;
        }
    }

    protected final void require(boolean condition, String message) {
        if (!condition) {
            throw new RecipeExceptionJS(message);
        }
    }

    protected void validateRecipe() {
    }

    @Override
    public void afterLoaded() {
        if (!newRecipe) {
            super.afterLoaded();
            validateRecipe();
        }
    }

    @Override
    public void serialize() {
        validateRecipe();
        super.serialize();
    }

    @Info(value = "Set the Mana and Artifice recipe tier.", params = {
            @Param(name = "value", value = "Tier number from 1 to 5.")
    })
    public T tier(int value) {
        return setKey("tier", value);
    }

    @Info(value = "Set the required faction for this recipe.", params = {
            @Param(name = "value", value = "Faction id such as mna:council.")
    })
    public T faction(MnaFactionId value) {
        return setKey("requiredFaction", value);
    }

    protected final LinkedHashMap<String, Object> editableObject(String key) {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        Object current = toScriptValue(currentValue(key));
        if (current instanceof Map<?, ?> map) {
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                result.put(String.valueOf(entry.getKey()), entry.getValue());
            }
        }
        return result;
    }

    protected final ArrayList<Object> editableList(String key) {
        ArrayList<Object> result = new ArrayList<>();
        Object current = toScriptValue(currentValue(key));
        if (current instanceof Collection<?> collection) {
            result.addAll(collection);
        } else if (current != null) {
            result.add(current);
        }
        return result;
    }

    protected final Object toScriptValue(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof MnaTypedId typedId) {
            return typedId.id();
        }
        if (value instanceof MnaItemOrTag itemOrTag) {
            return itemOrTag.scriptValue();
        }
        if (value instanceof ResourceLocation location) {
            return location.toString();
        }
        if (value instanceof Item item) {
            return KubeJSCompat.itemIdString(item);
        }
        if (value instanceof Block block) {
            ResourceLocation id = ForgeRegistries.BLOCKS.getKey(block);
            return id == null ? null : id.toString();
        }
        if (value instanceof ItemStack stack) {
            return stack.copy();
        }
        if (value instanceof Affinity affinity) {
            return affinity.name();
        }
        if (value instanceof Either<?, ?> either) {
            return either.map(
                    left -> {
                        if (left instanceof TagKey<?> tagKey) {
                            return "#" + tagKey.location();
                        }
                        return String.valueOf(left);
                    },
                    right -> {
                        if (right instanceof Item item) {
                            return KubeJSCompat.itemIdString(item);
                        }
                        if (right instanceof Block block) {
                            ResourceLocation id = ForgeRegistries.BLOCKS.getKey(block);
                            return id == null ? null : id.toString();
                        }
                        return String.valueOf(right);
                    }
            );
        }
        if (value instanceof RecipeComponentBuilderMap map) {
            LinkedHashMap<String, Object> result = new LinkedHashMap<>();
            for (var holder : map.holders) {
                if (holder.value != null) {
                    result.put(holder.key.name, toScriptValue(holder.value));
                }
            }
            return result;
        }
        if (value instanceof TinyMap<?, ?> map) {
            LinkedHashMap<String, Object> result = new LinkedHashMap<>();
            for (var entry : map.entries()) {
                result.put(String.valueOf(entry.key()), toScriptValue(entry.value()));
            }
            return result;
        }
        if (value instanceof Map<?, ?> map) {
            LinkedHashMap<String, Object> result = new LinkedHashMap<>();
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                result.put(String.valueOf(entry.getKey()), toScriptValue(entry.getValue()));
            }
            return result;
        }
        if (value instanceof Collection<?> collection) {
            ArrayList<Object> result = new ArrayList<>(collection.size());
            for (Object element : collection) {
                result.add(toScriptValue(element));
            }
            return result;
        }
        if (value.getClass().isArray()) {
            int length = Array.getLength(value);
            ArrayList<Object> result = new ArrayList<>(length);
            for (int index = 0; index < length; index++) {
                result.add(toScriptValue(Array.get(value, index)));
            }
            return result;
        }
        return value;
    }
}
