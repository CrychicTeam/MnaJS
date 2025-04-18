package com.crychicteam.mnajs.kubejs;

import com.crychicteam.mnajs.recipes.ProgressionSchema;
import com.crychicteam.mnajs.recipes.RitualRecipeBuilder;
import com.crychicteam.mnajs.recipes.components.IRitualKeyComponent;
import com.crychicteam.mnajs.recipes.components.ProgressionAdvancementComponent;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RecipeComponentFactoryRegistryEvent;
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;
import dev.latvian.mods.kubejs.script.BindingsEvent;

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
	public void registerBindings(BindingsEvent event) {
		event.add("RitualHelper", RitualRecipeBuilder.class);
	}

	@Override
	public void registerRecipeComponents(RecipeComponentFactoryRegistryEvent event) {
//		event.register("ritualKeys", RitualKeyComponent.INSTANCE);
//		event.register("ritualKey", RitualKeyComponent.RitualKey.INSTANCE);
		event.register("ritualKey", IRitualKeyComponent.INPUT);
		event.register("advancement", ProgressionAdvancementComponent.INSTANCE);
	}
	
	@Override
	public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
		event.namespace("mna")
				.register("progression-condition", ProgressionSchema.SCHEMA);
//		var ritualSchema = RitualRecipeSchema.RITUAL_RECIPE;
//		event.register(RecipeInit.RITUAL_TYPE.getId(), ritualSchema);
	}
}

