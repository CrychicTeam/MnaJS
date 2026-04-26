package com.pickaid.mnajs.kubejs.id;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.latvian.mods.rhino.Wrapper;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public final class MnaEnumIds {
    private MnaEnumIds() {
    }

    public static String parseName(Object value, String fieldName) {
        Object unwrapped = Wrapper.unwrapped(value);

        if (unwrapped instanceof JsonElement element) {
            if (element.isJsonNull()) {
                throw new IllegalArgumentException(fieldName + " can't be null");
            }

            if (element.isJsonPrimitive()) {
                JsonPrimitive primitive = element.getAsJsonPrimitive();
                if (primitive.isString() || primitive.isNumber() || primitive.isBoolean()) {
                    return normalizeName(primitive.getAsString(), fieldName);
                }
            }

            if (element.isJsonObject()) {
                JsonObject object = element.getAsJsonObject();
                if (object.has("id")) {
                    return parseName(object.get("id"), fieldName);
                }
                if (object.has("name")) {
                    return parseName(object.get("name"), fieldName);
                }
            }
        }

        if (unwrapped instanceof Enum<?> enumValue) {
            return externalName(enumValue);
        }

        if (unwrapped instanceof CharSequence charSequence) {
            return normalizeName(charSequence.toString(), fieldName);
        }

        throw new IllegalArgumentException("Unsupported enum id for " + fieldName + ": " + unwrapped);
    }

    public static String normalizeName(String raw, String fieldName) {
        Objects.requireNonNull(raw, fieldName + " can't be null");
        String normalized = raw.trim();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " can't be empty");
        }

        normalized = normalized
                .replace('-', '_')
                .replace(' ', '_')
                .toLowerCase(Locale.ROOT);

        return normalized;
    }

    public static <E extends Enum<E>> E requireEnum(Class<E> enumClass, String id, String fieldName) {
        Objects.requireNonNull(enumClass, "enumClass");
        String normalized = normalizeName(id, fieldName);
        try {
            return Enum.valueOf(enumClass, normalized.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Unknown " + fieldName + ": " + normalized);
        }
    }

    public static String externalName(Enum<?> value) {
        Objects.requireNonNull(value, "value");
        return value.name().toLowerCase(Locale.ROOT);
    }

    public static <E extends Enum<E>> List<String> valuesOf(Class<E> enumClass) {
        Objects.requireNonNull(enumClass, "enumClass");
        return Arrays.stream(enumClass.getEnumConstants())
                .map(MnaEnumIds::externalName)
                .sorted()
                .toList();
    }
}
