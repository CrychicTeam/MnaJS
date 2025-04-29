package com.pickaid.mnajs.recipes;

import com.pickaid.mnajs.recipes.builders.*;
import dev.latvian.mods.kubejs.typings.Info;

public class RecipesHelper {
    @Info("The Helper for creating rituals using event.custom().")
    public static RitualRecipeBuilder ritual = new RitualRecipeBuilder();
    @Info("The Helper for creating progression recipes using event.custom().")
    public static ProgressionRecipeBuilder progression = new ProgressionRecipeBuilder();
    @Info("The Helper for creating crushing recipes using event.custom().")
    public static CrushingBuilder crushing = new CrushingBuilder();
    @Info("The Helper for creating eldrin Altar Builder recipes using event.custom().")
    public static EldrinAltarBuilder eldrinAltarBuilder = new EldrinAltarBuilder();
    @Info("The Helper for creating fume Filter Builder recipes using event.custom().")
    public static FumeFilterBuilder fumeFilterBuilder = new FumeFilterBuilder();
    @Info("The Helper for creating manaweaving Altar Builder recipes using event.custom().")
    public static ManaweavingAltarBuilder manaweavingAltarBuilder = new ManaweavingAltarBuilder();
    @Info("The Helper for creating rune Forging Builder recipes using event.custom().")
    public static RuneForgingBuilder runeForgingBuilder = new RuneForgingBuilder();
    @Info("The Helper for creating rune Scribing Builder recipes using event.custom().")
    public static RuneScribingBuilder runeScribingBuilder = new RuneScribingBuilder();
    @Info("The Helper for creating arcane Furnace Builder recipes using event.custom().")
    public static ArcaneFurnaceBuilder arcaneFurnaceBuilder = new ArcaneFurnaceBuilder();
    @Info("The Helper for creating transmutation Builder recipes using event.custom().")
    public static TransmutationBuilder transmutationBuilder = new TransmutationBuilder();
    @Info("The Helper for creating component Builder recipes using event.custom().")
    public static ComponentBuilder componentBuilder = new ComponentBuilder();
    @Info("The Helper for creating modifier Builder recipes using event.custom().")
    public static ModifierBuilder modifierBuilder = new ModifierBuilder();
    @Info("The Helper for creating shape Builder recipes using event.custom().")
    public static ShapeBuilder shapeBuilder = new ShapeBuilder();
    @Info("The Helper for creating manaweaving Pattern Builder recipes using event.custom().")
    public static ManaweavingPatternBuilder manaweavingPatternBuilder = new ManaweavingPatternBuilder();
    @Info("The Helper for creating multiblock Definition Builder recipes using event.custom().")
    public static MultiblockDefinitionBuilder multiblockDefinitionBuilder = new MultiblockDefinitionBuilder();
}
