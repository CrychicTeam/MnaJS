package com.pickaid.mnajs.kubejs.probe;

import zzzank.probejs.ProbeJS;
import zzzank.probejs.lang.snippet.Snippet;
import zzzank.probejs.lang.snippet.SnippetDump;

import java.util.List;
import java.util.Locale;

final class MnaJSLegacyProbeSnippets {
    private static final String DEFAULT_ITEM_ID = "minecraft:iron_ingot";
    private static final String DEFAULT_ITEM_TAG = "#forge:ingots/iron";
    private static final String DEFAULT_OUTPUT_ID = "mna:example_output";
    private static final String DEFAULT_BLOCK_ID = "minecraft:stone";
    private static final String DEFAULT_PATTERN_ID = "mna:example_pattern";
    private static final String DEFAULT_LOOT_TABLE_ID = "minecraft:blocks/stone";
    private static final String DEFAULT_ADVANCEMENT_ID = "mna:tier_0/vinteum_bar";
    private static final String DEFAULT_MOB_EFFECT_ID = "minecraft:speed";
    private static final String DEFAULT_TEXTURE_ID = "mna:textures/gui/example";

    private MnaJSLegacyProbeSnippets() {
    }

    static void addSnippets(SnippetDump dump) {
        addIdChoiceSnippets(dump);
        addRecipeChainSnippets(dump);
        addRitualChainSnippet(dump);
        addRitualRowSnippets(dump);
        addDisplayPatternRowSnippets(dump);
        addReagentRowSnippets(dump);
        addRitualReagentSnippets(dump);
        addManaweaveGridSnippets(dump);
        addTextureSnippets(dump);
    }

    private static void addIdChoiceSnippets(SnippetDump dump) {
        for (MnaJSLegacyProbeIdAliases.IdAlias alias : MnaJSLegacyProbeIdAliases.all()) {
            if (alias.specialTypeReference() != null && !alias.specialTypeReference().isBlank()) {
                continue;
            }

            String prefix = "@" + toSnakeCase(alias.alias());
            Snippet snippet = dump.snippet(alias.alias())
                    .prefix(prefix)
                    .description("MnaJS choices for " + alias.alias());
            List<String> choices = jsonChoices(alias.ids().get());
            if (choices.isEmpty()) {
                snippet.tabStop(1, ProbeJS.GSON.toJson("mna:path"));
            } else {
                snippet.choices(choices);
            }
        }
    }

    private static void addRecipeChainSnippets(SnippetDump dump) {
        addCrushingChainSnippet(dump);
        addArcaneFurnaceChainSnippet(dump);
        addRunescribingChainSnippet(dump);
        addRuneforgingChainSnippet(dump);
        addShapeChainSnippet(dump);
        addComponentChainSnippet(dump);
        addModifierChainSnippet(dump);
        addManaweavingAltarChainSnippet(dump);
        addProgressionChainSnippet(dump);
        addTransmutationReplaceChainSnippet(dump);
        addTransmutationLootChainSnippet(dump);
        addFumeFilterChainSnippet(dump);
        addEldrinAltarChainSnippet(dump);
        addCacheEffectChainSnippet(dump);
        addManaweavingPatternRecipeChainSnippet(dump);
    }

    private static void addRitualChainSnippet(SnippetDump dump) {
        Snippet snippet = dump.snippet("MnaRitualChain")
                .prefix("@mna_ritual_chain")
                .description("MnaJS ritual chain DSL scaffold");
        snippet.literal("event.recipes.mna.ritual()")
                .newline().literal("    .tier(").tabStop(1, "1").literal(")")
                .newline().literal("    .patternRows(");
        appendStringRowBlock(snippet, 3, 3, false, 2, "        ");
        snippet.newline().literal("    )")
                .newline().literal("    .displayPatternRows(");
        appendStringRowBlock(snippet, 3, 3, false, 5, "        ");
        snippet.newline().literal("    )")
                .newline().literal("    .reagentRows(");
        appendRitualExampleReagentRows(snippet, 8, "        ");
        snippet.newline().literal("    )")
                .newline().literal("    .reagent(new MnaRitualReagent(").tabStop(11, ProbeJS.GSON.toJson("A")).literal(", ").tabStop(12, ProbeJS.GSON.toJson(DEFAULT_ITEM_ID)).literal("))")
                .newline().literal("    .reagent(new MnaRitualReagent(").tabStop(13, ProbeJS.GSON.toJson("B")).literal(", ").tabStop(14, ProbeJS.GSON.toJson(DEFAULT_ITEM_TAG)).literal(").optional().keep())")
                .newline().literal("    .outputItem(").tabStop(15, ProbeJS.GSON.toJson(DEFAULT_OUTPUT_ID)).literal(")")
                .tabStop(0);
    }

    private static void addCrushingChainSnippet(SnippetDump dump) {
        Snippet snippet = dump.snippet("MnaCrushingChain")
                .prefix("@mna_crushing_chain")
                .description("MnaJS crushing chain DSL scaffold");
        snippet.literal("event.recipes.mna.crushing()")
                .newline().literal("    .input(").tabStop(1, ProbeJS.GSON.toJson(DEFAULT_ITEM_ID)).literal(")")
                .newline().literal("    .output(").tabStop(2, ProbeJS.GSON.toJson(DEFAULT_OUTPUT_ID)).literal(")")
                .newline().literal("    .outputQuantity(").tabStop(3, "1").literal(")");
        appendTierAndFaction(snippet, 4, 5);
        snippet.tabStop(0);
    }

    private static void addArcaneFurnaceChainSnippet(SnippetDump dump) {
        Snippet snippet = dump.snippet("MnaArcaneFurnaceChain")
                .prefix("@mna_arcane_furnace_chain")
                .description("MnaJS arcane furnace chain DSL scaffold");
        snippet.literal("event.recipes.mna.arcaneFurnace()")
                .newline().literal("    .input(").tabStop(1, ProbeJS.GSON.toJson(DEFAULT_ITEM_ID)).literal(")")
                .newline().literal("    .output(").tabStop(2, ProbeJS.GSON.toJson(DEFAULT_OUTPUT_ID)).literal(")")
                .newline().literal("    .burnTime(").tabStop(3, "200").literal(")")
                .newline().literal("    .outputQuantity(").tabStop(4, "1").literal(")");
        appendTierAndFaction(snippet, 5, 6);
        snippet.tabStop(0);
    }

    private static void addRunescribingChainSnippet(SnippetDump dump) {
        Snippet snippet = dump.snippet("MnaRunescribingChain")
                .prefix("@mna_runescribing_chain")
                .description("MnaJS runescribing chain DSL scaffold");
        snippet.literal("event.recipes.mna.runescribing()")
                .newline().literal("    .output(").tabStop(1, ProbeJS.GSON.toJson(DEFAULT_OUTPUT_ID)).literal(")")
                .newline().literal("    .hMutex(").tabStop(2, "0").literal(")")
                .newline().literal("    .vMutex(").tabStop(3, "0").literal(")");
        appendTierAndFaction(snippet, 4, 5);
        snippet.tabStop(0);
    }

    private static void addRuneforgingChainSnippet(SnippetDump dump) {
        Snippet snippet = dump.snippet("MnaRuneforgingChain")
                .prefix("@mna_runeforging_chain")
                .description("MnaJS runeforging chain DSL scaffold");
        snippet.literal("event.recipes.mna.runeforging()")
                .newline().literal("    .pattern(").tabStop(1, ProbeJS.GSON.toJson(DEFAULT_ITEM_ID)).literal(")")
                .newline().literal("    .output(").tabStop(2, ProbeJS.GSON.toJson(DEFAULT_OUTPUT_ID)).literal(")")
                .newline().literal("    .material(").tabStop(3, ProbeJS.GSON.toJson(DEFAULT_ITEM_ID)).literal(")")
                .newline().literal("    .hits(").tabStop(4, "10").literal(")")
                .newline().literal("    .outputQuantity(").tabStop(5, "1").literal(")");
        appendTierAndFaction(snippet, 6, 7);
        snippet.tabStop(0);
    }

    private static void addShapeChainSnippet(SnippetDump dump) {
        Snippet snippet = dump.snippet("MnaShapeChain")
                .prefix("@mna_shape_chain")
                .description("MnaJS shape recipe chain DSL scaffold");
        snippet.literal("event.recipes.mna.shape()")
                .newline().literal("    .output(");
        appendIdChoiceOrPlaceholder(snippet, "MnaShapeId", 1, "mna:self");
        snippet.literal(")")
                .newline().literal("    .inputs(")
                .tabStop(2, ProbeJS.GSON.toJson(DEFAULT_ITEM_ID))
                .literal(", ")
                .tabStop(3, ProbeJS.GSON.toJson(DEFAULT_ITEM_TAG))
                .literal(")")
                .newline().literal("    .patterns(");
        appendIdChoiceOrPlaceholder(snippet, "MnaManaweavePatternId", 4, DEFAULT_PATTERN_ID);
        snippet.literal(")")
                .newline().literal("    .outputQuantity(").tabStop(5, "1").literal(")");
        appendTierAndFaction(snippet, 6, 7);
        snippet.tabStop(0);
    }

    private static void addComponentChainSnippet(SnippetDump dump) {
        Snippet snippet = dump.snippet("MnaComponentChain")
                .prefix("@mna_component_chain")
                .description("MnaJS component recipe chain DSL scaffold");
        snippet.literal("event.recipes.mna.component()")
                .newline().literal("    .output(");
        appendIdChoiceOrPlaceholder(snippet, "MnaSpellEffectId", 1, "mna:example_component");
        snippet.literal(")")
                .newline().literal("    .inputs(")
                .tabStop(2, ProbeJS.GSON.toJson(DEFAULT_ITEM_ID))
                .literal(", ")
                .tabStop(3, ProbeJS.GSON.toJson(DEFAULT_ITEM_TAG))
                .literal(")")
                .newline().literal("    .patterns(");
        appendIdChoiceOrPlaceholder(snippet, "MnaManaweavePatternId", 4, DEFAULT_PATTERN_ID);
        snippet.literal(")")
                .newline().literal("    .outputQuantity(").tabStop(5, "1").literal(")");
        appendTierAndFaction(snippet, 6, 7);
        snippet.tabStop(0);
    }

    private static void addModifierChainSnippet(SnippetDump dump) {
        Snippet snippet = dump.snippet("MnaModifierChain")
                .prefix("@mna_modifier_chain")
                .description("MnaJS modifier recipe chain DSL scaffold");
        snippet.literal("event.recipes.mna.modifier()")
                .newline().literal("    .output(");
        appendIdChoiceOrPlaceholder(snippet, "MnaModifierId", 1, "mna:example_modifier");
        snippet.literal(")")
                .newline().literal("    .inputs(")
                .tabStop(2, ProbeJS.GSON.toJson(DEFAULT_ITEM_ID))
                .literal(", ")
                .tabStop(3, ProbeJS.GSON.toJson(DEFAULT_ITEM_TAG))
                .literal(")")
                .newline().literal("    .patterns(");
        appendIdChoiceOrPlaceholder(snippet, "MnaManaweavePatternId", 4, DEFAULT_PATTERN_ID);
        snippet.literal(")")
                .newline().literal("    .outputQuantity(").tabStop(5, "1").literal(")");
        appendTierAndFaction(snippet, 6, 7);
        snippet.tabStop(0);
    }

    private static void addManaweavingAltarChainSnippet(SnippetDump dump) {
        Snippet snippet = dump.snippet("MnaManaweavingAltarChain")
                .prefix("@mna_manaweaving_altar_chain")
                .description("MnaJS manaweaving altar chain DSL scaffold");
        snippet.literal("event.recipes.mna.manaweavingAltar()")
                .newline().literal("    .output(").tabStop(1, ProbeJS.GSON.toJson(DEFAULT_OUTPUT_ID)).literal(")")
                .newline().literal("    .inputs(")
                .tabStop(2, ProbeJS.GSON.toJson(DEFAULT_ITEM_ID))
                .literal(", ")
                .tabStop(3, ProbeJS.GSON.toJson(DEFAULT_ITEM_TAG))
                .literal(")")
                .newline().literal("    .patterns(");
        appendIdChoiceOrPlaceholder(snippet, "MnaManaweavePatternId", 4, DEFAULT_PATTERN_ID);
        snippet.literal(")")
                .newline().literal("    .enchant(").tabStop(5, ProbeJS.GSON.toJson("minecraft:sharpness")).literal(")")
                .newline().literal("    .magnitude(").tabStop(6, "1").literal(")")
                .newline().literal("    .copyNbt(").tabStop(7, "false").literal(")")
                .newline().literal("    .outputQuantity(").tabStop(8, "1").literal(")");
        appendTierAndFaction(snippet, 9, 10);
        snippet.tabStop(0);
    }

    private static void addProgressionChainSnippet(SnippetDump dump) {
        Snippet snippet = dump.snippet("MnaProgressionChain")
                .prefix("@mna_progression_chain")
                .description("MnaJS progression chain DSL scaffold");
        snippet.literal("event.recipes.mna.progression()")
                .newline().literal("    .advancement(");
        appendIdChoiceOrPlaceholder(snippet, "MnaAdvancementId", 1, DEFAULT_ADVANCEMENT_ID);
        snippet.literal(")")
                .newline().literal("    .description(").tabStop(2, ProbeJS.GSON.toJson("mna.progression.example")).literal(")");
        appendTierAndFaction(snippet, 3, 4);
        snippet.tabStop(0);
    }

    private static void addTransmutationReplaceChainSnippet(SnippetDump dump) {
        Snippet snippet = dump.snippet("MnaTransmutationReplaceChain")
                .prefix("@mna_transmutation_replace_chain")
                .description("MnaJS transmutation replace-block chain DSL scaffold");
        snippet.literal("event.recipes.mna.transmutation()")
                .newline().literal("    .targetBlock(").tabStop(1, ProbeJS.GSON.toJson(DEFAULT_BLOCK_ID)).literal(")")
                .newline().literal("    .replaceBlock(").tabStop(2, ProbeJS.GSON.toJson("minecraft:diamond_block")).literal(")");
        appendTierAndFaction(snippet, 3, 4);
        snippet.tabStop(0);
    }

    private static void addTransmutationLootChainSnippet(SnippetDump dump) {
        Snippet snippet = dump.snippet("MnaTransmutationLootChain")
                .prefix("@mna_transmutation_loot_chain")
                .description("MnaJS transmutation loot-table chain DSL scaffold");
        snippet.literal("event.recipes.mna.transmutation()")
                .newline().literal("    .targetBlock(").tabStop(1, ProbeJS.GSON.toJson(DEFAULT_BLOCK_ID)).literal(")")
                .newline().literal("    .lootTable(").tabStop(2, ProbeJS.GSON.toJson(DEFAULT_LOOT_TABLE_ID)).literal(")")
                .newline().literal("    .representationItem(").tabStop(3, ProbeJS.GSON.toJson(DEFAULT_ITEM_ID)).literal(")");
        appendTierAndFaction(snippet, 4, 5);
        snippet.tabStop(0);
    }

    private static void addFumeFilterChainSnippet(SnippetDump dump) {
        Snippet snippet = dump.snippet("MnaFumeFilterChain")
                .prefix("@mna_fume_filter_chain")
                .description("MnaJS eldrin fume chain DSL scaffold");
        snippet.literal("event.recipes.mna.eldrinFume()")
                .newline().literal("    .item(").tabStop(1, ProbeJS.GSON.toJson(DEFAULT_ITEM_TAG)).literal(")")
                .newline().literal("    .powerProvided(").tabStop(2, "Affinity.ARCANE").literal(", ").tabStop(3, "5").literal(")");
        appendTierAndFaction(snippet, 4, 5);
        snippet.tabStop(0);
    }

    private static void addEldrinAltarChainSnippet(SnippetDump dump) {
        Snippet snippet = dump.snippet("MnaEldrinAltarChain")
                .prefix("@mna_eldrin_altar_chain")
                .description("MnaJS eldrin altar chain DSL scaffold");
        snippet.literal("event.recipes.mna.eldrinAltar()")
                .newline().literal("    .output(").tabStop(1, ProbeJS.GSON.toJson(DEFAULT_OUTPUT_ID)).literal(")")
                .newline().literal("    .inputs(")
                .tabStop(2, ProbeJS.GSON.toJson(DEFAULT_ITEM_ID))
                .literal(", ")
                .tabStop(3, ProbeJS.GSON.toJson(DEFAULT_ITEM_TAG))
                .literal(")")
                .newline().literal("    .powerRequirement(").tabStop(4, "Affinity.ARCANE").literal(", ").tabStop(5, "5").literal(")")
                .newline().literal("    .colors(").tabStop(6, "0x4F8AC9").literal(", ").tabStop(7, "0xE3C15E").literal(")")
                .newline().literal("    .outputQuantity(").tabStop(8, "1").literal(")");
        appendTierAndFaction(snippet, 9, 10);
        snippet.tabStop(0);
    }

    private static void addCacheEffectChainSnippet(SnippetDump dump) {
        Snippet snippet = dump.snippet("MnaCacheEffectChain")
                .prefix("@mna_cache_effect_chain")
                .description("MnaJS manaweave cache effect chain DSL scaffold");
        snippet.literal("event.recipes.mna.cacheEffect()")
                .newline().literal("    .effect(").tabStop(1, ProbeJS.GSON.toJson(DEFAULT_MOB_EFFECT_ID)).literal(")")
                .newline().literal("    .duration(").tabStop(2, "200").literal(", ").tabStop(3, "400").literal(")")
                .newline().literal("    .magnitude(").tabStop(4, "1").literal(")");
        appendTierAndFaction(snippet, 5, 6);
        snippet.tabStop(0);
    }

    private static void addManaweavingPatternRecipeChainSnippet(SnippetDump dump) {
        Snippet snippet = dump.snippet("MnaManaweavingPatternChain")
                .prefix("@mna_manaweaving_pattern_chain")
                .prefix("@mna_pattern_chain")
                .description("MnaJS manaweaving-pattern chain DSL scaffold");
        snippet.literal("event.recipes.mna.pattern()")
                .newline().literal("    .pattern([");
        appendIntGridBlock(snippet, 11, 11, 1, "        ");
        snippet.newline().literal("    ])");
        appendTierAndFaction(snippet, 12, 13);
        snippet.tabStop(0);
    }

    private static void addRitualRowSnippets(SnippetDump dump) {
        for (int size : List.of(3, 5, 7, 9, 11)) {
            Snippet snippet = dump.snippet("MnaRitualRows" + size)
                    .prefix("@mna_pattern_rows_" + size)
                    .prefix("@mna_ritual_rows_" + size)
                    .description("MnaJS ritual patternRows block for " + size + "x" + size);
            snippet.literal(".patternRows(");
            appendStringRowBlock(snippet, size, size, false, 1, "    ");
            snippet.newline().literal(")")
                    .tabStop(0);
        }
    }

    private static void addDisplayPatternRowSnippets(SnippetDump dump) {
        for (int size : List.of(3, 5, 7, 9, 11)) {
            Snippet snippet = dump.snippet("MnaDisplayPatternRows" + size)
                    .prefix("@mna_display_pattern_rows_" + size)
                    .description("MnaJS ritual displayPatternRows block for " + size + "x" + size);
            snippet.literal(".displayPatternRows(");
            appendStringRowBlock(snippet, size, size, false, 1, "    ");
            snippet.newline().literal(")")
                    .tabStop(0);
        }
    }

    private static void addReagentRowSnippets(SnippetDump dump) {
        for (int size : List.of(3, 5, 7, 9, 11)) {
            Snippet snippet = dump.snippet("MnaReagentRows" + size)
                    .prefix("@mna_reagent_rows_" + size)
                    .description("MnaJS ritual reagentRows block for " + size + "x" + size);
            snippet.literal(".reagentRows(");
            appendStringRowBlock(snippet, size, size, true, 1, "    ");
            snippet.newline().literal(")")
                    .tabStop(0);
        }
    }

    private static void addRitualReagentSnippets(SnippetDump dump) {
        addConfiguredReagentSnippet(
                dump,
                "MnaRitualReagent",
                "@mna_ritual_reagent",
                "MnaJS ritual reagent declaration",
                ".reagent(new MnaRitualReagent(",
                DEFAULT_ITEM_ID,
                "))"
        );
        addConfiguredReagentSnippet(
                dump,
                "MnaRitualDynamicReagent",
                "@mna_ritual_dynamic_reagent",
                "MnaJS ritual dynamic reagent declaration",
                ".reagent(new MnaRitualReagent(",
                DEFAULT_ITEM_ID,
                ").dynamic())"
        );
        addConfiguredReagentSnippet(
                dump,
                "MnaRitualDynamicSourceReagent",
                "@mna_ritual_dynamic_source_reagent",
                "MnaJS ritual dynamic source reagent declaration",
                ".reagent(new MnaRitualReagent(",
                DEFAULT_ITEM_ID,
                ").dynamicSource())"
        );
        addConfiguredReagentSnippet(
                dump,
                "MnaRitualManualReturnReagent",
                "@mna_ritual_manual_return_reagent",
                "MnaJS ritual manual-return reagent declaration",
                ".reagent(new MnaRitualReagent(",
                DEFAULT_ITEM_TAG,
                ").manualReturn())"
        );
    }

    private static void addManaweaveGridSnippets(SnippetDump dump) {
        Snippet patternSnippet = dump.snippet("MnaManaweavePatternBlock")
                .prefix("@mna_manaweave_rows")
                .prefix("@mna_manaweave_pattern")
                .description("MnaJS manaweave pattern(int[][]) block scaffold");
        patternSnippet.literal(".pattern([");
        appendIntGridBlock(patternSnippet, 11, 11, 1, "    ");
        patternSnippet.newline().literal("])")
                .tabStop(0);

        Snippet patternsSnippet = dump.snippet("MnaManaweavePatternsCall")
                .prefix("@mna_manaweave_patterns")
                .description("MnaJS manaweave pattern id chain call");
        patternsSnippet.literal(".manaweavePatterns(");
        appendIdChoiceOrPlaceholder(patternsSnippet, "MnaManaweavePatternId", 1, "mna:example_pattern");
        patternsSnippet.literal(")")
                .tabStop(0);

        Snippet addPatternSnippet = dump.snippet("MnaAddManaweavePatternCall")
                .prefix("@mna_add_pattern")
                .prefix("@mna_add_manaweave_pattern")
                .description("MnaJS addPattern(...) chain call for pattern-consuming recipes");
        addPatternSnippet.literal(".addPattern(");
        appendIdChoiceOrPlaceholder(addPatternSnippet, "MnaManaweavePatternId", 1, DEFAULT_PATTERN_ID);
        addPatternSnippet.literal(")")
                .tabStop(0);
    }

    private static void addTextureSnippets(SnippetDump dump) {
        Snippet literalSnippet = dump.snippet("MnaTextureLiteral")
                .prefix("@mna_texture_literal")
                .description("MnaJS texture literal scaffold");
        appendIdChoiceOrPlaceholder(literalSnippet, "MnaTexture", 1, DEFAULT_TEXTURE_ID);
        literalSnippet
                .tabStop(0);

        Snippet iconSnippet = dump.snippet("MnaTextureIconCall")
                .prefix("@mna_texture_icon")
                .description("MnaJS cantrip icon texture call");
        iconSnippet.literal(".icon(")
                ;
        appendIdChoiceOrPlaceholder(iconSnippet, "MnaTexture", 1, DEFAULT_TEXTURE_ID);
        iconSnippet
                .literal(")")
                .tabStop(0);
    }

    private static void addConfiguredReagentSnippet(SnippetDump dump, String name, String prefix, String description, String methodPrefix, String defaultItem, String suffix) {
        Snippet snippet = dump.snippet(name)
                .prefix(prefix)
                .description(description);
        snippet.literal(methodPrefix)
                .tabStop(1, ProbeJS.GSON.toJson("A"))
                .literal(", ")
                .tabStop(2, ProbeJS.GSON.toJson(defaultItem))
                .literal(suffix)
                .tabStop(0);
    }

    private static void appendTierAndFaction(Snippet snippet, int tierIndex, int factionIndex) {
        snippet.newline().literal("    .tier(").tabStop(tierIndex, "1").literal(")")
                .newline().literal("    .faction(");
        appendIdChoiceOrPlaceholder(snippet, "MnaFactionId", factionIndex, "mna:none");
        snippet.literal(")");
    }

    private static void appendStringRowBlock(Snippet snippet, int rows, int columns, boolean reagentRows, int startIndex, String indent) {
        for (int row = 0; row < rows; row++) {
            snippet.newline().literal(indent).tabStop(startIndex + row, ProbeJS.GSON.toJson(defaultRow(columns, reagentRows)));
            if (row < rows - 1) {
                snippet.literal(",");
            }
        }
    }

    private static void appendRitualExampleReagentRows(Snippet snippet, int startIndex, String indent) {
        List<String> rows = List.of(" A ", "ABA", " A ");
        for (int row = 0; row < rows.size(); row++) {
            snippet.newline().literal(indent).tabStop(startIndex + row, ProbeJS.GSON.toJson(rows.get(row)));
            if (row < rows.size() - 1) {
                snippet.literal(",");
            }
        }
    }

    private static void appendIntGridBlock(Snippet snippet, int rows, int columns, int startIndex, String indent) {
        for (int row = 0; row < rows; row++) {
            snippet.newline().literal(indent).literal("[").tabStop(startIndex + row, defaultIntRow(columns)).literal("]");
            if (row < rows - 1) {
                snippet.literal(",");
            }
        }
    }

    private static void appendIdChoiceOrPlaceholder(Snippet snippet, String aliasName, int index, String fallback) {
        List<String> choices = aliasChoices(aliasName);
        if (choices.isEmpty()) {
            snippet.tabStop(index, ProbeJS.GSON.toJson(fallback));
        } else {
            snippet.choices(index, choices);
        }
    }

    private static List<String> aliasChoices(String aliasName) {
        return MnaJSLegacyProbeIdAliases.all().stream()
                .filter(alias -> alias.alias().equals(aliasName))
                .findFirst()
                .map(alias -> jsonChoices(alias.ids().get()))
                .orElseGet(List::of);
    }

    private static String defaultIntRow(int size) {
        StringBuilder row = new StringBuilder(size * 4);
        for (int index = 0; index < size; index++) {
            if (index > 0) {
                row.append(", ");
            }
            row.append('0');
        }
        return row.toString();
    }

    private static String defaultRow(int size, boolean reagentRow) {
        if (reagentRow) {
            return " ".repeat(size);
        }

        StringBuilder row = new StringBuilder(size * 2);
        for (int index = 0; index < size; index++) {
            if (index > 0) {
                row.append(' ');
            }
            row.append('0');
        }
        return row.toString();
    }

    private static List<String> jsonChoices(List<String> ids) {
        return ids.stream()
                .sorted()
                .distinct()
                .map(ProbeJS.GSON::toJson)
                .toList();
    }

    private static String toSnakeCase(String value) {
        return value
                .replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
                .replaceAll("([a-z\\d])([A-Z])", "$1_$2")
                .toLowerCase(Locale.ROOT);
    }
}
