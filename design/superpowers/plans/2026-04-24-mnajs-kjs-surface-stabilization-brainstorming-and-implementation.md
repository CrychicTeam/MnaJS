# MnaJS KJS Surface Stabilization Brainstorming And Implementation Plan

> For follow-up implementation work, treat this file as the execution checklist for the approved design.

Date: 2026-04-24
Project: MnaJS
Status: In progress. Slices 1-8 have landed and been compile-verified on 2026-04-24.

## Progress Log

### Completed Slice 1: Transmutation typed surface stabilization

- added `MnaLootTableId`
- added typed recipe components for item id, block id, and loot table id
- registered the new wrappers and components in the KubeJS plugin
- extended Probe with `MnaLootTableId` alias support and real loot table id discovery
- normalized `TransmutationRecipeJS` to typed id methods with `@HideFromJS` compatibility overloads
- normalized `TransmutationSchema` to typed `RecipeKey`s and explicit constructors
- normalized `TransmutationBuilder` to typed id storage and JSON emission
- added local PiSerializeKit `-deobf.jar` fallback in `build.gradle` so the project compiles even when the remote Maven artifact is unavailable

Verification:

- `bash ./gradlew compileJava --console=plain`
- result: `BUILD SUCCESSFUL`

### Completed Slice 2: first real PiSerializeKit integration

- PiSerializeKit now participates in `annotationProcessor`, not only runtime/compile classpath
- added `MnaPatternEditorDocumentState` as the first real `@PiSyncModel`
- added `MnaPatternDocumentKind` enum and `MnaPatternEditorDocuments` conversion helpers
- the document state uses the exact Pi-friendly shapes discussed in the design:
  - `Optional<ResourceLocation>`
  - `List<List<Integer>>`
  - `List<String>`
  - `Map<String, String>`
- compile verification confirmed generated files:
  - `MnaPatternEditorDocumentState_PiSchema`
  - `MnaPatternEditorDocumentState_PiSchemaProvider`
  - `MnaPatternEditorDocumentState_PiFields`

Verification:

- `bash ./gradlew compileJava --console=plain`
- result: `BUILD SUCCESSFUL`

### Completed Slice 3: wrapper-backed Pi serializers

- added runtime Pi serializer registration for the pure `ResourceLocation`-backed Mna wrappers
- avoided `ServiceLoader` ordering problems by bootstrapping custom serializers from `MnaJS` mod init
- added explicit `@PiField` codec provider classes for wrapper-backed fields
- changed the editor document `patternId` field from `Optional<ResourceLocation>` to `Optional<MnaManaweavePatternId>`
- compile verification confirmed generated schema now references `Optional<MnaManaweavePatternId>` through the explicit provider

Verification:

- `bash ./gradlew compileJava --console=plain`
- generated output contains `new com.pickaid.mnajs.kubejs.id.MnaTypedIdFieldCodecs.OptionalManaweavePatternIdCodec().serializer()`

### Completed Slice 4: `manaweaving-pattern` public `int[][]` stabilization

- replaced the boxed-number pattern key with an explicit `int[][]` recipe component in `ManaweavingPatternSchema`
- normalized `ManaweavingPatternRecipeJS` around `pattern(int[][])` and kept `byte[][]` as hidden compatibility only
- normalized `ManaweavingPatternBuilder` to store, validate, and emit `int[][]`

Verification:

- `bash ./gradlew compileJava --console=plain`
- result: `BUILD SUCCESSFUL`

### Completed Slice 5: ritual typed surface and validation normalization

- normalized `RitualRecipeJS` around:
  - `pattern(int[][])` and `patternRows(String...)`
  - `displayPattern(int[][])` and `displayPatternRows(String...)`
  - `reagentRows(String...)`
  - `reagent(char, MnaItemOrTag)` plus typed dynamic/manual-return variants
  - `outputItem(MnaItemId)`
- hid legacy string/item/itemstack ritual overloads from the JS-visible surface
- changed `RitualRecipeSchema` so `pattern` and `displayPattern` are `int[][]`
- changed `RitualRecipeSchema` so `createsItem` is `MnaItemId`
- normalized `RitualRecipeBuilder` to the same typed contract for `event.custom(...)`
- added explicit ritual validation for:
  - required `pattern`
  - required `reagents`
  - required `outputItem`
  - required key definitions
  - row/footprint mismatches
  - undefined reagent symbols
  - multiple dynamic sources

Verification:

- `bash ./gradlew compileJava --console=plain`
- result: `BUILD SUCCESSFUL`

### Completed Slice 6: single-item recipe surface normalization

- normalized `Crushing`, `ArcaneFurnace`, `RuneForging`, and `RuneScribing` schemas to `MnaItemId`
- normalized `FumerFliterSchema` to `MnaItemOrTag`
- added a dedicated `MnaItemOrTag` recipe component and registered it in `MnaJSPlugin`
- normalized the corresponding KubeJS recipe classes so their public item surfaces stay typed and their validation reads typed schema values
- normalized the corresponding `event.custom(...)` builders so their public signatures are wrapper-first and raw overloads are `@HideFromJS`

Verification:

- `bash ./gradlew compileJava --console=plain`
- result: `BUILD SUCCESSFUL`

### Completed Slice 7: altar and items-pattern family normalization

- changed `ItemsPatternSchema` to use `MnaItemOrTag[]`
- changed `ManaweavingAltarSchema` and `EldrinAltarSchema` to the same typed input family
- normalized `MnaItemsPatternRecipeJS`, `ManaweavingAltarRecipeJS`, and `EldrinAltarRecipeJS` around typed item-or-tag inputs
- normalized `ComponentBuilder`, `ModifierBuilder`, and `ShapeBuilder` around typed item-or-tag and manaweave-pattern inputs
- normalized `ManaweavingAltarBuilder` and `EldrinAltarBuilder` around typed item-id output and fixed their emitted recipe type ids
- tightened `Progression` and `ManaweaveCacheEffect` id parsing without inventing half-finished new wrapper families

Verification:

- `bash ./gradlew compileJava --console=plain`
- result: `BUILD SUCCESSFUL`

### Completed Slice 8: Probe snippet alignment for chain and editor-first flows

- replaced Probe ritual row snippets so they emit readable `.patternRows(...)` and `.reagentRows(...)` chain blocks instead of `MnaPatternHelper.*Rows(...)`
- added a full ritual chain scaffold snippet that matches the approved public contract:
  - `.patternRows(...)`
  - `.displayPatternRows(...)`
  - `.reagentRows(...)`
  - typed reagent declarations
  - `.outputItem(...)`
- added dedicated reagent declaration snippets for:
  - `.reagent(...)`
  - `.dynamicReagent(...)`
  - `.dynamicSourceReagent(...)`
  - `.manualReturnReagent(...)`
- replaced the old manaweave helper snippet with:
  - a readable `.pattern([...])` `int[][]` scaffold
  - a `.manaweavePatterns(...)` chain snippet backed by actual discovered pattern ids when available
- added texture-facing snippets for quoted texture literals and `.icon(...)` calls so Probe no longer ignores the texture authoring surface

Verification:

- `bash ./gradlew compileJava --console=plain`
- result: `BUILD SUCCESSFUL`

External follow-up completed on 2026-04-24:

- updated `toolkit-box-page/docs/.vitepress/utils/ritualGenerator/useRitualGenerator.ts` outside this repository so the ritual generator now emits:
  - numeric-grid JSON for `pattern` and `displayPattern`
  - chain-style KubeJS output with `.patternRows(...)`, `.displayPatternRows(...)`, `.reagentRows(...)`, typed reagent helper calls, and `.outputItem(...)`
  - explicit invalid-output messages when the output item is missing
- file-level verification passed in the external repository:
  - `pnpm exec tsc --noEmit .vitepress/utils/ritualGenerator/useRitualGenerator.ts`
- full site-level verification for `toolkit-box-page` still needs to be run in that repository

### Next Slice Candidates

- editor document model and toolkit alignment
- toolkit output alignment for ritual and pattern authoring
- optional vanilla-id wrapper families if Probe-backed completion is worth carrying

## Example Authoring Rule

For startup registry surfaces like `mna:ritual-effects` and `mna:components`, examples and future docs must use an explicitly typed local builder before chaining.

Required pattern:

```js
/**
 * @type {Internal.CustomRitualEffect$Builder}
 */
const ritualBuilder = event.create("kubejs:verdant_bloom", "basic");

ritualBuilder.applyEffect((context) => true);

/**
 * @type {Internal.CustomDamageComponent$Builder}
 */
const damageBuilder = event.create("kubejs:searing_arc", "damage");

damageBuilder.damageEntity((source, target, damage, context) => {
    return ComponentApplicationResult.SUCCESS;
});
```

Do not use direct `event.create(...).method(...)` chains there as the primary example until Probe can narrow those builder types on its own.

## Goal

Stabilize the MnaJS authoring surface so that:

- ids are typed
- Probe completion is trustworthy
- recipe chaining is consistent
- `lootTable` and `representationItem` stop leaking as loose strings
- pattern authoring becomes editor-first
- the in-game editor and `toolkit-box-page` share one document model
- `toolkit-box-page` emits the same DSL that runtime expects

## Current Defects To Fix

- some recipe JS classes still expose weak or inconsistent signatures
- `MnaPatternHelper` still risks being treated as a public authoring direction even though it does not solve the shape-drawing problem
- the website ritual generator still emits old output
- there is no in-game pattern editor yet
- Probe support is still not rich enough for the final wrapper/typeid direction

## Architecture Decisions

### 1. Item And Block Fields

Options considered:

- keep raw `String`
- use vanilla `Item` and `Block` directly
- use dedicated wrappers

Decision:

- use wrappers as the public KJS surface

Chosen wrappers:

- `MnaItemId`
- `MnaBlockId`

### 2. Item-Or-Tag Inputs

Options considered:

- raw strings only
- vanilla `Either`-style surfaces
- dedicated wrapper

Decision:

- use `MnaItemOrTag`

Reason:

- better Probe boundary
- better authoring intent
- no Rhino overload guessing

### 3. Loot Table Surface

Options considered:

- keep raw `String`
- expose `ResourceLocation`
- introduce `MnaLootTableId`

Decision:

- introduce `MnaLootTableId`

Reason:

- user explicitly rejected loose string handling here
- it is an id-bearing field and should match the rest of the stabilized surface

### 4. Output Fields That Also Need Stack Support

Options considered:

- one overloaded method with many accepted runtime types
- one typed-id method plus one explicitly named stack method

Decision:

- keep the common path typed-id first
- use separate method names for stack-rich paths

Example:

- `output(MnaItemId)`
- `outputStack(ItemStack)`

### 5. Ritual Reagent Surface

Decision:

- ritual reagent definitions use `MnaItemOrTag`

Reason:

- one public type family covers item ids and tags
- works with Probe
- works with chain DSL

### 6. Pattern Data Type

Options considered:

- keep `byte[][]`
- expose both `byte[][]` and `int[][]`
- move fully to `int[][]`

Decision:

- move fully to `int[][]`

Reason:

- explicit user requirement
- `byte` is needless JS friction

### 7. Pattern Authoring Strategy

Options considered:

- keep inventing helper APIs
- move the main authoring path into editors and demote helpers to infrastructure

Decision:

- editor-first
- helper-last

Consequence:

- `MnaPatternHelper` is not part of the public product story
- if retained temporarily, it should be reduced to parser or normalization infrastructure
- exported code should be shaped around editor output, not around geometry-scripting fantasy

### 8. In-Game Editor Entry Point

Options considered:

- machine-bound UI
- guidebook-only entry
- client command or utility-item authoring tool

Decision:

- first version uses a client authoring entry point
- command-first is the safest initial path
- utility item may be added later if it improves workflow

Reason:

- tile support is out of scope
- the editor targets content authors
- MnaJS does not currently have existing gameplay-machine UI infrastructure for this feature

### 9. In-Game Editor Runtime Architecture

Options considered:

- `AbstractContainerMenu` + server-owned state
- pure client screen with local state
- Pibrary-driven session framework from day one

Decision:

- version one uses a pure client screen with local state
- no mandatory menu
- no mandatory packet path
- no mandatory Pibrary dependency

Reason:

- the first editor is an authoring tool, not a server authority surface
- MnaJS currently has no editor UI baseline
- current project properties do not configure Pibrary

Follow-up rule:

- if packets are later required, use typed DTOs and preferably PiSerializeKit-backed serializers

### 10. Shared Editor Model

Decision:

- the in-game editor and `toolkit-box-page` must share one document model and one export contract

Reason:

- prevents drift between authoring surfaces
- allows import/export round-tripping
- keeps validation rules centralized

Concrete implementation rule:

- distinguish between the public export model and the Pi-backed persistence model
- public export still targets readable `int[][]`
- Pi-backed persistence should prefer `List<List<Integer>>`, `Optional<...>`, `Map<String, ...>`, `ResourceLocation`, and enums
- reagent symbol maps should use single-character `String` keys, not `Character`

Reason:

- PiSerializeKit clearly supports composite collections and enums
- PiSerializeKit does not expose a built-in `Character` serializer
- primitive-array persistence should not become a hidden processor gamble
- explicit conversion at import/export boundaries is cheaper than fighting serializers everywhere

### 11. PiSerializeKit Role

Options considered:

- ignore it for now
- use it everywhere
- use it only where it improves typed parser or payload boundaries

Decision:

- use it only where it gives real value

Approved use cases:

- editor document codecs and generated bindings
- import/export payload envelopes
- wrapper normalization around id-like values
- future packet payloads if the editor later needs them
- migration-safe local editor save formats

Rejected use case:

- pretending it solves drawing UX

Concrete consequence:

- if editor state classes use `@PiSyncModel` or packets use `@PiPacket`, MnaJS must wire PiSerializeKit on the annotation-processor path instead of relying only on `implementation`
- wrapper-heavy KJS-facing types should stay outside Pi-backed state where possible
- prefer Pi-friendly nested state classes over custom serializer providers when the same model can be expressed with built-in `ResourceLocation`, enums, lists, maps, and optionals

## File Plan

### MnaJS Wrapper And Probe Layer

- Modify: `src/main/java/com/pickaid/mnajs/kubejs/MnaJSPlugin.java`
- Modify: `src/main/java/com/pickaid/mnajs/kubejs/probe/MnaJSLegacyProbeIdAliases.java`
- Modify: `src/main/java/com/pickaid/mnajs/kubejs/probe/MnaJSLegacyProbeJava.java`
- Modify: `src/main/java/com/pickaid/mnajs/kubejs/probe/MnaJSLegacyProbeSnippets.java`
- Create: `src/main/java/com/pickaid/mnajs/kubejs/id/MnaLootTableId.java`
- Verify and finish: `src/main/java/com/pickaid/mnajs/kubejs/id/MnaItemId.java`
- Verify and finish: `src/main/java/com/pickaid/mnajs/kubejs/id/MnaBlockId.java`
- Verify and finish: `src/main/java/com/pickaid/mnajs/kubejs/id/MnaItemOrTag.java`
- Verify and finish: `src/main/java/com/pickaid/mnajs/kubejs/texture/MnaTexture.java`

### Recipe JS Layer

- Modify: `src/main/java/com/pickaid/mnajs/kubejs/recipe/MnaBaseRecipeJS.java`
- Modify: `src/main/java/com/pickaid/mnajs/kubejs/recipe/ArcaneFurnaceRecipeJS.java`
- Modify: `src/main/java/com/pickaid/mnajs/kubejs/recipe/CrushingRecipeJS.java`
- Modify: `src/main/java/com/pickaid/mnajs/kubejs/recipe/FumeFilterRecipeJS.java`
- Modify: `src/main/java/com/pickaid/mnajs/kubejs/recipe/RuneForgingRecipeJS.java`
- Modify: `src/main/java/com/pickaid/mnajs/kubejs/recipe/RuneScribingRecipeJS.java`
- Modify: `src/main/java/com/pickaid/mnajs/kubejs/recipe/EldrinAltarRecipeJS.java`
- Modify: `src/main/java/com/pickaid/mnajs/kubejs/recipe/ManaweavingAltarRecipeJS.java`
- Modify: `src/main/java/com/pickaid/mnajs/kubejs/recipe/TransmutationRecipeJS.java`
- Modify: `src/main/java/com/pickaid/mnajs/kubejs/recipe/RitualRecipeJS.java`
- Modify: `src/main/java/com/pickaid/mnajs/kubejs/recipe/MnaItemsPatternRecipeJS.java`
- Modify: `src/main/java/com/pickaid/mnajs/kubejs/recipe/ManaweavingPatternRecipeJS.java`

### Pattern Schema And Builder Layer

- Modify: `src/main/java/com/pickaid/mnajs/recipes/schema/ManaweavingPatternSchema.java`
- Modify: `src/main/java/com/pickaid/mnajs/recipes/builders/ManaweavingPatternBuilder.java`
- Modify: `src/main/java/com/pickaid/mnajs/recipes/schema/RitualRecipeSchema.java`
- Modify: `src/main/java/com/pickaid/mnajs/recipes/builders/RitualRecipeBuilder.java`
- Demote or remove from public story: `src/main/java/com/pickaid/mnajs/kubejs/pattern/MnaPatternHelper.java`

### In-Game Editor Layer

These names are concrete targets, though minor package naming changes are acceptable if the same boundaries are preserved.

- Create: `src/main/java/com/pickaid/mnajs/handlers/client/ClientStartup.java`
- Create: `src/main/java/com/pickaid/mnajs/client/editor/pattern/PatternEditorMode.java`
- Create: `src/main/java/com/pickaid/mnajs/client/editor/pattern/PatternEditorState.java`
- Create: `src/main/java/com/pickaid/mnajs/client/editor/pattern/PatternEditorScreen.java`
- Create: `src/main/java/com/pickaid/mnajs/client/editor/pattern/PatternEditorSelection.java`
- Create: `src/main/java/com/pickaid/mnajs/client/editor/pattern/model/PatternDocument.java`
- Create: `src/main/java/com/pickaid/mnajs/client/editor/pattern/model/RitualPatternDocument.java`
- Create: `src/main/java/com/pickaid/mnajs/client/editor/pattern/model/ManaweavePatternDocument.java`
- Create: `src/main/java/com/pickaid/mnajs/client/editor/pattern/model/pi/PatternDocumentState.java`
- Create: `src/main/java/com/pickaid/mnajs/client/editor/pattern/model/pi/RitualPatternState.java`
- Create: `src/main/java/com/pickaid/mnajs/client/editor/pattern/model/pi/ManaweavePatternState.java`
- Create: `src/main/java/com/pickaid/mnajs/client/editor/pattern/model/pi/ReagentDefinitionState.java`
- Create: `src/main/java/com/pickaid/mnajs/client/editor/pattern/model/pi/ItemOrTagRefState.java`
- Create: `src/main/java/com/pickaid/mnajs/client/editor/pattern/model/pi/PatternMetadataState.java`
- Create: `src/main/java/com/pickaid/mnajs/client/editor/pattern/export/PatternExportService.java`
- Create: `src/main/java/com/pickaid/mnajs/client/editor/pattern/export/PatternImportService.java`
- Optional if PiSerializeKit custom providers become necessary: `src/main/java/com/pickaid/mnajs/client/editor/pattern/codec/PatternFieldCodecProviders.java`
- Optional if editor transport becomes networked: `src/main/java/com/pickaid/mnajs/client/editor/pattern/packet/*.java`
- Optional if command-first entry is used: `src/main/java/com/pickaid/mnajs/client/editor/pattern/PatternEditorClientCommands.java`

### Dependency Layer

- Verify: `project.toml`
  - `piserializekit_version = "0.0.4"` is already enabled in the current working branch
  - Pibrary is not configured in current project properties
  - prefer wiring PiSerializeKit processor support through the existing `[dependencies]` template surface if possible
- Verify: `build.gradle`
  - confirm PiSerializeKit resolves cleanly
  - add explicit PiSerializeKit annotation-processor wiring before introducing `@PiSyncModel` or `@PiPacket`
  - do not accidentally turn Pibrary into an unplanned hard dependency

### Toolkit Layer

- Modify: `/Users/gedwen/Documents/programing/GitHub/toolkit-box-page/docs/.vitepress/utils/ritualGenerator/useRitualGenerator.ts`
- Modify: `/Users/gedwen/Documents/programing/GitHub/toolkit-box-page/docs/.vitepress/theme/components/AvalonWard/index.ts`
- Modify: `/Users/gedwen/Documents/programing/GitHub/toolkit-box-page/docs/.vitepress/utils/vitepress/componentRegistry/localToolkitComponents.ts`
- Modify: `/Users/gedwen/Documents/programing/GitHub/toolkit-box-page/docs/src/en-US/Mna/index.md`
- Modify: `/Users/gedwen/Documents/programing/GitHub/toolkit-box-page/docs/src/zh-CN/Mna/index.md`
- Create: dedicated pattern editor component tree under the AvalonWard MNA tool area
- Create: shared editor-document types and export utilities on the toolkit side

## Ordered Implementation Tasks

- [ ] **Task 1: Finish the wrapper inventory**
  - add `MnaLootTableId`
  - finish Probe aliases for item/block/item-or-tag/loot-table/texture surfaces
  - ensure wrappers are usable as the intended `typeid` surface

- [ ] **Task 2: Normalize transmutation first**
  - refactor `targetBlock`, `replaceBlock`, `lootTable`, and `representationItem`
  - remove JS-visible loose string behavior
  - add validation for `lootTable` plus `representationItem`

- [ ] **Task 3: Normalize single-item recipe surfaces**
  - `ArcaneFurnaceRecipeJS`
  - `CrushingRecipeJS`
  - `RuneForgingRecipeJS`
  - `RuneScribingRecipeJS`
  - `FumeFilterRecipeJS`

- [ ] **Task 4: Normalize altar and pattern-item surfaces**
  - `MnaItemsPatternRecipeJS`
  - `EldrinAltarRecipeJS`
  - `ManaweavingAltarRecipeJS`
  - keep public surfaces typed and overload-safe

- [ ] **Task 5: Rewrite ritual around the approved chain contract**
  - finish typed reagent definitions
  - finish `pattern` and `displayPattern` handling
  - keep `outputItem` required
  - make required-field failures explicit

- [ ] **Task 6: Stabilize pattern storage and builders**
  - move schema and builder usage fully to `int[][]`
  - remove JS-visible `byte[][]`
  - align builder expectations with editor output

- [ ] **Task 7: Define the shared pattern document before UI coding spreads**
  - lock down the editor document model
  - lock down export flavors
  - lock down validation rules for ritual and manaweave modes
  - lock down the Pi-friendly persistence model separately from the public KJS export model
  - use `List<List<Integer>>` and `Optional<...>` in Pi-backed state instead of betting on primitive-array support
  - use single-character `String` keys for reagent maps

- [ ] **Task 8: Build the in-game editor core**
  - wire PiSerializeKit annotation processing if Pi-backed editor state is used
  - create client bootstrap path
  - create shared editor state and selection model
  - implement base screen shell
  - keep it client-local first

- [ ] **Task 9: Implement ritual editor mode**
  - layered pattern and display-pattern editing
  - reagent row overlay support
  - typed reagent-definition panel
  - KJS chain DSL export

- [ ] **Task 10: Implement manaweave editor mode**
  - 11x11 numeric editing
  - value brush or palette
  - transforms and readable export

- [ ] **Task 11: Add import/export infrastructure**
  - clipboard export
  - readable JSON or editor-document export
  - optional explicit local file export
  - if codecs are introduced here, use PiSerializeKit where it helps
  - convert Pi-backed persistence classes into public `int[][]` export shapes here instead of leaking Pi-friendly collection shapes into KJS output

- [ ] **Task 12: Update toolkit-box-page to the same model**
  - change ritual generator output to chain DSL
  - add the dedicated pattern editor
  - mirror the same document model and export contracts
  - expose the tooling in both locales

- [ ] **Task 13: Reduce helper prominence**
  - stop documenting `MnaPatternHelper` as the recommended solution
  - keep only necessary parser or normalization logic
  - do not ship new helper-led UX features

- [ ] **Task 14: Verification**
  - compile MnaJS
  - build toolkit-box-page docs
  - inspect representative exported KJS output from ritual and manaweave flows

## Verification Commands

### MnaJS

Run:

```bash
cd /Users/gedwen/Documents/programing/MC/MnaJS
./gradlew compileJava --console=plain
```

Expected:

- wrapper and recipe changes compile cleanly
- editor classes compile cleanly
- no touched public KJS surface falls back to JS-visible `Object`

### toolkit-box-page

Run:

```bash
cd /Users/gedwen/Documents/programing/GitHub/toolkit-box-page/docs
yarn docs:build
```

Expected:

- VitePress builds cleanly
- ritual generator output matches the chain DSL
- pattern editor pages are reachable from the MNA index pages
- toolkit export matches the runtime editor document contract

## Risks And Sequencing Notes

- Do not start by polishing helper APIs again. That is the old failure mode.
- Do not couple the first in-game editor to tile support. That is outside scope.
- Do not force Pibrary into the first editor iteration just because it has related concepts.
- Do not let toolkit invent a separate payload shape from the runtime editor.
- Do not let Pi-backed persistence types leak directly into the public KJS/export contract.
- Do not finish editor UI before locking the shared pattern document model.

## Recommended Implementation Order

1. wrappers and Probe
2. transmutation and ritual normalization
3. `int[][]` stabilization
4. shared editor document
5. in-game editor core
6. toolkit editor and generator alignment
7. PiSerializeKit cleanup where it actually reduces payload or parser boilerplate
