package com.crychicteam.mnajs.recipes;

import com.google.gson.JsonObject;

public class SchemaHelper {
    public static void moveParameterIfExists(JsonObject source, JsonObject target, String key) {
        if (source.has(key)) {
            target.add(key, source.get(key));
            source.remove(key);
        }
    }
}
