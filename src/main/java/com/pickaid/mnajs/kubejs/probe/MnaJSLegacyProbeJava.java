package com.pickaid.mnajs.kubejs.probe;

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
import com.mna.api.items.ItemUtils;
import com.mna.api.spells.ComponentApplicationResult;
import com.mna.api.spells.attributes.Attribute;
import com.mna.api.spells.parts.Modifier;
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
import com.pickaid.mnajs.content.spell.CustomDamageComponent;
import com.pickaid.mnajs.content.spell.CustomModifier;
import com.pickaid.mnajs.content.spell.CustomPotionEffectComponent;
import com.pickaid.mnajs.content.spell.CustomShape;
import com.pickaid.mnajs.content.spell.CustomSpellEffect;
import com.pickaid.mnajs.kubejs.events.startup.CantripRegistrationEventJS;
import com.pickaid.mnajs.kubejs.events.startup.GuideBookRegisterEventJS;
import com.pickaid.mnajs.kubejs.id.MnaAdvancementId;
import com.pickaid.mnajs.kubejs.id.MnaCastingResourceId;
import com.pickaid.mnajs.kubejs.id.MnaCantripId;
import com.pickaid.mnajs.kubejs.id.MnaBlockId;
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
import com.pickaid.mnajs.kubejs.pattern.MnaPatternHelper;
import com.pickaid.mnajs.kubejs.recipe.MnaRitualReagent;
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
import com.pickaid.mnajs.util.CastingResourceState;
import com.pickaid.mnajs.util.PlayerMagicState;
import com.pickaid.mnajs.util.PlayerProgressionHelper;
import com.pickaid.mnajs.util.PlayerProgressionState;
import com.pickaid.mnajs.util.PlayerUtil;
import com.pickaid.mnajs.util.ProgressionEvents;
import com.pickaid.mnajs.util.WorldMagic;
import com.pickaid.mnajs.util.WorldMagicState;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;

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
            MnaRitualReagent.class,
            MnaAdvancementId.class,
            MnaProgressionEventId.class,
            MnaFactionId.class,
            MnaCastingResourceId.class,
            MnaConstructMaterialId.class,
            MnaConstructSlotId.class,
            MnaConstructCapabilityId.class,
            MnaMobEffectId.class,
            MnaRitualEffectId.class,
            MnaSpellEffectId.class,
            MnaShapeId.class,
            MnaModifierId.class,
            MnaConstructTaskId.class,
            MnaRitualId.class,
            MnaManaweavePatternId.class,
            MnaCantripId.class,
            MnaItemId.class,
            MnaBlockId.class,
            MnaItemOrTag.class,
            MnaLootTableId.class,
            MnaSoundId.class,
            MnaStructureId.class,
            MnaTexture.class,
            CustomFaction.Builder.class,
            CustomRitualEffect.Builder.class,
            CustomSpellEffect.Builder.class,
            CustomDamageComponent.Builder.class,
            CustomPotionEffectComponent.Builder.class,
            CustomShape.Builder.class,
            CustomModifier.Builder.class,
            CustomConstructMaterial.Builder.class,
            CustomConstructPartItem.Builder.class,
            CustomConstructTask.Builder.class,
            Affinity.class,
            ComponentApplicationResult.class,
            Attribute.class,
            Modifier.class,
            SpellTarget.class,
            ConstructCapability.class,
            ConstructSlot.class,
            ConstructMaterial.class,
            ItemConstructPart.class,
            ConstructTask.class,
            ConstructAITask.class,
            ConstructAITaskParameter.class,
            ConstructParameterTypes.class,
            ConstructTaskBooleanParameter.class,
            ConstructTaskIntegerParameter.class,
            ConstructTaskItemStackParameter.class,
            ConstructTaskPointParameter.class,
            ConstructTaskAreaParameter.class,
            ConstructTaskFilterParameter.class,
            ConstructMutexConstants.Arms.class,
            ConstructMutexConstants.Head.class,
            ConstructMutexConstants.Legs.class,
            ConstructMutexConstants.Torso.class,
            Tier.class,
            Tiers.class,
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
            ConstructTasks.class,
            ProgressionEventIDs.class,
            WorldRenderUtils.class,
            GuiRenderUtils.class,
            ParticleConfigurations.class,
            CastingResourceState.class,
            PlayerMagicState.class,
            PlayerProgressionHelper.class,
            PlayerProgressionState.class,
            PlayerUtil.class,
            ProgressionEvents.class,
            WorldMagic.class,
            WorldMagicState.class
    );

    private MnaJSLegacyProbeJava() {
    }

    static Set<Class<?>> providedClasses() {
        return PROVIDED_CLASSES;
    }
}
