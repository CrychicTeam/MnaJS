package com.crychicteam.mnajs.recipes.components;

import com.crychicteam.mnajs.MnaJS;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Objects;

public class RitualKey {
	public static final RitualKey EMPTY = new RitualKey(Ingredient.EMPTY);
	
	private final Ingredient ingredient;
	private final boolean optional;
	private final boolean consume;
	private final boolean manualReturn;
	private final boolean isDynamic;
	private final boolean dynamicSource;
	
	public RitualKey(Ingredient ingredient) {
		this(ingredient, false, false, false, false, true);
	}
	
	public RitualKey(Ingredient ingredient, boolean optional) {
		this(ingredient, optional, false, false, false, true);
	}
	
	public RitualKey(Ingredient ingredient, boolean optional, boolean manualReturn) {
		this(ingredient, optional, manualReturn, false, false, true);
	}
	
	public RitualKey(Ingredient ingredient, boolean optional, boolean manualReturn, boolean isDynamic) {
		this(ingredient, optional, manualReturn, isDynamic, false, true);
	}
	
	public RitualKey(Ingredient ingredient, boolean optional, boolean manualReturn, boolean isDynamic, boolean dynamicSource) {
		this(ingredient, optional, manualReturn, isDynamic, dynamicSource, true);
	}
	
	public RitualKey(Ingredient ingredient, boolean optional, boolean manualReturn, boolean isDynamic, boolean dynamicSource, boolean consume) {
		this.ingredient = ingredient;
		this.optional = optional;
		this.manualReturn = manualReturn;
		this.isDynamic = isDynamic;
		this.dynamicSource = dynamicSource;
		this.consume = consume;
	}
	
	public JsonElement writeToJson() {
		JsonObject object = new JsonObject();
		object.add("item", ingredient.toJson());
		object.addProperty("optional", optional);
		object.addProperty("manualReturn", manualReturn);
		object.addProperty("isDynamic", isDynamic);
		object.addProperty("dynamicSource", dynamicSource);
		object.addProperty("consume", consume);
		return object;
	}
	
	public boolean isEmpty() {
		return this == EMPTY || ingredient.isEmpty();
	}
	
	public static RitualKey ofJson(JsonElement element) {
		JsonObject object = element.getAsJsonObject();
		Ingredient ingredient = Ingredient.fromJson(Objects.requireNonNull(object.get("item"), "The item field was not found when parsing Json"));
		boolean optional = object.has("optional") ? object.get("optional").getAsBoolean() : false;
		boolean manualReturn = object.has("manualReturn") ? object.get("manualReturn").getAsBoolean() : false;
		boolean isDynamic = object.has("isDynamic") ? object.get("isDynamic").getAsBoolean() : false;
		boolean dynamicSource = object.has("dynamicSource") ? object.get("dynamicSource").getAsBoolean() : false;
		boolean consume = object.has("consume") ? object.get("consume").getAsBoolean() : true;
		
		return new RitualKey(ingredient, optional, manualReturn, isDynamic, dynamicSource, consume);
	}
	
	public static RitualKey of(Object object) {
		try {
			if (object instanceof RitualKey ritualKey) {
				return ritualKey;
			}
			
			if (object instanceof JsonElement element) {
				return ofJson(element);
			}
		} catch (Exception e) {
			MnaJS.LOGGER.error("Error message: {}, at: {}", e.getMessage(), e.getStackTrace());
		}
		return null;
	}
}
