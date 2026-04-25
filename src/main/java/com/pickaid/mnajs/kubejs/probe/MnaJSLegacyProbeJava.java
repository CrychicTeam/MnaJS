package com.pickaid.mnajs.kubejs.probe;

import com.mna.api.affinity.Affinity;
import com.mna.api.events.ProgressionEventIDs;
import com.mna.api.items.ItemUtils;
import com.mna.api.spells.attributes.Attribute;
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
import com.pickaid.mnajs.content.spell.CustomDamageComponent;
import com.pickaid.mnajs.content.spell.CustomPotionEffectComponent;
import com.pickaid.mnajs.content.spell.CustomShape;
import com.pickaid.mnajs.content.spell.CustomSpellEffect;
import com.pickaid.mnajs.kubejs.events.startup.CantripRegistrationEventJS;
import com.pickaid.mnajs.kubejs.events.startup.GuideBookRegisterEventJS;
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
import com.pickaid.mnajs.kubejs.recipe.ArcaneFurnaceRecipeJS;
import com.pickaid.mnajs.kubejs.recipe.ComponentRecipeJS;
import com.pickaid.mnajs.kubejs.recipe.CrushingRecipeJS;
import com.pickaid.mnajs.kubejs.recipe.EldrinAltarRecipeJS;
import com.pickaid.mnajs.kubejs.recipe.FumeFilterRecipeJS;
import com.pickaid.mnajs.kubejs.recipe.ManaweaveCacheEffectRecipeJS;
import com.pickaid.mnajs.kubejs.recipe.ManaweavingAltarRecipeJS;
import com.pickaid.mnajs.kubejs.recipe.ManaweavingPatternRecipeJS;
import com.pickaid.mnajs.kubejs.recipe.MnaBaseRecipeJS;
import com.pickaid.mnajs.kubejs.recipe.MnaItemsPatternRecipeJS;
import com.pickaid.mnajs.kubejs.recipe.ModifierRecipeJS;
import com.pickaid.mnajs.kubejs.recipe.ProgressionRecipeJS;
import com.pickaid.mnajs.kubejs.recipe.RitualRecipeJS;
import com.pickaid.mnajs.kubejs.recipe.RuneForgingRecipeJS;
import com.pickaid.mnajs.kubejs.recipe.RuneScribingRecipeJS;
import com.pickaid.mnajs.kubejs.recipe.ShapeRecipeJS;
import com.pickaid.mnajs.kubejs.recipe.TransmutationRecipeJS;
import com.pickaid.mnajs.kubejs.texture.MnaTexture;
import com.pickaid.mnajs.recipes.RecipesHelper;
import com.pickaid.mnajs.recipes.builders.ArcaneFurnaceBuilder;
import com.pickaid.mnajs.recipes.builders.ComponentBuilder;
import com.pickaid.mnajs.recipes.builders.CrushingBuilder;
import com.pickaid.mnajs.recipes.builders.EldrinAltarBuilder;
import com.pickaid.mnajs.recipes.builders.FumeFilterBuilder;
import com.pickaid.mnajs.recipes.builders.ManaweaveCacheEffectBuilder;
import com.pickaid.mnajs.recipes.builders.ManaweavingAltarBuilder;
import com.pickaid.mnajs.recipes.builders.ManaweavingPatternBuilder;
import com.pickaid.mnajs.recipes.builders.ModifierBuilder;
import com.pickaid.mnajs.recipes.builders.MultiblockDefinitionBuilder;
import com.pickaid.mnajs.recipes.builders.ProgressionRecipeBuilder;
import com.pickaid.mnajs.recipes.builders.RitualRecipeBuilder;
import com.pickaid.mnajs.recipes.builders.RuneForgingBuilder;
import com.pickaid.mnajs.recipes.builders.RuneScribingBuilder;
import com.pickaid.mnajs.recipes.builders.ShapeBuilder;
import com.pickaid.mnajs.recipes.builders.TransmutationBuilder;
import com.pickaid.mnajs.util.PlayerUtil;
import com.pickaid.mnajs.util.WorldMagic;

import java.util.Set;

final class MnaJSLegacyProbeJava {
    private static final Set<Class<?>> PROVIDED_CLASSES = Set.of(
            RecipesHelper.class,
            RitualRecipeJS.class,
            CrushingRecipeJS.class,
            EldrinAltarRecipeJS.class,
            FumeFilterRecipeJS.class,
            ManaweavingAltarRecipeJS.class,
            RuneForgingRecipeJS.class,
            RuneScribingRecipeJS.class,
            ArcaneFurnaceRecipeJS.class,
            TransmutationRecipeJS.class,
            MnaBaseRecipeJS.class,
            MnaItemsPatternRecipeJS.class,
            ComponentRecipeJS.class,
            ModifierRecipeJS.class,
            ShapeRecipeJS.class,
            ProgressionRecipeJS.class,
            ManaweavingPatternRecipeJS.class,
            ManaweaveCacheEffectRecipeJS.class,
            RitualRecipeBuilder.class,
            ProgressionRecipeBuilder.class,
            CrushingBuilder.class,
            EldrinAltarBuilder.class,
            FumeFilterBuilder.class,
            ManaweavingAltarBuilder.class,
            RuneForgingBuilder.class,
            RuneScribingBuilder.class,
            ArcaneFurnaceBuilder.class,
            TransmutationBuilder.class,
            ComponentBuilder.class,
            ModifierBuilder.class,
            ShapeBuilder.class,
            ManaweavingPatternBuilder.class,
            ManaweaveCacheEffectBuilder.class,
            MultiblockDefinitionBuilder.class,
            CantripRegistrationEventJS.class,
            GuideBookRegisterEventJS.class,
            MnaPatternHelper.class,
            MnaFactionId.class,
            MnaRitualEffectId.class,
            MnaSpellEffectId.class,
            MnaShapeId.class,
            MnaModifierId.class,
            MnaRitualId.class,
            MnaManaweavePatternId.class,
            MnaCantripId.class,
            MnaItemId.class,
            MnaBlockId.class,
            MnaItemOrTag.class,
            MnaLootTableId.class,
            MnaTexture.class,
            CustomFaction.Builder.class,
            CustomRitualEffect.Builder.class,
            CustomSpellEffect.Builder.class,
            CustomDamageComponent.Builder.class,
            CustomPotionEffectComponent.Builder.class,
            CustomShape.Builder.class,
            Affinity.class,
            Attribute.class,
            CollectionUtils.class,
            MATags.class,
            MathUtils.class,
            BiomeUtils.class,
            ProjectileHelper.class,
            InventoryUtilities.class,
            RecipeUtil.class,
            StructureUtils.class,
            EntityUtil.class,
            SummonUtils.class,
            ShearHelper.class,
            Factions.class,
            ItemUtils.class,
            EntityHelper.class,
            FactionRaidHelper.class,
            ProgressionEventIDs.class,
            WorldRenderUtils.class,
            GuiRenderUtils.class,
            ParticleConfigurations.class,
            PlayerUtil.class,
            WorldMagic.class
    );

    private MnaJSLegacyProbeJava() {
    }

    static Set<Class<?>> providedClasses() {
        return PROVIDED_CLASSES;
    }
}
