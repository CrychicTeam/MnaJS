package com.pickaid.mnajs.kubejs;

import com.mna.api.affinity.Affinity;
import com.mna.api.events.ProgressionEventIDs;
import com.mna.api.faction.IFaction;
import com.mna.api.items.ItemUtils;
import com.mna.api.rituals.RitualEffect;
import com.mna.api.spells.attributes.Attribute;
import com.mna.api.spells.parts.Shape;
import com.mna.api.spells.parts.SpellEffect;
import com.mna.api.tools.CollectionUtils;
import com.mna.api.tools.MATags;
import com.mna.apibridge.EntityHelper;
import com.mna.apibridge.FactionRaidHelper;
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
import com.pickaid.mnajs.content.blocks.CustomManaweaveNotifiableBlock;
import com.pickaid.mnajs.content.blocks.CustomSpellInteractibleBlock;
import com.pickaid.mnajs.content.items.CustomManaBatteryItem;
import com.pickaid.mnajs.content.items.CustomManaItem;
import com.pickaid.mnajs.content.spell.CustomDamageComponent;
import com.pickaid.mnajs.content.spell.CustomPotionEffectComponent;
import com.pickaid.mnajs.content.spell.CustomShape;
import com.pickaid.mnajs.content.spell.CustomSpellEffect;
import com.pickaid.mnajs.kubejs.id.MnaCantripId;
import com.pickaid.mnajs.kubejs.id.MnaBlockId;
import com.pickaid.mnajs.kubejs.id.MnaFactionId;
import com.pickaid.mnajs.kubejs.id.MnaItemId;
import com.pickaid.mnajs.kubejs.id.MnaItemOrTag;
import com.pickaid.mnajs.kubejs.id.MnaLootTableId;
import com.pickaid.mnajs.kubejs.id.MnaManaweavePatternId;
import com.pickaid.mnajs.kubejs.id.MnaModifierId;
import com.pickaid.mnajs.kubejs.id.MnaRitualEffectId;
import com.pickaid.mnajs.kubejs.id.MnaRitualId;
import com.pickaid.mnajs.kubejs.id.MnaShapeId;
import com.pickaid.mnajs.kubejs.id.MnaSpellEffectId;
import com.pickaid.mnajs.kubejs.pattern.MnaPatternHelper;
import com.pickaid.mnajs.kubejs.texture.MnaTexture;
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
import com.pickaid.mnajs.util.PlayerUtil;
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
    public static final Lazy<RegistryInfo<IFaction>> FACTION_REGISTRY =
            Lazy.of(() -> RegistryInfo.of(ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("mna", "factions")), IFaction.class));
    public static final Lazy<RegistryInfo<RitualEffect>> RITUAL_EFFECT_REGISTRY =
            Lazy.of(() -> RegistryInfo.of(ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("mna", "ritual-effects")), RitualEffect.class));
    public static final Lazy<RegistryInfo<SpellEffect>> SPELL_EFFECT =
            Lazy.of(() -> RegistryInfo.of(ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("mna", "components")), SpellEffect.class));
    public static final Lazy<RegistryInfo<Shape>> SPELL_SHAPE =
            Lazy.of(() -> RegistryInfo.of(ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath("mna", "shape")), Shape.class));

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

        RegistryInfo.ITEM.addType("mana_battery_item", CustomManaBatteryItem.Builder.class, CustomManaBatteryItem.Builder::new);
        RegistryInfo.ITEM.addType("tiered_item", CustomManaItem.Builder.class, CustomManaItem.Builder::new);

        RegistryInfo.BLOCK.addType("spell_interactible", CustomSpellInteractibleBlock.Builder.class, CustomSpellInteractibleBlock.Builder::new);
        RegistryInfo.BLOCK.addType("manaweave_notifiable", CustomManaweaveNotifiableBlock.Builder.class, CustomManaweaveNotifiableBlock.Builder::new);
    }

    @Override
    public void registerTypeWrappers(ScriptType type, TypeWrappers typeWrappers) {
        typeWrappers.registerSimple(MnaFactionId.class, MnaFactionId::parse);
        typeWrappers.registerSimple(MnaRitualEffectId.class, MnaRitualEffectId::parse);
        typeWrappers.registerSimple(MnaSpellEffectId.class, MnaSpellEffectId::parse);
        typeWrappers.registerSimple(MnaShapeId.class, MnaShapeId::parse);
        typeWrappers.registerSimple(MnaModifierId.class, MnaModifierId::parse);
        typeWrappers.registerSimple(MnaRitualId.class, MnaRitualId::parse);
        typeWrappers.registerSimple(MnaManaweavePatternId.class, MnaManaweavePatternId::parse);
        typeWrappers.registerSimple(MnaCantripId.class, MnaCantripId::parse);
        typeWrappers.registerSimple(MnaItemId.class, MnaItemId::parse);
        typeWrappers.registerSimple(MnaBlockId.class, MnaBlockId::parse);
        typeWrappers.registerSimple(MnaItemOrTag.class, MnaItemOrTag::parse);
        typeWrappers.registerSimple(MnaLootTableId.class, MnaLootTableId::parse);
        typeWrappers.registerSimple(MnaTexture.class, MnaTexture::parse);
        typeWrappers.registerSimple(IFaction.class, o -> {
            if (o instanceof IFaction faction) {
                return faction;
            }
            return Factions.INSTANCE.getFaction(MnaFactionId.parse(o).location());
        });
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("Affinity", Affinity.class);
        event.add("SpellAttribute", Attribute.class);
        event.add("CollectionUtils", CollectionUtils.class);
        event.add("MATags", MATags.class);
        event.add("MathUtils", MathUtils.class);
        event.add("BiomeUtils", BiomeUtils.class);
        event.add("ProjectileHelper", ProjectileHelper.class);
        event.add("PlayerMagic", PlayerUtil.class);
        event.add("WorldMagic", WorldMagic.class);
        event.add("InventoryUtilities", InventoryUtilities.class);
        event.add("RecipeUtil", RecipeUtil.class);
        event.add("MnaPatternHelper", MnaPatternHelper.class);

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
        event.register("mnaRitualEffectId", MnaRecipeComponents.RITUAL_EFFECT_ID);
        event.register("mnaSpellEffectId", MnaRecipeComponents.SPELL_EFFECT_ID);
        event.register("mnaShapeId", MnaRecipeComponents.SHAPE_ID);
        event.register("mnaModifierId", MnaRecipeComponents.MODIFIER_ID);
        event.register("mnaRitualId", MnaRecipeComponents.RITUAL_ID);
        event.register("mnaManaweavePatternId", MnaRecipeComponents.MANAWEAVE_PATTERN_ID);
        event.register("mnaCantripId", MnaRecipeComponents.CANTRIP_ID);
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

        mnaNamespace.put("progression", new RecipeSchemaType(mnaNamespace, ResourceLocation.fromNamespaceAndPath("mna", "progression-condition"), ProgressionSchema.SCHEMA));
        mnaNamespace.put("manaweavingAltar", new RecipeSchemaType(mnaNamespace, ResourceLocation.fromNamespaceAndPath("mna", "manaweaving-recipe"), ManaweavingAltarSchema.SCHEMA));
        mnaNamespace.put("arcaneFurnace", new RecipeSchemaType(mnaNamespace, ResourceLocation.fromNamespaceAndPath("mna", "arcane-furnace"), ArcaneFurnaceSchema.SCHEMA));
        mnaNamespace.put("eldrinAltar", new RecipeSchemaType(mnaNamespace, ResourceLocation.fromNamespaceAndPath("mna", "eldrin-altar"), EldrinAltarSchema.SCHEMA));
        mnaNamespace.put("eldrinFume", new RecipeSchemaType(mnaNamespace, ResourceLocation.fromNamespaceAndPath("mna", "eldrin-fume"), FumerFliterSchema.SCHEMA));
        mnaNamespace.put("pattern", new RecipeSchemaType(mnaNamespace, ResourceLocation.fromNamespaceAndPath("mna", "manaweaving-pattern"), ManaweavingPatternSchema.SCHEMA));
        mnaNamespace.put("cacheEffect", new RecipeSchemaType(mnaNamespace, ResourceLocation.fromNamespaceAndPath("mna", "manaweave-cache-effect"), ManaweaveCacheEffectSchema.SCHEMA));
    }
}
