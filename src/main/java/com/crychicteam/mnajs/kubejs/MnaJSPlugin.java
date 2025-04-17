package com.crychicteam.mnajs.kubejs;

import com.crychicteam.mnajs.recipes.RitualRecipeSchema;
import com.crychicteam.mnajs.recipes.components.IRitualKeyComponent;
import com.crychicteam.mnajs.recipes.components.RitualKeyComponent;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RecipeComponentFactoryRegistryEvent;
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;

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
	public void registerRecipeComponents(RecipeComponentFactoryRegistryEvent event) {
//		event.register("ritualKeys", RitualKeyComponent.INSTANCE);
//		event.register("ritualKey", RitualKeyComponent.RitualKey.INSTANCE);
		event.register("ritualKey", IRitualKeyComponent.INPUT);
	}
	
	@Override
	public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
		event.namespace("mna").register("ritual", RitualRecipeSchema.RITUAL_RECIPE);
//		var ritualSchema = RitualRecipeSchema.RITUAL_RECIPE;
//		event.register(RecipeInit.RITUAL_TYPE.getId(), ritualSchema);
	}
}

