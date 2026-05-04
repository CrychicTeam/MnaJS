package com.pickaid.mnajs.kubejs.id;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import dev.latvian.mods.kubejs.registry.BuilderBase;
import dev.latvian.mods.rhino.Wrapper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;

public final class MnaIds {
    private MnaIds() {
    }

    public static ResourceLocation parse(Object value, String fieldName) {
        return parse(value, fieldName, "mna", null);
    }

    public static ResourceLocation parse(Object value, String fieldName, String defaultNamespace, String pathPrefix) {
        Object unwrapped = Wrapper.unwrapped(value);

        if (unwrapped instanceof JsonElement element) {
            if (element.isJsonNull()) {
                throw new IllegalArgumentException(fieldName + " can't be null");
            }

            if (element.isJsonPrimitive()) {
                JsonPrimitive primitive = element.getAsJsonPrimitive();
                if (primitive.isString() || primitive.isNumber() || primitive.isBoolean()) {
                    return parse(primitive.getAsString(), fieldName, defaultNamespace, pathPrefix);
                }
            }

            if (element.isJsonObject()) {
                JsonObject object = element.getAsJsonObject();
                if (object.has("id")) {
                    return parse(object.get("id"), fieldName, defaultNamespace, pathPrefix);
                }
                if (object.has("name")) {
                    return parse(object.get("name"), fieldName, defaultNamespace, pathPrefix);
                }
            }
        }

        if (unwrapped instanceof MnaTypedId typedId) {
            return normalize(typedId.location(), defaultNamespace, pathPrefix);
        }

        if (unwrapped instanceof ResourceLocation resourceLocation) {
            return normalize(resourceLocation, defaultNamespace, pathPrefix);
        }

        if (unwrapped instanceof RegistryObject<?> registryObject) {
            return normalize(registryObject.getId(), defaultNamespace, pathPrefix);
        }

        if (unwrapped instanceof BuilderBase<?> builderBase) {
            return normalize(builderBase.id, defaultNamespace, pathPrefix);
        }

        if (unwrapped instanceof CharSequence charSequence) {
            String raw = charSequence.toString().trim();
            if (raw.isEmpty()) {
                throw new IllegalArgumentException(fieldName + " can't be empty");
            }

            String normalized = raw.contains(":") ? raw : defaultNamespace + ":" + raw;
            ResourceLocation parsed = ResourceLocation.tryParse(normalized);
            if (parsed == null) {
                throw new IllegalArgumentException("Invalid resource location for " + fieldName + ": " + raw);
            }

            return normalize(parsed, defaultNamespace, pathPrefix);
        }

        throw new IllegalArgumentException("Unsupported resource location for " + fieldName + ": " + unwrapped);
    }

    public static ResourceLocation normalize(ResourceLocation value, String defaultNamespace, String pathPrefix) {
        if (value == null) {
            throw new IllegalArgumentException("Resource location can't be null");
        }

        String namespace = value.getNamespace();
        if (namespace == null || namespace.isBlank()) {
            namespace = defaultNamespace;
        }

        String path = value.getPath();
        if (pathPrefix != null && !pathPrefix.isBlank() && !path.startsWith(pathPrefix + "/")) {
            path = pathPrefix + "/" + path;
        }

        return new ResourceLocation(namespace, path);
    }
}
