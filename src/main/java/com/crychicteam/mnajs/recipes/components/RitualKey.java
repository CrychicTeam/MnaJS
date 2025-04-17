package com.crychicteam.mnajs.recipes.components;

import com.crychicteam.mnajs.MnaJS;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.item.InputItem;

import java.util.Map;
import java.util.Objects;

public class RitualKey {
	public static final RitualKey EMPTY = new RitualKey(InputItem.EMPTY);
	
	private final InputItem inputItem;
	private final boolean optional;
	private final boolean consume;
	private final boolean manualReturn;
	private final boolean isDynamic;
	private final boolean dynamicSource;
	
	public RitualKey(InputItem inputItem) {
		this(inputItem, false, false, false, false, true);
	}
	
	public RitualKey(InputItem inputItem, boolean optional) {
		this(inputItem, optional, false, false, false, true);
	}
	
	public RitualKey(InputItem inputItem, boolean optional, boolean manualReturn) {
		this(inputItem, optional, manualReturn, false, false, true);
	}
	
	public RitualKey(InputItem inputItem, boolean optional, boolean manualReturn, boolean isDynamic) {
		this(inputItem, optional, manualReturn, isDynamic, false, true);
	}
	
	public RitualKey(InputItem inputItem, boolean optional, boolean manualReturn, boolean isDynamic, boolean dynamicSource) {
		this(inputItem, optional, manualReturn, isDynamic, dynamicSource, true);
	}
	
	public RitualKey(InputItem inputItem, boolean optional, boolean manualReturn, boolean isDynamic, boolean dynamicSource, boolean consume) {
		this.inputItem = inputItem;
		this.optional = optional;
		this.manualReturn = manualReturn;
		this.isDynamic = isDynamic;
		this.dynamicSource = dynamicSource;
		this.consume = consume;
	}
	
	public JsonElement writeToJson() {
		JsonObject object = new JsonObject();
		object.add("item", inputItem.ingredient.toJson());
		object.addProperty("optional", optional);
		object.addProperty("manualReturn", manualReturn);
		object.addProperty("isDynamic", isDynamic);
		object.addProperty("dynamicSource", dynamicSource);
		object.addProperty("consume", consume);
		return object;
	}
	
	public boolean isEmpty() {
		return this == EMPTY || inputItem.isEmpty();
	}
	
	public static RitualKey ofJson(JsonElement json) {
		MnaJS.LOGGER.info("RitualKey ofJson");
		if (json == null || json.isJsonNull() || json.isJsonArray() && json.getAsJsonArray().isEmpty()) {
			MnaJS.LOGGER.warn("Json is Empty");
			return EMPTY;
		}
		
		if (json.isJsonPrimitive()) {
			MnaJS.LOGGER.info("RitualKey isJsonPrimitive");
			return ofJsonString(json);
		}
		
		if (json.isJsonObject()) {
			MnaJS.LOGGER.info("RitualKey isJsonObject");
			return ofJsonObject(json.getAsJsonObject());
		}
		MnaJS.LOGGER.error("ofJson method is failed");
		return EMPTY;
	}
	
	private static RitualKey ofJsonString(JsonElement json) {
		InputItem item = InputItem.of(json);
		return new RitualKey(item);
	}
	
	private static RitualKey ofJsonObject(JsonObject object) {
		InputItem item = InputItem.of(Objects.requireNonNull(object.get("item"), "The item field was not found when parsing Json"));
		boolean optional = object.has("optional") ? object.get("optional").getAsBoolean() : false;
		boolean manualReturn = object.has("manualReturn") ? object.get("manualReturn").getAsBoolean() : false;
		boolean isDynamic = object.has("isDynamic") ? object.get("isDynamic").getAsBoolean() : false;
		boolean dynamicSource = object.has("dynamicSource") ? object.get("dynamicSource").getAsBoolean() : false;
		boolean consume = object.has("consume") ? object.get("consume").getAsBoolean() : true;
		
		RitualKey key = new RitualKey(item, optional, manualReturn, isDynamic, dynamicSource, consume);
		MnaJS.LOGGER.info("return RitualKey");
		return key;
	}
	
	
	public static RitualKey of(Object object) {
		if (object instanceof RitualKey ritualKey) {
			return ritualKey;
		} else if (object instanceof JsonElement element) {
			MnaJS.LOGGER.info("object instanceof JsonElement element");
			return ofJson(element);
		} else if (object instanceof Map map) {
			if(map.containsKey("item")){
				InputItem item = InputItem.of(map.get("item"));
				boolean optional = map.containsKey("optional") ? (boolean) map.get("optional") : false;
				boolean manualReturn = map.containsKey("manualReturn") ? (boolean) map.get("manualReturn") : false;
				boolean isDynamic = map.containsKey("isDynamic") ? (boolean) map.get("isDynamic") : false;
				boolean dynamicSource = map.containsKey("dynamicSource") ? (boolean) map.get("dynamicSource") : false;
				boolean consume = map.containsKey("consume") ? (boolean) map.get("consume") : true;
				RitualKey key = new RitualKey(item, optional, manualReturn, isDynamic, dynamicSource, consume);
				MnaJS.LOGGER.info("return RitualKey 2");
				return key;
			}else {
				MnaJS.LOGGER.error("item field is not exit");
				return EMPTY;
			}
		} else {
			MnaJS.LOGGER.info("RitualKey pf(Object) EMPTY, class: {}", object.getClass());
			return EMPTY;
		}
	}
}
