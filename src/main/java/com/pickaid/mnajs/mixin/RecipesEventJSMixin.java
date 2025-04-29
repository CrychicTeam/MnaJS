package com.pickaid.mnajs.mixin;

import com.pickaid.mnajs.recipes.schema.*;
import dev.latvian.mods.kubejs.recipe.NamespaceFunction;
import dev.latvian.mods.kubejs.recipe.RecipeTypeFunction;
import dev.latvian.mods.kubejs.recipe.RecipesEventJS;
import dev.latvian.mods.kubejs.recipe.schema.RecipeNamespace;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaType;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.util.Map;

@Mixin(RecipesEventJS.class)
public class RecipesEventJSMixin {
    @Final
    @Shadow(remap = false)
    private Map<String, Object> recipeFunctions;

    @Inject(method = "<init>", at = @At(value = "RETURN"), remap = false)
    private void mna$injectSchemas(CallbackInfo ci) {
        RecipesEventJS self = (RecipesEventJS) (Object) this;
        try {
            Object mnaNamespaceObj = recipeFunctions.get("mna");

            if (mnaNamespaceObj instanceof NamespaceFunction) {
                NamespaceFunction mnaNamespace = (NamespaceFunction) mnaNamespaceObj;
                Field mapField = NamespaceFunction.class.getDeclaredField("map");
                mapField.setAccessible(true);
                Map<String, RecipeTypeFunction> mnaMap = (Map<String, RecipeTypeFunction>) mapField.get(mnaNamespace);
                if (mnaMap != null) {
                    mnaJS$addAliasIfPresent(self, mnaMap, "progression-condition", "progression");
                    mnaJS$addAliasIfPresent(self, mnaMap, "manaweaving-recipe", "manaweavingAltar");
                    mnaJS$addAliasIfPresent(self, mnaMap, "arcane-furnace", "arcaneFurnace");
                    mnaJS$addAliasIfPresent(self, mnaMap, "eldrin-altar", "eldrinAltar");
                    mnaJS$addAliasIfPresent(self, mnaMap, "eldrin-fume", "eldrinFume");
                    mnaJS$addAliasIfPresent(self, mnaMap, "manaweaving-pattern", "pattern");
                    mnaJS$addAliasIfPresent(self, mnaMap, "manaweave-cache-effect", "cacheEffect");
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to inject MNA recipe schema aliases: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Unique
    private void mnaJS$addAliasIfPresent(RecipesEventJS event, Map<String, RecipeTypeFunction> map, String originalKey, String aliasKey) {
        RecipeSchema schema = switch (originalKey) {
            case "progression-condition" -> ProgressionSchema.SCHEMA;
            case "manaweaving-recipe" -> ManaweavingAltarSchema.SCHEMA;
            case "arcane-furnace" -> ArcaneFurnaceSchema.SCHEMA;
            case "eldrin-altar" -> EldrinAltarSchema.SCHEMA;
            case "eldrin-fume" -> FumerFliterSchema.SCHEMA;
            case "manaweaving-pattern" -> ManaweavingPatternSchema.SCHEMA;
            case "manaweave-cache-effect" -> ManaweaveCacheEffectSchema.SCHEMA;
            default -> null;
        };
        if (schema == null) return;
        RecipeSchemaType type = new RecipeSchemaType(RecipeNamespace.getAll().get("mna"), new ResourceLocation("mna:" + originalKey), schema);
        RecipeTypeFunction function = new RecipeTypeFunction(event, type);
        map.put(aliasKey, function);
        recipeFunctions.put("mna:" + aliasKey, function);
    }
}