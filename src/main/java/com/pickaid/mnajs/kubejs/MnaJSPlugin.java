package com.pickaid.mnajs.kubejs;

import com.mna.api.affinity.Affinity;
import com.mna.api.events.ProgressionEventIDs;
import com.mna.api.faction.IFaction;
import com.mna.api.items.ItemUtils;
import com.mna.api.rituals.RitualEffect;
import com.mna.api.spells.attributes.Attribute;
import com.mna.api.spells.parts.Shape;
import com.mna.api.spells.parts.SpellEffect;
import com.mna.apibridge.EntityHelper;
import com.mna.apibridge.FactionRaidHelper;
import com.mna.factions.Factions;
import com.mna.tools.render.WorldRenderUtils;
import com.pickaid.mnajs.content.CustomFaction;
import com.pickaid.mnajs.content.CustomRitualEffect;
import com.pickaid.mnajs.content.blocks.CustomManaweaveNotifiableBlock;
import com.pickaid.mnajs.content.blocks.CustomSpellInteractibleBlock;
import com.pickaid.mnajs.content.items.CustomManaBatteryItem;
import com.pickaid.mnajs.content.items.CustomManaItem;
import com.pickaid.mnajs.content.spell.CustomDamageComponent;
import com.pickaid.mnajs.content.spell.CustomPotionEffectComponent;
import com.pickaid.mnajs.content.spell.CustomShape;
import com.pickaid.mnajs.content.spell.CustomSpellEffect;
import com.pickaid.mnajs.recipes.RecipesHelper;
import com.pickaid.mnajs.recipes.component.ItemComponent;
import com.pickaid.mnajs.recipes.component.ItemOrTagComponent;
import com.pickaid.mnajs.recipes.component.ItemStackComponent;
import com.pickaid.mnajs.recipes.component.ItemsOrTagsComponent;
import com.pickaid.mnajs.recipes.component.mna.PowerProvidedComponent;
import com.pickaid.mnajs.recipes.schema.*;
import com.pickaid.mnajs.util.MnaUtils;
import com.pickaid.mnajs.util.PlayerUtil;
import com.pickaid.mnajs.util.TypeWrap;
import com.pickaid.mnajs.util.WorldMagic;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RecipeComponentFactoryRegistryEvent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaType;
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.rhino.util.wrap.TypeWrappers;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.Lazy;

public class MnaJSPlugin extends KubeJSPlugin {
    public static final Lazy<RegistryInfo<IFaction>> FACTION_REGISTRY
			= Lazy.of(() -> RegistryInfo.of(ResourceKey.createRegistryKey(new ResourceLocation("mna:factions")), IFaction.class));
	public static final Lazy<RegistryInfo<RitualEffect>> RITUAL_EFFECT_REGISTRY
			= Lazy.of(() -> RegistryInfo.of(ResourceKey.createRegistryKey(new ResourceLocation("mna:ritual-effects")), RitualEffect.class));
	public static final Lazy<RegistryInfo<SpellEffect>> SPELL_EFFECT
			= Lazy.of(() -> RegistryInfo.of(ResourceKey.createRegistryKey(new ResourceLocation("mna:components")), SpellEffect.class));
	public static final Lazy<RegistryInfo<Shape>> SPELL_SHAPE
			= Lazy.of(() -> RegistryInfo.of(ResourceKey.createRegistryKey(new ResourceLocation("mna:shape")), Shape.class));


	@Override
	public void registerEvents() {
		MnaJSEvents.GROUP.register();
		MnaJSEvents.RUNE_FORGE_GROUP.register();
		MnaJSEvents.SPELL_GROUP.register();
	}
	
	@Override
	public void init() {
		FACTION_REGISTRY.get().addType("basic", CustomFaction.Builder.class, CustomFaction.Builder::new);
		RITUAL_EFFECT_REGISTRY.get().addType("basic", CustomRitualEffect.Builder.class, CustomRitualEffect.Builder::new);

		SPELL_EFFECT.get().addType("basic", CustomSpellEffect.Builder.class, CustomSpellEffect.Builder::new);
		SPELL_EFFECT.get().addType("damage", CustomDamageComponent.Builder.class, CustomSpellEffect.Builder::new);
		SPELL_EFFECT.get().addType("potion", CustomPotionEffectComponent.Builder.class, CustomSpellEffect.Builder::new);

		SPELL_SHAPE.get().addType("basic", CustomShape.Builder.class, CustomShape.Builder::new);

		RegistryInfo.ITEM.addType("mana_battery_item" , CustomManaBatteryItem.Builder.class, CustomManaBatteryItem.Builder::new);
		RegistryInfo.ITEM.addType("tiered_item" , CustomManaItem.Builder.class, CustomManaItem.Builder::new);

		RegistryInfo.BLOCK.addType("spell_interactible" , CustomSpellInteractibleBlock.Builder.class, CustomSpellInteractibleBlock.Builder::new);
		RegistryInfo.BLOCK.addType("manaweave_notifiable" , CustomManaweaveNotifiableBlock.Builder.class, CustomManaweaveNotifiableBlock.Builder::new);
	}

	@Override
	public void registerTypeWrappers(ScriptType type, TypeWrappers typeWrappers) {
		typeWrappers.registerSimple(IFaction.class, o -> {
			if (o instanceof IFaction faction) return faction;
			return Factions.INSTANCE.getFaction(TypeWrap.FactionHolder.of(o).getLocation());
		});
	}

	@Override
	public void registerBindings(BindingsEvent event) {
		event.add("MNARecipesHelper", RecipesHelper.class);
		event.add("ProgressionEventIDs",ProgressionEventIDs.class);
		event.add("PlayerMagic", PlayerUtil.class);
		event.add("WorldMagic", WorldMagic.class);
		event.add("MnaUtils", MnaUtils.class);
		event.add("Affinity", Affinity.class);
		event.add("SpellAttribute", Attribute.class);

		event.add("MnaFactionUtil", Factions.class);
		event.add("ManaItemUtil", ItemUtils.class);
		event.add("WorldRenderUtils", WorldRenderUtils.class);
		event.add("MnaEntityHelper", EntityHelper.class);
		event.add("MnaFactionRaidHelper", FactionRaidHelper.class);
	}

	@Override
	public void registerRecipeComponents(RecipeComponentFactoryRegistryEvent event) {
		event.register("power_key", PowerProvidedComponent.POWER_PROVIDED_COMPONENT);
		event.register("item", ItemComponent.ITEM);
		event.register("itemStack", ItemStackComponent.ITEMSTACK);
		event.register("itemOrTagComponent", ItemOrTagComponent.ITEM_OR_TAG_COMPONENT);
		event.register("itemsOrTagsComponent", ItemsOrTagsComponent.ITEMS_OR_TAGS_COMPONENT);
	}

	@Override
	public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
		var mnaNamespace = event.namespace("mna");
		mnaNamespace
				.register("ritual", RitualRecipeSchema.SCHEMA)
				.register("transmutation", TransmutationSchema.SCHEMA)
				.register("crushing", CrushingSchema.SCHEMA)
				.register("runeforging", RuneForgingSchema.SCHEMA)
				.register("runescribing", RunescribingSchema.SCHEMA)
				.register("component", ComponentSchema.SCHEMA)
				.register("modifier", ModifierSchema.SCHEMA)
				.register("shape", ShapeSchema.SCHEMA);



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
				.put("eldrinFume", new RecipeSchemaType(event.namespace("mna")
						, new ResourceLocation("mna:eldrin-fume")
						, FumerFliterSchema.SCHEMA));
//		event.namespace("mna")
//				.put("pattern", new RecipeSchemaType(event.namespace("mna")
//						, new ResourceLocation("mna:manaweaving-pattern")
//						, ManaweavingPatternSchema.SCHEMA));
		event.namespace("mna")
				.put("cacheEffect", new RecipeSchemaType(event.namespace("mna")
						, new ResourceLocation("mna:manaweave-cache-effect")
						, ManaweaveCacheEffectSchema.SCHEMA));
	}
}

