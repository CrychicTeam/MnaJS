package com.pickaid.mnajs.kubejs;

import com.mna.api.affinity.Affinity;
import com.mna.api.entities.construct.ConstructCapability;
import com.mna.api.entities.construct.ConstructMaterial;
import com.mna.api.entities.construct.ConstructSlot;
import com.mna.api.entities.construct.ItemConstructPart;
import com.mna.api.entities.construct.ai.ConstructAITask;
import com.mna.api.entities.construct.ai.ConstructTask;
import com.mna.api.entities.construct.ai.parameter.ConstructAITaskParameter;
import com.mna.api.entities.construct.ai.parameter.ConstructParameterTypes;
import com.mna.api.entities.construct.ai.parameter.ConstructTaskAreaParameter;
import com.mna.api.entities.construct.ai.parameter.ConstructTaskBooleanParameter;
import com.mna.api.entities.construct.ai.parameter.ConstructTaskFilterParameter;
import com.mna.api.entities.construct.ai.parameter.ConstructTaskIntegerParameter;
import com.mna.api.entities.construct.ai.parameter.ConstructTaskItemStackParameter;
import com.mna.api.entities.construct.ai.parameter.ConstructTaskPointParameter;
import com.mna.api.events.ProgressionEventIDs;
import com.mna.api.faction.IFaction;
import com.mna.api.items.ItemUtils;
import com.mna.api.rituals.RitualEffect;
import com.mna.api.spells.collections.Modifiers;
import com.mna.api.spells.collections.Shapes;
import com.mna.api.spells.ComponentApplicationResult;
import com.mna.api.spells.attributes.Attribute;
import com.mna.api.spells.parts.Modifier;
import com.mna.api.spells.parts.Shape;
import com.mna.api.spells.parts.SpellEffect;
import com.mna.api.spells.targeting.SpellTarget;
import com.mna.api.tools.CollectionUtils;
import com.mna.api.tools.MATags;
import com.mna.apibridge.EntityHelper;
import com.mna.apibridge.FactionRaidHelper;
import com.mna.entities.constructs.animated.ConstructMutexConstants;
import com.mna.entities.constructs.ai.base.ConstructTasks;
import com.mna.factions.Factions;
import com.mna.tools.BiomeUtils;
import com.mna.tools.EntityUtil;
import com.mna.tools.InventoryUtilities;
import com.mna.tools.ParticleConfigurations;
import com.mna.tools.ProjectileHelper;
import com.mna.tools.RecipeUtil;
import com.mna.tools.ShearHelper;
import com.mna.tools.StructureUtils;
import com.mna.tools.SummonUtils;
import com.mna.tools.math.MathUtils;
import com.mna.tools.render.GuiRenderUtils;
import com.mna.tools.render.WorldRenderUtils;
import com.pickaid.mnajs.content.CustomFaction;
import com.pickaid.mnajs.content.CustomRitualEffect;
import com.pickaid.mnajs.content.construct.CustomConstructMaterial;
import com.pickaid.mnajs.content.construct.CustomConstructPartItem;
import com.pickaid.mnajs.content.construct.CustomConstructTask;
import com.pickaid.mnajs.content.blocks.CustomManaweaveNotifiableBlock;
import com.pickaid.mnajs.content.blocks.CustomSpellInteractibleBlock;
import com.pickaid.mnajs.content.items.CustomManaBatteryItem;
import com.pickaid.mnajs.content.items.CustomManaItem;
import com.pickaid.mnajs.content.spell.CustomDamageComponent;
import com.pickaid.mnajs.content.spell.CustomModifier;
import com.pickaid.mnajs.content.spell.CustomPotionEffectComponent;
import com.pickaid.mnajs.content.spell.CustomShape;
import com.pickaid.mnajs.content.spell.CustomSpellEffect;
import com.pickaid.mnajs.kubejs.id.MnaAdvancementId;
import com.pickaid.mnajs.kubejs.id.MnaCantripId;
import com.pickaid.mnajs.kubejs.id.MnaBlockId;
import com.pickaid.mnajs.kubejs.id.MnaCastingResourceId;
import com.pickaid.mnajs.kubejs.id.MnaConstructMaterialId;
import com.pickaid.mnajs.kubejs.id.MnaConstructCapabilityId;
import com.pickaid.mnajs.kubejs.id.MnaConstructSlotId;
import com.pickaid.mnajs.kubejs.id.MnaFactionId;
import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.kubejs.id.MnaItemOrTag;
import com.pickaid.mnajs.kubejs.id.MnaLootTableId;
import com.pickaid.mnajs.kubejs.id.MnaManaweavePatternId;
import com.pickaid.mnajs.kubejs.id.MnaMobEffectId;
import com.pickaid.mnajs.kubejs.id.MnaConstructTaskId;
import com.pickaid.mnajs.kubejs.id.MnaModifierId;
import com.pickaid.mnajs.kubejs.id.MnaProgressionEventId;
import com.pickaid.mnajs.kubejs.id.MnaRitualEffectId;
import com.pickaid.mnajs.kubejs.id.MnaRitualId;
import com.pickaid.mnajs.kubejs.id.MnaShapeId;
import com.pickaid.mnajs.kubejs.id.MnaSoundId;
import com.pickaid.mnajs.kubejs.id.MnaSpellEffectId;
import com.pickaid.mnajs.kubejs.id.MnaStructureId;
import com.pickaid.mnajs.kubejs.id.MnaTypedIdLookups;
import com.pickaid.mnajs.kubejs.pattern.MnaPatternHelper;
import com.pickaid.mnajs.kubejs.recipe.MnaRitualReagent;
import com.pickaid.mnajs.kubejs.texture.MnaTexture;
import com.pickaid.mnajs.registry.MnaJSRegistries;
import com.pickaid.mnajs.recipes.RecipesHelper;
import com.pickaid.mnajs.recipes.component.ItemComponent;
import com.pickaid.mnajs.recipes.component.ItemOrTagComponent;
import com.pickaid.mnajs.recipes.component.ItemStackComponent;
import com.pickaid.mnajs.recipes.component.ItemsOrTagsComponent;
import com.pickaid.mnajs.recipes.component.mna.MnaRecipeComponents;
import com.pickaid.mnajs.recipes.component.mna.PowerProvidedComponent;
import com.pickaid.mnajs.recipes.schema.ArcaneFurnaceSchema;
import com.pickaid.mnajs.recipes.schema.ComponentSchema;
import com.pickaid.mnajs.recipes.schema.CrushingSchema;
import com.pickaid.mnajs.recipes.schema.EldrinAltarSchema;
import com.pickaid.mnajs.recipes.schema.FumerFliterSchema;
import com.pickaid.mnajs.recipes.schema.ManaweaveCacheEffectSchema;
import com.pickaid.mnajs.recipes.schema.ManaweavingAltarSchema;
import com.pickaid.mnajs.recipes.schema.ManaweavingPatternSchema;
import com.pickaid.mnajs.recipes.schema.ModifierSchema;
import com.pickaid.mnajs.recipes.schema.ProgressionSchema;
import com.pickaid.mnajs.recipes.schema.RitualRecipeSchema;
import com.pickaid.mnajs.recipes.schema.RuneForgingSchema;
import com.pickaid.mnajs.recipes.schema.RunescribingSchema;
import com.pickaid.mnajs.recipes.schema.ShapeSchema;
import com.pickaid.mnajs.recipes.schema.TransmutationSchema;
import com.pickaid.mnajs.util.PlayerProgressionHelper;
import com.pickaid.mnajs.util.PlayerUtil;
import com.pickaid.mnajs.util.ProgressionEvents;
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
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.util.Lazy;

public class MnaJSPlugin extends KubeJSPlugin {
    public static final Lazy<RegistryInfo<IFaction>> FACTION_REGISTRY =
            Lazy.of(() -> RegistryInfo.of(ResourceKey.createRegistryKey(new ResourceLocation("mna", "factions")), IFaction.class));
    public static final Lazy<RegistryInfo<RitualEffect>> RITUAL_EFFECT_REGISTRY =
            Lazy.of(() -> RegistryInfo.of(ResourceKey.createRegistryKey(new ResourceLocation("mna", "ritual-effects")), RitualEffect.class));
    public static final Lazy<RegistryInfo<SpellEffect>> SPELL_EFFECT =
            Lazy.of(() -> RegistryInfo.of(ResourceKey.createRegistryKey(new ResourceLocation("mna", "components")), SpellEffect.class));
    public static final Lazy<RegistryInfo<Shape>> SPELL_SHAPE =
            Lazy.of(() -> RegistryInfo.of(ResourceKey.createRegistryKey(new ResourceLocation("mna", "shapes")), Shape.class));
    public static final Lazy<RegistryInfo<Modifier>> MODIFIER_REGISTRY =
            Lazy.of(() -> RegistryInfo.of(ResourceKey.createRegistryKey(new ResourceLocation("mna", "modifiers")), Modifier.class));
    public static final Lazy<RegistryInfo<ConstructTask>> CONSTRUCT_TASK_REGISTRY =
            Lazy.of(() -> RegistryInfo.of(ResourceKey.createRegistryKey(new ResourceLocation("mna", "construct_task")), ConstructTask.class));
    public static final Lazy<RegistryInfo<ConstructMaterial>> CONSTRUCT_MATERIAL_REGISTRY =
            Lazy.of(() -> RegistryInfo.of(MnaJSRegistries.CONSTRUCT_MATERIAL_REGISTRY_KEY, ConstructMaterial.class));

    @Override
    public void registerEvents() {
        MnaJSEvents.GROUP.register();
        MnaJSEvents.PLAYER_GROUP.register();
        MnaJSEvents.RUNE_FORGE_GROUP.register();
        MnaJSEvents.SPELL_GROUP.register();
    }

    @Override
    public void init() {
        FACTION_REGISTRY.get().addType("basic", CustomFaction.Builder.class, CustomFaction.Builder::new);
        RITUAL_EFFECT_REGISTRY.get().addType("basic", CustomRitualEffect.Builder.class, CustomRitualEffect.Builder::new);

        SPELL_EFFECT.get().addType("basic", CustomSpellEffect.Builder.class, CustomSpellEffect.Builder::new);
        SPELL_EFFECT.get().addType("damage", CustomDamageComponent.Builder.class, CustomDamageComponent.Builder::new);
        SPELL_EFFECT.get().addType("potion", CustomPotionEffectComponent.Builder.class, CustomPotionEffectComponent.Builder::new);

        SPELL_SHAPE.get().addType("basic", CustomShape.Builder.class, CustomShape.Builder::new);
        MODIFIER_REGISTRY.get().addType("basic", CustomModifier.Builder.class, CustomModifier.Builder::new);
        CONSTRUCT_TASK_REGISTRY.get().addType("basic", CustomConstructTask.Builder.class, CustomConstructTask.Builder::new);
        CONSTRUCT_MATERIAL_REGISTRY.get().addType("basic", CustomConstructMaterial.Builder.class, CustomConstructMaterial.Builder::new);

        RegistryInfo.ITEM.addType("mana_battery_item", CustomManaBatteryItem.Builder.class, CustomManaBatteryItem.Builder::new);
        RegistryInfo.ITEM.addType("tiered_item", CustomManaItem.Builder.class, CustomManaItem.Builder::new);
        RegistryInfo.ITEM.addType("construct_part", CustomConstructPartItem.Builder.class, CustomConstructPartItem.Builder::new);

        RegistryInfo.BLOCK.addType("spell_interactible", CustomSpellInteractibleBlock.Builder.class, CustomSpellInteractibleBlock.Builder::new);
        RegistryInfo.BLOCK.addType("manaweave_notifiable", CustomManaweaveNotifiableBlock.Builder.class, CustomManaweaveNotifiableBlock.Builder::new);
    }

    @Override
    public void registerTypeWrappers(ScriptType type, TypeWrappers typeWrappers) {
        typeWrappers.registerSimple(MnaAdvancementId.class, MnaAdvancementId::parse);
        typeWrappers.registerSimple(MnaProgressionEventId.class, MnaProgressionEventId::parse);
        typeWrappers.registerSimple(MnaFactionId.class, MnaFactionId::parse);
        typeWrappers.registerSimple(MnaCastingResourceId.class, MnaCastingResourceId::parse);
        typeWrappers.registerSimple(MnaConstructMaterialId.class, MnaConstructMaterialId::parse);
        typeWrappers.registerSimple(MnaConstructSlotId.class, MnaConstructSlotId::parse);
        typeWrappers.registerSimple(MnaConstructCapabilityId.class, MnaConstructCapabilityId::parse);
        typeWrappers.registerSimple(MnaMobEffectId.class, MnaMobEffectId::parse);
        typeWrappers.registerSimple(MnaRitualEffectId.class, MnaRitualEffectId::parse);
        typeWrappers.registerSimple(MnaSpellEffectId.class, MnaSpellEffectId::parse);
        typeWrappers.registerSimple(MnaShapeId.class, MnaShapeId::parse);
        typeWrappers.registerSimple(MnaModifierId.class, MnaModifierId::parse);
        typeWrappers.registerSimple(MnaConstructTaskId.class, MnaConstructTaskId::parse);
        typeWrappers.registerSimple(MnaRitualId.class, MnaRitualId::parse);
        typeWrappers.registerSimple(MnaManaweavePatternId.class, MnaManaweavePatternId::parse);
        typeWrappers.registerSimple(MnaCantripId.class, MnaCantripId::parse);
        typeWrappers.registerSimple(MnaItemId.class, MnaItemId::parse);
        typeWrappers.registerSimple(MnaBlockId.class, MnaBlockId::parse);
        typeWrappers.registerSimple(MnaItemOrTag.class, MnaItemOrTag::parse);
        typeWrappers.registerSimple(MnaLootTableId.class, MnaLootTableId::parse);
        typeWrappers.registerSimple(MnaSoundId.class, MnaSoundId::parse);
        typeWrappers.registerSimple(MnaStructureId.class, MnaStructureId::parse);
        typeWrappers.registerSimple(MnaTexture.class, MnaTexture::parse);
        typeWrappers.registerSimple(IFaction.class, o -> {
            if (o instanceof IFaction faction) {
                return faction;
            }
            return MnaTypedIdLookups.requireFaction(MnaFactionId.parse(o), "faction");
        });
        typeWrappers.registerSimple(RitualEffect.class, o -> {
            if (o instanceof RitualEffect effect) {
                return effect;
            }
            return MnaTypedIdLookups.requireRitualEffect(MnaRitualEffectId.parse(o), "ritualEffect");
        });
        typeWrappers.registerSimple(SpellEffect.class, o -> {
            if (o instanceof SpellEffect effect) {
                return effect;
            }
            return MnaTypedIdLookups.requireSpellEffect(MnaSpellEffectId.parse(o), "spellEffect");
        });
        typeWrappers.registerSimple(Shape.class, o -> {
            if (o instanceof Shape shape) {
                return shape;
            }
            return MnaTypedIdLookups.requireShape(MnaShapeId.parse(o), "shape");
        });
        typeWrappers.registerSimple(Modifier.class, o -> {
            if (o instanceof Modifier modifier) {
                return modifier;
            }
            return MnaTypedIdLookups.requireModifier(MnaModifierId.parse(o), "modifier");
        });
        typeWrappers.registerSimple(ConstructTask.class, o -> {
            if (o instanceof ConstructTask task) {
                return task;
            }
            return MnaTypedIdLookups.requireConstructTask(MnaConstructTaskId.parse(o), "constructTask");
        });
        typeWrappers.registerSimple(ConstructMaterial.class, o -> {
            if (o instanceof ConstructMaterial material) {
                return material;
            }
            return MnaTypedIdLookups.requireConstructMaterial(MnaConstructMaterialId.parse(o), "constructMaterial");
        });
        typeWrappers.registerSimple(ConstructSlot.class, o -> {
            if (o instanceof ConstructSlot slot) {
                return slot;
            }
            return MnaTypedIdLookups.requireConstructSlot(MnaConstructSlotId.parse(o), "constructSlot");
        });
        typeWrappers.registerSimple(ConstructCapability.class, o -> {
            if (o instanceof ConstructCapability capability) {
                return capability;
            }
            return MnaTypedIdLookups.requireConstructCapability(MnaConstructCapabilityId.parse(o), "constructCapability");
        });
        typeWrappers.registerSimple(ItemConstructPart.class, o -> {
            if (o instanceof ItemConstructPart part) {
                return part;
            }
            return MnaTypedIdLookups.requireConstructPart(MnaItemId.parse(o), "constructPart");
        });
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("Affinity", Affinity.class);
        event.add("Attribute", Attribute.class);
        event.add("ComponentApplicationResult", ComponentApplicationResult.class);
        event.add("SpellAttribute", Attribute.class);
        event.add("SpellTarget", SpellTarget.class);
        event.add("MnaShapes", Shapes.class);
        event.add("MnaModifiers", Modifiers.class);
        event.add("MnaConstructTasks", ConstructTasks.class);
        event.add("ConstructCapability", ConstructCapability.class);
        event.add("ConstructSlot", ConstructSlot.class);
        event.add("ConstructMaterial", ConstructMaterial.class);
        event.add("ItemConstructPart", ItemConstructPart.class);
        event.add("ConstructParameterTypes", ConstructParameterTypes.class);
        event.add("ConstructTask", ConstructTask.class);
        event.add("ConstructAITask", ConstructAITask.class);
        event.add("ConstructAITaskParameter", ConstructAITaskParameter.class);
        event.add("ConstructTaskBooleanParameter", ConstructTaskBooleanParameter.class);
        event.add("ConstructTaskIntegerParameter", ConstructTaskIntegerParameter.class);
        event.add("ConstructTaskItemStackParameter", ConstructTaskItemStackParameter.class);
        event.add("ConstructTaskPointParameter", ConstructTaskPointParameter.class);
        event.add("ConstructTaskAreaParameter", ConstructTaskAreaParameter.class);
        event.add("ConstructTaskFilterParameter", ConstructTaskFilterParameter.class);
        event.add("ConstructMutexArms", ConstructMutexConstants.Arms.class);
        event.add("ConstructMutexHead", ConstructMutexConstants.Head.class);
        event.add("ConstructMutexLegs", ConstructMutexConstants.Legs.class);
        event.add("ConstructMutexTorso", ConstructMutexConstants.Torso.class);
        event.add("Tier", Tier.class);
        event.add("Tiers", Tiers.class);
        event.add("CollectionUtils", CollectionUtils.class);
        event.add("MATags", MATags.class);
        event.add("MathUtils", MathUtils.class);
        event.add("BiomeUtils", BiomeUtils.class);
        event.add("ProjectileHelper", ProjectileHelper.class);
        event.add("PlayerMagic", PlayerUtil.class);
        event.add("PlayerProgression", PlayerProgressionHelper.class);
        event.add("ProgressionEvents", ProgressionEvents.class);
        event.add("WorldMagic", WorldMagic.class);
        event.add("InventoryUtilities", InventoryUtilities.class);
        event.add("RecipeUtil", RecipeUtil.class);
        event.add("MnaPatternHelper", MnaPatternHelper.class);
        event.add("MnaRitualReagent", MnaRitualReagent.class);

        if (event.getType().isServer()) {
            event.add("MNARecipesHelper", RecipesHelper.class);
            event.add("ProgressionEventIDs", ProgressionEventIDs.class);
            event.add("StructureUtils", StructureUtils.class);
            event.add("EntityUtil", EntityUtil.class);
            event.add("SummonUtils", SummonUtils.class);
            event.add("ShearHelper", ShearHelper.class);
            event.add("MnaFactionUtil", Factions.class);
            event.add("ManaItemUtil", ItemUtils.class);
            event.add("MnaEntityHelper", EntityHelper.class);
            event.add("MnaFactionRaidHelper", FactionRaidHelper.class);
        }

        if (event.getType().isClient()) {
            event.add("WorldRenderUtils", WorldRenderUtils.class);
            event.add("GuiRenderUtils", GuiRenderUtils.class);
            event.add("ParticleConfigurations", ParticleConfigurations.class);
        }
    }

    @Override
    public void registerRecipeComponents(RecipeComponentFactoryRegistryEvent event) {
        event.register("mnaAdvancementId", MnaRecipeComponents.ADVANCEMENT_ID);
        event.register("power_key", PowerProvidedComponent.POWER_PROVIDED_COMPONENT);
        event.register("item", ItemComponent.ITEM);
        event.register("itemStack", ItemStackComponent.ITEMSTACK);
        event.register("itemOrTagComponent", ItemOrTagComponent.ITEM_OR_TAG_COMPONENT);
        event.register("itemsOrTagsComponent", ItemsOrTagsComponent.ITEMS_OR_TAGS_COMPONENT);
        event.register("mnaItemId", MnaRecipeComponents.ITEM_ID);
        event.register("mnaBlockId", MnaRecipeComponents.BLOCK_ID);
        event.register("mnaItemOrTag", MnaRecipeComponents.ITEM_OR_TAG);
        event.register("mnaLootTableId", MnaRecipeComponents.LOOT_TABLE_ID);
        event.register("mnaFactionId", MnaRecipeComponents.FACTION_ID);
        event.register("mnaCastingResourceId", MnaRecipeComponents.CASTING_RESOURCE_ID);
        event.register("mnaMobEffectId", MnaRecipeComponents.MOB_EFFECT_ID);
        event.register("mnaRitualEffectId", MnaRecipeComponents.RITUAL_EFFECT_ID);
        event.register("mnaSpellEffectId", MnaRecipeComponents.SPELL_EFFECT_ID);
        event.register("mnaShapeId", MnaRecipeComponents.SHAPE_ID);
        event.register("mnaModifierId", MnaRecipeComponents.MODIFIER_ID);
        event.register("mnaRitualId", MnaRecipeComponents.RITUAL_ID);
        event.register("mnaManaweavePatternId", MnaRecipeComponents.MANAWEAVE_PATTERN_ID);
        event.register("mnaCantripId", MnaRecipeComponents.CANTRIP_ID);
        event.register("mnaSoundId", MnaRecipeComponents.SOUND_ID);
        event.register("mnaStructureId", MnaRecipeComponents.STRUCTURE_ID);
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

        mnaNamespace.put("progression", new RecipeSchemaType(mnaNamespace, new ResourceLocation("mna", "progression-condition"), ProgressionSchema.SCHEMA));
        mnaNamespace.put("manaweavingAltar", new RecipeSchemaType(mnaNamespace, new ResourceLocation("mna", "manaweaving-recipe"), ManaweavingAltarSchema.SCHEMA));
        mnaNamespace.put("arcaneFurnace", new RecipeSchemaType(mnaNamespace, new ResourceLocation("mna", "arcane-furnace"), ArcaneFurnaceSchema.SCHEMA));
        mnaNamespace.put("eldrinAltar", new RecipeSchemaType(mnaNamespace, new ResourceLocation("mna", "eldrin-altar"), EldrinAltarSchema.SCHEMA));
        mnaNamespace.put("eldrinFume", new RecipeSchemaType(mnaNamespace, new ResourceLocation("mna", "eldrin-fume"), FumerFliterSchema.SCHEMA));
        mnaNamespace.put("pattern", new RecipeSchemaType(mnaNamespace, new ResourceLocation("mna", "manaweaving-pattern"), ManaweavingPatternSchema.SCHEMA));
        mnaNamespace.put("cacheEffect", new RecipeSchemaType(mnaNamespace, new ResourceLocation("mna", "manaweave-cache-effect"), ManaweaveCacheEffectSchema.SCHEMA));
    }
}
