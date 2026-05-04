package com.pickaid.mnajs.kubejs.id;

import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public record MnaManaweavePatternId(ResourceLocation location) implements MnaTypedId {
    private static final String LEGACY_RECIPE_PATH = "manaweave_patterns/";

    public MnaManaweavePatternId {
        Objects.requireNonNull(location, "location");
        location = MnaIds.normalize(location, "mna", null);
        String path = location.getPath();
        if (path.startsWith(LEGACY_RECIPE_PATH)) {
            location = new ResourceLocation(
                    location.getNamespace(),
                    path.substring(LEGACY_RECIPE_PATH.length())
            );
        }
    }

    public static MnaManaweavePatternId of(ResourceLocation location) {
        return new MnaManaweavePatternId(location);
    }

    public static MnaManaweavePatternId parse(Object value) {
        if (value instanceof MnaManaweavePatternId id) {
            return id;
        }
        return new MnaManaweavePatternId(MnaIds.parse(value, "manaweavePatternId", "mna", null));
    }

    public ResourceLocation recipeLocation() {
        String path = location.getPath();
        if ("mna".equals(location.getNamespace()) && !path.startsWith(LEGACY_RECIPE_PATH)) {
            return new ResourceLocation(location.getNamespace(), LEGACY_RECIPE_PATH + path);
        }
        return location;
    }

    public String recipeId() {
        return recipeLocation().toString();
    }

    @Override
    public String toString() {
        return id();
    }
}
