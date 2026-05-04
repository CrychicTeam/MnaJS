package com.pickaid.mnajs.mixin;

import com.pickaid.mnajs.recipes.schema.ArcaneFurnaceSchema;
import com.pickaid.mnajs.recipes.schema.EldrinAltarSchema;
import com.pickaid.mnajs.recipes.schema.FumerFliterSchema;
import com.pickaid.mnajs.recipes.schema.ManaweaveCacheEffectSchema;
import com.pickaid.mnajs.recipes.schema.ManaweavingAltarSchema;
import com.pickaid.mnajs.recipes.schema.ManaweavingPatternSchema;
import com.pickaid.mnajs.recipes.schema.ProgressionSchema;
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
import java.util.List;
import java.util.Map;

@Mixin(RecipesEventJS.class)
public class RecipesEventJSMixin {
    @Unique
    private static final List<MnaSchemaAlias> MNA_SCHEMA_ALIASES = List.of(
            new MnaSchemaAlias("progression-condition", "progression", ProgressionSchema.SCHEMA),
            new MnaSchemaAlias("manaweaving-recipe", "manaweavingAltar", ManaweavingAltarSchema.SCHEMA),
            new MnaSchemaAlias("arcane-furnace", "arcaneFurnace", ArcaneFurnaceSchema.SCHEMA),
            new MnaSchemaAlias("eldrin-altar", "eldrinAltar", EldrinAltarSchema.SCHEMA),
            new MnaSchemaAlias("eldrin-fume", "eldrinFume", FumerFliterSchema.SCHEMA),
            new MnaSchemaAlias("manaweaving-pattern", "pattern", ManaweavingPatternSchema.SCHEMA),
            new MnaSchemaAlias("manaweave-cache-effect", "cacheEffect", ManaweaveCacheEffectSchema.SCHEMA)
    );

    @Final
    @Shadow(remap = false)
    private Map<String, Object> recipeFunctions;

    @Inject(method = "<init>", at = @At(value = "RETURN"), remap = false)
    @SuppressWarnings("unchecked")
    private void mna$injectSchemas(CallbackInfo ci) {
        RecipesEventJS self = (RecipesEventJS) (Object) this;
        RecipeNamespace namespace = RecipeNamespace.getAll().get("mna");
        if (namespace == null) {
            return;
        }

        try {
            Object mnaNamespaceObj = recipeFunctions.get("mna");
            if (!(mnaNamespaceObj instanceof NamespaceFunction mnaNamespace)) {
                return;
            }

            Field mapField = NamespaceFunction.class.getDeclaredField("map");
            mapField.setAccessible(true);
            Map<String, RecipeTypeFunction> mnaMap = (Map<String, RecipeTypeFunction>) mapField.get(mnaNamespace);
            if (mnaMap == null) {
                return;
            }

            for (MnaSchemaAlias alias : MNA_SCHEMA_ALIASES) {
                mnaJS$addAliasIfMissing(self, namespace, mnaMap, alias);
            }
        } catch (ReflectiveOperationException exception) {
            System.err.println("Failed to inject MNA recipe schema aliases: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    @Unique
    private void mnaJS$addAliasIfMissing(RecipesEventJS event, RecipeNamespace namespace, Map<String, RecipeTypeFunction> map, MnaSchemaAlias alias) {
        RecipeTypeFunction function = map.get(alias.aliasKey());
        if (function == null) {
            RecipeSchemaType type = new RecipeSchemaType(
                    namespace,
                    new ResourceLocation("mna", alias.originalKey()),
                    alias.schema()
            );
            function = new RecipeTypeFunction(event, type);
            map.put(alias.aliasKey(), function);
        }

        recipeFunctions.putIfAbsent("mna:" + alias.aliasKey(), function);
    }

    @Unique
    private record MnaSchemaAlias(String originalKey, String aliasKey, RecipeSchema schema) {
    }
}
