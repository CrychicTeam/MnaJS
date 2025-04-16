package com.crychicteam.mnajs.kubejs;

import com.crychicteam.mnajs.content.CustomFaction;
import com.crychicteam.mnajs.recipes.RitualRecipeSchema;
import com.mna.Registries;
import com.mna.api.faction.IFaction;
import com.mna.recipes.RecipeInit;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;
import dev.latvian.mods.kubejs.registry.RegistryInfo;

public class MnaJSPlugin extends KubeJSPlugin {
//    public static final RegistryInfo<IFaction> FACTION_REGISTRY = RegistryInfo.of(Registries.Factions.get().getRegistryKey(), IFaction.class);

    @Override
    public void registerEvents() {
        MnaJSEvents.GROUP.register();
    }

    @Override
    public void init() {
//        FACTION_REGISTRY.addType("basic", CustomFaction.Builder.class, CustomFaction.Builder::new);
    }

    @Override
    public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
        var ritualSchema = RitualRecipeSchema.RITUAL_RECIPE;
        event.register(RecipeInit.RITUAL_TYPE.getId(), ritualSchema);
    }
}

