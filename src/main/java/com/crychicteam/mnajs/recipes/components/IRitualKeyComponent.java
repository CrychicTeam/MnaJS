package com.crychicteam.mnajs.recipes.components;

import com.google.gson.JsonElement;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ComponentRole;
import dev.latvian.mods.kubejs.recipe.component.MapRecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.StringComponent;
import dev.latvian.mods.kubejs.util.TinyMap;

public interface IRitualKeyComponent {
	RecipeComponent<RitualKey> INPUT = new RecipeComponent<>() {
		@Override
		public String componentType() {
			return "ritual_key";
		}
		
		@Override
		public ComponentRole role() {
			return ComponentRole.INPUT;
		}
		
		@Override
		public Class<?> componentClass() {
			return RitualKey.class;
		}
		
		@Override
		public JsonElement write(RecipeJS recipe, RitualKey value) {
			return value.writeToJson();
		}
		
		@Override
		public RitualKey read(RecipeJS recipe, Object from) {
			return RitualKey.of(from);
		}
		
		@Override
		public String checkEmpty(RecipeKey<RitualKey> key, RitualKey value) {
			return value.isEmpty() ? "Ingredient '" + key.name + "' can't be empty!" : "";
		}
		
		@Override
		public RecipeComponent<TinyMap<Character, RitualKey>> asPatternKey() {
			return new MapRecipeComponent<>(StringComponent.CHARACTER, this, true);
		}
		
		@Override
		public String toString() {
			return this.componentType();
		}
	};
}
