package com.pickaid.mnajs.kubejs;

import com.mna.api.events.ProgressionEventIDs;
import com.mna.api.faction.IFaction;
import com.mna.api.rituals.RitualEffect;
import com.mna.api.spells.parts.SpellEffect;
import com.pickaid.mnajs.content.CustomFaction;
import com.pickaid.mnajs.content.CustomRitualEffect;
import com.pickaid.mnajs.content.CustomSpellEffect;
import com.pickaid.mnajs.content.items.CustomManaBatteryItem;
import com.pickaid.mnajs.recipes.RecipesHelper;
import com.pickaid.mnajs.recipes.component.IRitualKeyComponent;
import com.pickaid.mnajs.recipes.component.InputItemsComponent;
import com.pickaid.mnajs.recipes.component.PowerProvidedComponent;
import com.pickaid.mnajs.recipes.schema.*;
import com.pickaid.mnajs.recipes.schema.Basic.ItemBaseSchema;
import com.pickaid.mnajs.util.PlayerMagic;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RecipeComponentFactoryRegistryEvent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaType;
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.Lazy;

public class MnaJSPlugin extends KubeJSPlugin {
    public static final Lazy<RegistryInfo<IFaction>> FACTION_REGISTRY = Lazy.of(() -> RegistryInfo.of(ResourceKey.createRegistryKey(new ResourceLocation("mna:factions")), IFaction.class));
	public static final Lazy<RegistryInfo<RitualEffect>> RITUAL_EFFECT_REGISTRY = Lazy.of(() -> RegistryInfo.of(ResourceKey.createRegistryKey(new ResourceLocation("mna:ritual-effects")),RitualEffect.class));
	public static final Lazy<RegistryInfo<SpellEffect>> SPELL_EFFECT = Lazy.of(() -> RegistryInfo.of(ResourceKey.createRegistryKey(new ResourceLocation("mna:components")),SpellEffect.class));


	@Override
	public void registerEvents() {
		MnaJSEvents.GROUP.register();
	}
	
	@Override
	public void init() {
		FACTION_REGISTRY.get().addType("basic", CustomFaction.Builder.class, CustomFaction.Builder::new);
		RITUAL_EFFECT_REGISTRY.get().addType("basic", CustomRitualEffect.Builder.class, CustomRitualEffect.Builder::new);
		SPELL_EFFECT.get().addType("basic", CustomSpellEffect.Builder.class, CustomSpellEffect.Builder::new);

		RegistryInfo.ITEM.addType("mana_battery_item" , CustomManaBatteryItem.Builder.class, CustomManaBatteryItem.Builder::new);
	}

	@Override
	public void registerBindings(BindingsEvent event) {
		event.add("MNARecipesHelper", RecipesHelper.class);
		event.add("PlayerMagic", PlayerMagic.class);
		event.add("ProgressionEventIDs",ProgressionEventIDs.class);
	}

	@Override
	public void registerRecipeComponents(RecipeComponentFactoryRegistryEvent event) {
		event.register("ritualKey", IRitualKeyComponent.RITUAL_PATTERN_KEY);
		event.register("item", ItemBaseSchema.ITEM);
		event.register("itemStack", ItemBaseSchema.ITEMSTACK);
		event.register("inputItems", InputItemsComponent.INPUT_ITEMS);
		event.register("power_requirements", PowerProvidedComponent.POWER_PROVIDED_COMPONENT);
	}

	@Override
	public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
		event.namespace("mna")
				.register("ritual", RitualRecipeSchema.SCHEMA)
				.register("transmutation", TransmutationSchema.SCHEMA)
				.register("crushing", CrushingSchema.SCHEMA)
				.register("runeforging", RuneForgingSchema.SCHEMA)
				.register("runescribing", RunescribingSchema.SCHEMA);
		event.namespace("mna")
				.put("progression", new RecipeSchemaType(event.namespace("mna")
						, new ResourceLocation("mna:progression-condition")
						, ProgressionSchema.SCHEMA));
		event.namespace("mna")
				.put("manaweavingAltar", new RecipeSchemaType(event.namespace("mna")
						, new ResourceLocation("mna:manaweaving-recipe")
						, ManaweavingAltarSchema.SCHEMA));
		event.namespace("mna")
				.put("arcaneFurnace", new RecipeSchemaType(event.namespace("mna")
						, new ResourceLocation("mna:arcane-furnace")
						, ArcaneFurnaceSchema.SCHEMA));
		event.namespace("mna")
				.put("eldrinAltar", new RecipeSchemaType(event.namespace("mna")
						, new ResourceLocation("mna:eldrin-altar")
						, EldrinAltarSchema.SCHEMA));
		event.namespace("mna")
				.put("Fumefilter", new RecipeSchemaType(event.namespace("mna")
						, new ResourceLocation("mna:eldrin-fume")
						, FumerFliterSchema.SCHEMA));

	}
}

