# MnaJS KJS Surface Stabilization And Editor-First Pattern Authoring Design

Date: 2026-04-24
Project: MnaJS
Status: In progress. Multiple implementation slices landed and compile-verified on 2026-04-24, including Probe snippet alignment for the chain DSL.

## Implementation Progress

Implemented in the first slice:

- `MnaLootTableId` now exists as a dedicated typed id wrapper.
- typed recipe components now include `MnaItemId`, `MnaBlockId`, and `MnaLootTableId`.
- Probe now knows `MnaLootTableId` and scans actual loot table ids from project resources and code-source jars.
- `TransmutationRecipeJS`, `TransmutationSchema`, and `TransmutationBuilder` now use typed id surfaces instead of loose `String`/vanilla overload surfaces.
- JS-visible `Transmutation` entry points now expose one public type family per concept, with legacy compatibility overloads hidden from JS.
- `build.gradle` now prefers a local PiSerializeKit `-deobf.jar` when co-developing beside `../PiSerializeKit`, while keeping remote Maven fallback.

Implemented in the second slice:

- PiSerializeKit is now actually used in MnaJS source instead of being only a declared dependency.
- `build.gradle` now wires PiSerializeKit to both `implementation` and `annotationProcessor`.
- a first editor-side Pi model now exists:
  - `MnaPatternEditorDocumentState`
  - `MnaPatternDocumentKind`
  - `MnaPatternEditorDocuments`
- the document model stores editor-facing state in Pi-friendly shapes:
  - `enum` for document kind
  - `Optional<ResourceLocation>` for optional pattern id
  - `List<List<Integer>>` for the numeric grid
  - `List<String>` for reagent rows
  - `Map<String, String>` for symbol bindings
- local compile verification confirmed generated Pi output under `build/generated/sources/annotationProcessor/java/main/...`

Implemented in the third slice:

- pure `ResourceLocation`-backed Mna wrappers now have Pi serializer registrations:
  - `MnaFactionId`
  - `MnaRitualEffectId`
  - `MnaSpellEffectId`
  - `MnaShapeId`
  - `MnaModifierId`
  - `MnaRitualId`
  - `MnaManaweavePatternId`
  - `MnaCantripId`
  - `MnaItemId`
  - `MnaBlockId`
  - `MnaLootTableId`
  - `MnaTexture`
- MnaJS now bootstraps those serializers at mod init instead of relying on ServiceLoader ordering.
- explicit `@PiField(serializer = ...)` providers now exist for wrapper-backed fields.
- the editor document now uses `Optional<MnaManaweavePatternId>` instead of `Optional<ResourceLocation>` for `patternId`.

Implemented in the fourth slice:

- `ManaweavingPatternSchema` now uses an explicit `int[][]` recipe component instead of falling back to boxed-number arrays.
- `ManaweavingPatternRecipeJS` now treats `int[][]` as the public contract and keeps `byte[][]` only as a hidden compatibility path.
- `ManaweavingPatternBuilder` now stores and validates `int[][]` end-to-end.
- local compile verification confirmed the `manaweaving-pattern` public surface no longer requires JS authors to work through `byte[][]`.

Implemented in the fifth slice:

- `RitualRecipeJS` now exposes typed reagent and output surfaces:
  - `reagent(char, MnaItemOrTag)`
  - `dynamicReagent(char, MnaItemOrTag)`
  - `dynamicSourceReagent(char, MnaItemOrTag)`
  - `manualReturnReagent(char, MnaItemOrTag)`
  - `outputItem(MnaItemId)`
- `RitualRecipeJS` now exposes readable import paths:
  - `patternRows(String...)`
  - `displayPatternRows(String...)`
  - `reagentRows(String...)`
- legacy string/item/itemstack ritual overloads are now hidden from JS instead of remaining as the primary surface.
- `RitualRecipeSchema` now stores `pattern` and `displayPattern` as `int[][]` and stores `createsItem` as `MnaItemId`.
- `RitualRecipeBuilder` now mirrors the same typed surface and required-field rules for `event.custom(...)` authors.
- ritual validation now fails loudly for:
  - missing `pattern`
  - missing `reagents`
  - missing `outputItem`
  - missing reagent key definitions
  - reagent rows that do not match pattern footprint
  - undefined reagent symbols
  - multiple dynamic-source reagents

Implemented in the sixth slice:

- `CrushingSchema`, `ArcaneFurnaceSchema`, `RuneForgingSchema`, and `RunescribingSchema` now use `MnaItemId` keys instead of legacy `Item` keys.
- `FumerFliterSchema` now uses `MnaItemOrTag` instead of exposing the old `Either<TagKey<Item>, Item>` surface.
- the KubeJS recipe classes for those recipes now keep typed wrappers through `setKey(...)` and validate against typed schema values instead of legacy vanilla item values.
- `CrushingBuilder`, `ArcaneFurnaceBuilder`, `RuneForgingBuilder`, and `RuneScribingBuilder` now expose typed `MnaItemId` public methods with raw compatibility overloads hidden from JS.
- `FumeFilterBuilder` now exposes `MnaItemOrTag` as the public input family and keeps only same-family alias names such as `input(...)`.
- `MnaRecipeComponents` now includes a dedicated `MnaItemOrTag` recipe component, and `MnaJSPlugin` registers it for schema use.

Implemented in the seventh slice:

- `ItemsPatternSchema` now uses `MnaItemOrTag[]` for shared item-input families instead of the old raw `Either<TagKey<Item>, Item>[]` surface.
- `ManaweavingAltarSchema` and `EldrinAltarSchema` now inherit that typed item-or-tag input surface.
- `MnaItemsPatternRecipeJS`, `ManaweavingAltarRecipeJS`, and `EldrinAltarRecipeJS` now feed typed item-or-tag values into schema parsing instead of flattening everything back to loose strings as the primary path.
- `ComponentBuilder`, `ModifierBuilder`, and `ShapeBuilder` now expose typed item-or-tag inputs and typed manaweave-pattern inputs, with raw overloads hidden from JS.
- `ManaweavingAltarBuilder` and `EldrinAltarBuilder` now expose typed item-id output and typed item-or-tag input families, and their emitted recipe type ids now match the actual registered recipe ids.
- `ManaweaveCacheEffectRecipeJS` and `ManaweaveCacheEffectBuilder` now normalize string effect ids through registry lookup instead of leaving raw strings in-flight.
- `ProgressionRecipeJS` and `ProgressionRecipeBuilder` now normalize advancement ids before emission, while keeping one JS-visible advancement type family.

Implemented in the eighth slice:

- Probe ritual snippets no longer teach `MnaPatternHelper.*Rows(...)` as the primary story.
- Probe now emits a ritual chain scaffold built around:
  - `.patternRows(...)`
  - `.displayPatternRows(...)`
  - `.reagentRows(...)`
  - typed reagent declaration calls
  - `.outputItem(...)`
- Probe now emits standalone row-block snippets for `patternRows(...)` and `reagentRows(...)`.
- Probe now emits manaweave snippets as readable nested numeric arrays and typed `.manaweavePatterns(...)` calls, not helper-generated string rows.
- Probe now includes texture-facing snippets for quoted texture literals and `.icon(...)` calls, which keeps the texture authoring surface visible while the richer texture type support continues to rely on Probe's special typing.
- the external `toolkit-box-page` ritual generator has also been updated to emit numeric-grid JSON and the chain-style ritual DSL.
- file-level verification for that generator now passes with:
  - `pnpm exec tsc --noEmit .vitepress/utils/ritualGenerator/useRitualGenerator.ts`
- broader site-level verification for the external repository is still pending.

Important boundary:

- PiSerializeKit now handles wrapper serialization, NBT, packet, and schema usage.
- PiSerializeKit still does not replace Rhino/KubeJS-side `parse(Object)` or `registerTypeWrappers(...)`.

Still pending:

- editor-first pattern authoring implementation
- `toolkit-box-page` generator/editor alignment
- new wrapper families for non-MNA vanilla ids such as advancements or mob effects, if Probe-backed completion is later deemed worth the maintenance cost

## Why This Design Exists

MnaJS already has enough partial KubeJS work to be dangerous.

The current surface is not empty. It is inconsistent:

- several MNA-native ids already use wrapper + Probe typing
- several recipe classes still expose weak or ambiguous JS signatures
- `lootTable` and `representationItem` are still under-typed
- `toolkit-box-page` still emits older ritual output instead of the intended chain DSL
- pattern authoring still risks falling back into "one more helper method" instead of solving the actual drawing problem

That combination produces fake confidence. Authors get partial completion, partial safety, and partial readability, while still being forced into awkward authoring workflows.

This design treats the KJS surface, the editors, and the generator as one product surface:

- wrapper-first ids
- Probe and snippet support
- chain-style recipes
- editor-first pattern authoring
- toolkit alignment
- validation quality

These pieces must converge together.

## User Directives

The following constraints are explicit and non-negotiable for this pass:

- do not expose JS-visible `Object`
- prefer `typeid`-style wrapper + Probe surfaces
- recipe APIs should use typed ids wherever practical for completion
- avoid overload ambiguity
- one JS-visible method name should expose one type family only
- alternate overloads, if retained for Java-side compatibility, must be `@HideFromJS`
- `manaweaving-pattern` must use `int`, not `byte`
- `lootTable` and `representationItem` must become properly typed id surfaces
- Probe completion must be strong, including texture-oriented completion in the same spirit as PassiveSTJS
- tile or block-entity support is out of scope for now
- pattern authoring must become editor-first
- helper-led pattern authoring is not the solution
- `toolkit-box-page` must emit the same chain DSL and pattern payload shape that runtime expects
- PiSerializeKit may be used where it improves parser or payload boundaries, but not as a substitute for visual authoring UX

## Builder Completion Rule

For startup registry builders whose real type depends on the second `event.create(id, type)` argument, Probe currently needs an explicit JSDoc-typed local before chaining.

That rule is now part of the authoring contract for examples and future docs.

Use:

```js
/**
 * @type {Internal.CustomRitualEffect$Builder}
 */
const ritualBuilder = event.create("kubejs:verdant_bloom", "basic");

ritualBuilder
    .ritualName("mna:ritual/verdant_bloom")
    .applyEffect((context) => true);

/**
 * @type {Internal.CustomSpellEffect$Builder}
 */
const componentBuilder = event.create("kubejs:arcane_pulse", "basic");

componentBuilder
    .applyEffect((source, target, modificationData, context) => {
        return ComponentApplicationResult.SUCCESS;
    });
```

Do not teach the direct style as the primary example:

```js
event.create("kubejs:arcane_pulse", "basic").applyEffect(...)
```

That direct chain does not currently provide reliable completion for `ritual-effects` and `components`.

## Verified Local Facts

### Wrapper Foundation Already Exists

The repository already exposes wrapper-based MNA ids and therefore has an established direction:

- `MnaFactionId`
- `MnaRitualEffectId`
- `MnaSpellEffectId`
- `MnaShapeId`
- `MnaModifierId`
- `MnaRitualId`
- `MnaManaweavePatternId`
- `MnaCantripId`
- `MnaTexture`

The remaining recipe surface should follow that route instead of leaking back into loose strings or generic `Object`.

### The Recipe Refactor Is Still Mid-Flight

The current working tree contains partial refactor work but not a finished contract:

- some recipe classes were moved toward typed chaining
- several classes still expose incorrect or incomplete signatures
- `TransmutationRecipeJS` has now been normalized as the first completed typed-id slice
- `RitualRecipeJS` is now normalized around typed reagent/output surfaces and explicit validation
- `ManaweavingPatternSchema` and `ManaweavingPatternBuilder` are now stabilized around `int[][]`

This design document is therefore the stop point before more code is changed.

### `lootTable` And `representationItem` Are Still Under-Typed

The user callout here is correct:

- `lootTable` should be authored as a typed id-bearing surface
- `representationItem` should be authored as a typed item-id surface
- both need validation and Probe-friendly boundaries
- neither should remain casual strings while the rest of the API is being hardened

### `toolkit-box-page` Is Still Out Of Sync

The current local toolkit repository already contains:

- a ritual generator
- a runescribing editor with a split canvas/control layout

But the ritual generator still targets older output style instead of the intended chain DSL.

Design implication:

- the website is not just documentation
- it is an authoring surface
- if it emits stale DSL, the product is inconsistent

### PiSerializeKit Is Enabled, Pibrary Is Not

Current local configuration facts:

- `project.toml` now sets `piserializekit_version = "0.0.4"`
- current project config does not declare `pibrary_version`
- `build.gradle` already has conditional dependency hooks for both
- `build.gradle` currently wires PiSerializeKit as `implementation`, but does not yet explicitly add it to `annotationProcessor`

Local PiSerializeKit source review confirmed readily-usable serializer support for:

- `Integer`
- `String`
- `ResourceLocation`
- `ItemStack`
- collection helpers such as `arrayOf`, `listOf`, and `optionalOf`

Local Pibrary source review confirmed that Pibrary has screen-session concepts, but MnaJS is not currently wired to it.

Design implication:

- PiSerializeKit is available as an approved supporting dependency candidate now
- Pibrary should not be treated as a mandatory dependency for the first editor iteration
- if MnaJS starts using `@PiSyncModel` or `@PiPacket`, PiSerializeKit must also be present on the annotation-processor path instead of relying on the runtime jar alone

### MnaJS Currently Has No Real Client Editor Infrastructure

Current repository inspection shows:

- main mod bootstrap exists
- server event handlers exist
- KubeJS integration exists
- there is no meaningful existing client editor package, screen stack, or menu stack in MnaJS itself

Design implication:

- the first in-game editor should be designed as new infrastructure
- it should not pretend there is already a stable UI framework to plug into

## Target Outcome

After this design is implemented, MnaJS KJS support should cover one coherent authoring system:

1. MNA-native ids use wrapper-first typed surfaces with Probe completion.
2. item, block, item-or-tag, loot-table, and texture-facing surfaces are typed and Probe-friendly.
3. recipe APIs avoid JS-visible overload ambiguity.
4. ritual and transmutation surfaces are normalized around explicit chain DSL and validation.
5. `manaweaving-pattern` authoring uses `int[][]` end-to-end on the public side.
6. pattern authoring is primarily done through an in-game editor or `toolkit-box-page`, not through helper-first geometry scripting.
7. the in-game editor and the toolkit editor share one pattern document model and one export contract.
8. Probe snippets help authors consume editor output cleanly instead of steering them back toward raw strings.

## Scope

This design covers:

- the MnaJS KubeJS wrapper and Probe surface
- recipe JS classes under `src/main/java/com/pickaid/mnajs/kubejs/recipe`
- typed ids for `lootTable`, `representationItem`, items, blocks, and item-or-tag inputs
- ritual and manaweaving pattern public contracts
- the in-game pattern editor architecture
- the `toolkit-box-page` ritual generator and pattern editor alignment
- PiSerializeKit use for editor document, parser, and payload boundaries

This design does not cover:

- tile or block-entity scripting support
- new machine authoring APIs
- capability bridges for every future system
- automatic machine UI generation
- unrelated runtime/content refactors outside the KJS authoring surface

## Design Principles

### 1. No JS-Visible `Object`

If a field is conceptually an id, expose a typed id wrapper.

If a field is conceptually an item-or-tag input, expose a typed item-or-tag wrapper.

If a field conceptually supports advanced stack transport, expose that on a differently named method instead of asking Rhino to guess.

### 2. Completion Is Part Of The Product

Typed wrappers are not only runtime parsing boundaries.

They are the anchor for:

- Probe aliases
- TS dump quality
- snippets
- lower authoring error rates

### 3. One JS Surface Per Concept

The JS-visible API should never ask the runtime to guess among:

- `String`
- vanilla types
- stack types
- wrappers
- `Object`

Choose one public family. Hide or remove the rest from JS.

### 4. Pattern Authoring Must Be Editor-First

The hard problem is drawing the shape, not parsing a finished array.

Therefore:

- helpers cannot be treated as the main authoring answer
- editors are the primary authoring surfaces
- export/import utilities only exist to support the editors and the public recipe contract

### 5. Runtime And Toolkit Must Share One Model

The in-game editor and the web editor must not invent different representations for the same content.

They should share:

- one pattern document model
- one export contract
- one validation story

### 6. Client-Local First, Networking Only When Needed

The first in-game editor is a scripter tool, not a gameplay machine.

That means the safest initial architecture is:

- client-local state
- client-local rendering
- export/import boundaries
- optional packets only when later requirements justify them

### 7. PiSerializeKit Has A Bounded Role

PiSerializeKit is useful for:

- typed editor documents
- import/export payloads
- future packet DTOs
- structured resource-id normalization

PiSerializeKit is not useful for:

- drawing UX
- editor interaction design
- Probe completion by itself

## Chosen Public Type Families

### MNA-Native Registry And Recipe Ids

Keep and extend the current wrapper approach for:

- `MnaFactionId`
- `MnaRitualEffectId`
- `MnaSpellEffectId`
- `MnaShapeId`
- `MnaModifierId`
- `MnaRitualId`
- `MnaManaweavePatternId`
- `MnaCantripId`
- `MnaTexture`

### Vanilla-Oriented Typed References

Add or finish wrapper families for:

- `MnaItemId`
- `MnaBlockId`
- `MnaItemOrTag`
- `MnaLootTableId`

Probe intent:

- `MnaItemId` -> item completion
- `MnaBlockId` -> block completion
- `MnaItemOrTag` -> item-or-tag completion
- `MnaLootTableId` -> typed resource-id surface, even if the first Probe pass only offers resource-location-level completion

## Pattern Authoring Architecture

### Core Decision

Pattern authoring must become editor-first.

`MnaPatternHelper` is not the product surface and should not remain a recommended authoring entry point.

If helper logic survives at all, it is only as internal infrastructure for:

- parsing
- normalization
- validation
- import/export

It is not the UX answer.

### Shared Pattern Document Model

Both the in-game editor and `toolkit-box-page` should speak one logical document model.

That model should be expressed at two layers:

1. authoring/export model
2. PiSerializeKit-friendly persistence model

### Authoring And Export Model

This is the model authors conceptually work with and what KJS export should target.

It should include:

- `version`
- `mode`
  - `ritual`
  - `manaweave`
- `gridSize`
- `pattern: int[][]`
- `displayPattern: int[][]` when present
- `reagents: String[]` when present
- `reagentDefinitions`
- `metadata`
  - mode-specific fields such as tier, colors, manaweave references, or output hints

Design rules for the authoring/export model:

- public KJS and generated code still target `int[][]`
- ritual reagent overlay stays a separate concern from numeric pattern data
- export flavors are derived views of the same document, not separate source-of-truth formats

### PiSerializeKit-Friendly Persistence Model

If the shared document is implemented with PiSerializeKit-backed state classes, the persisted model should deliberately prefer types that PiSerializeKit already handles well at runtime and in the annotation processor.

Recommended persisted field shapes:

- `version: int`
- `mode: PatternEditorMode`
- `gridSize: int`
- `patternRows: List<List<Integer>>`
- `displayPatternRows: Optional<List<List<Integer>>>`
- `reagentRows: List<String>`
- `reagentDefinitions: Map<String, ReagentDefinitionState>`
- `metadata: PatternMetadataState`

Important constraints from verified PiSerializeKit behavior:

- do not model reagent keys as `Character`
  - PiSerializeKit does not provide a built-in `Character` serializer
  - use single-character `String` keys instead
- do not rely on primitive-array support for persisted Pi models
  - PiSerializeKit clearly supports composite `List` / `Set` / `Map` / `Optional`
  - its public `arrayOf(...)` path is proven on reference arrays, not on primitive arrays like `int[]`
  - therefore the Pi-backed persistence layer should use `List<List<Integer>>`, then convert to public `int[][]` at import/export boundaries
- prefer `Optional<T>` and empty collections instead of `null`
- prefer `ResourceLocation` plus small enums over stuffing KJS wrapper classes directly into the Pi document

### Concrete PiSerializeKit Mapping Rules

To keep the editor state processor-friendly and low-boilerplate:

- wrapper-backed ids in the persisted editor model should normally be stored as `ResourceLocation`
- item-or-tag references should be stored as a nested sync model such as:
  - `kind: ItemOrTagKind`
  - `id: ResourceLocation`
- reagent definitions should use `Map<String, ReagentDefinitionState>` where the map key must be length `1`
- mode-specific metadata should be modeled as nested `@PiSyncModel` classes where that reduces custom serializer work

This keeps PiSerializeKit on the path where it is strongest:

- built-in scalars
- enums
- lists
- maps
- optionals
- nested sync models

Instead of pushing it toward avoidable custom codec work for every editor field.

### Editor Surfaces

Two editor surfaces are approved:

1. in-game editor
2. `toolkit-box-page` editor/generator

These are parallel authoring surfaces, not temporary stopgaps for each other.

### In-Game Editor

The in-game editor is the primary shape-authoring surface because it removes the worst part of the workflow:

- manually imagining a pattern
- hand-writing coordinates or raw nested arrays
- then discovering the shape is wrong

Chosen first-iteration access model:

- open through a client-side authoring entry point
- use a command-first workflow for the first version
- optional utility item or guidebook hook can be added later

The first version should not be bound to:

- a machine screen
- a block entity
- a server-side menu contract

Reason:

- tile support is explicitly out of scope
- the editor is for content authors, not gameplay progression
- MnaJS currently lacks existing client editor infrastructure

### Toolkit Editor

The toolkit editor remains valuable for:

- out-of-game authoring
- copy/paste workflows
- documentation
- versioned collaboration
- quick experimentation outside a live client

The existing runescribing editor structure in `toolkit-box-page` is a good architectural reference:

- split canvas/control layout
- explicit composable for editor state
- dedicated output formatting layer

### Editor Modes

#### Ritual Mode

Ritual mode should support:

- odd square sizes
- numeric `pattern`
- optional `displayPattern`
- reagent overlay rows
- typed reagent definition mapping
- chain DSL export

Ritual mode is a layered editor, not just a single grid.

#### Manaweave Mode

Manaweave mode should support:

- fixed 11x11 numeric grid unless schema later proves otherwise
- direct cell painting and erasing
- numeric value brush or palette
- transform actions such as rotate and mirror
- readable `int[][]` export for the public recipe API

### Editor Interaction Requirements

Minimum editor capabilities:

- direct paint and erase
- visible grid with immediate feedback
- current brush/value visibility
- selection support
- rotate and mirror
- clear and fill operations
- import existing payloads
- export readable payloads
- undo/redo or at least reversible local edits

High-value follow-up capabilities:

- symmetry-aware editing
- stamp or template workflows
- clipboard preset exchange
- reusable local pattern library

### Entry, Persistence, And Export

The first in-game editor should be client-local first.

Primary outputs:

- clipboard export
- readable KJS chain DSL export
- readable JSON/editor-document export

Optional outputs:

- explicit local file export for author-controlled storage
- local autosave cache for session recovery

The editor should not silently mutate KubeJS scripts on disk.

Design reasons:

- scripts are source files, not transient UI state
- explicit export keeps authors in control
- this avoids accidental divergence between the runtime editor and the repo

### In-Game Technical Direction

Chosen first-iteration architecture:

- pure client `Screen`-style editor
- client-local state container
- no required `AbstractContainerMenu`
- no required packet path for version one

Optional later additions:

- server-assisted export/import
- collaborative sync
- registry-backed packet queries

If packets become necessary later, they should use typed payload DTOs and preferably PiSerializeKit-backed serializers.

### Pibrary Decision

Pibrary screen-session patterns are interesting, but they are not the chosen dependency for the first iteration.

Reason:

- current project properties do not configure Pibrary
- MnaJS has no existing Pibrary UI baseline
- this editor does not need a session framework just to ship version one

Future direction:

- if client UI work expands substantially later, Pibrary can be reconsidered
- that is not a blocker for the first editor milestone

## PiSerializeKit Role In This Design

PiSerializeKit is approved for supporting infrastructure, not for driving the authoring experience.

### Verified Capability Stack

PiSerializeKit does more than provide a runtime serializer registry.

Verified useful capabilities include:

- process-wide serializer registry through `PiSerializeRuntime`
- stable typed lookup and author-facing diagnostics through `PiSerializeServices`
- scoped runtime overrides through `PiSerializeServices.withScope(...)`
- built-in serializers for:
  - primitives and boxed numbers
  - `String`
  - `UUID`
  - `ResourceLocation`
  - `CompoundTag`
  - `BlockPos`
  - `Vec3`
  - `ItemStack`
- composite serializer builders for:
  - arrays of supported reference types
  - `Optional`
  - `List`
  - `Set`
  - `Map`
- compile-time annotation processing through `PiSyncModelProcessor`
- generated schema classes, packet classes, provider classes, and `META-INF/services` registration
- field-local serializer injection through `@PiField(serializer = ...)`
- schema and packet migration hooks
- nested sync-model serialization through generated bindings and `PiSchemaSerializers.forState(...)`

So for MnaJS, PiSerializeKit is not just a parser helper.

It is a realistic way to reduce boilerplate for:

- editor document persistence
- clipboard or cache envelopes
- future editor packets
- small generated state graphs

### Good Uses In MnaJS

- typed editor document serialization
- generated bindings for local editor/cache state
- import/export envelope codecs
- wrapper normalization around `ResourceLocation`-backed ids
- future packet DTOs if the editor later gains networked features
- migration-safe local editor save formats if the document evolves over time

### Concrete MnaJS Integration Rules

If MnaJS uses PiSerializeKit for editor work, it should do so with the following concrete rules:

1. Use `@PiSyncModel` for persisted editor-state classes, not for the public KJS wrapper surface.
2. Keep Pi-backed editor-state fields on Pi-friendly shapes:
   - `List<List<Integer>>` instead of `int[][]`
   - `Optional<List<List<Integer>>>` instead of nullable secondary layers
   - `Map<String, ReagentDefinitionState>` instead of `Map<Character, ...>`
   - `ResourceLocation` instead of direct KJS wrapper ids where possible
3. Add `@PiField(serializer = ...)` providers only when a wrapper or exotic type must cross the Pi boundary.
4. Convert Pi-backed document state to public KJS/export forms in explicit export services.
5. If editor networking is added later, use `@PiPacket` for transport DTOs rather than hand-writing packet decode boilerplate.

### Build And Wiring Requirements

Using only the runtime jar is not enough if MnaJS wants generated schemas or packets.

Once MnaJS starts declaring Pi annotations such as `@PiSyncModel` or `@PiPacket`, implementation must also:

- add PiSerializeKit to the annotation-processor path
- verify generated provider files land on the build output classpath
- install serializer runtime in a deliberate bootstrap path if custom serializers are introduced

Current project implication:

- `project.toml` already enables the PiSerializeKit dependency version
- current template-config also exposes an `annotation_processor` dependency bucket, so processor wiring can follow the project's existing configuration system instead of becoming a one-off Gradle hack
- `build.gradle` currently exposes PiSerializeKit as `implementation`
- annotation-processor wiring for PiSerializeKit still needs to be added intentionally before editor-state generation begins

Bad uses:

- pretending serialization solves shape authoring
- mixing packet concerns into the first client-local editor without need
- replacing explicit editor state models with generic untyped payload maps

## JS API Rules

### Rule 1: One JS-Visible Method Name Uses One Type Family

Examples:

- good: `output(MnaItemId value)`
- good: `outputStack(ItemStack value)`
- bad: `output(String)`, `output(Item)`, `output(ItemStack)`, `output(Object)` all public

### Rule 2: Advanced Stack Paths Must Be Renamed, Not Overloaded

Where schema truly accepts stack-rich output:

- `output(MnaItemId)`
- `outputStack(ItemStack)`

This keeps the common path predictable.

### Rule 3: Id-Bearing Fields Must Not Stay Bare Strings

This explicitly applies to:

- `lootTable`
- `representationItem`
- `createsItem`
- `outputItem`
- item/block recipe slots

### Rule 4: Raw Compatibility Overloads Are Hidden Or Removed

If raw overloads remain for Java-side compatibility, they must be:

- hidden from JS
- treated as secondary implementation details

## Recipe-Specific Contracts

### Arcane Furnace

- `input(MnaItemId)`
- `output(MnaItemId)`

### Crushing

- `input(MnaItemId)`
- `output(MnaItemId)`

### Rune Forging

- `pattern(MnaItemId)`
- `material(MnaItemId)`
- `output(MnaItemId)`

### Rune Scribing

- `output(MnaItemId)`

### Fume Filter

- `item(MnaItemOrTag)`
- optional alias names only if they keep the exact same type family

### Eldrin Altar And Manaweaving Altar

- input families move to `MnaItemOrTag`
- common output path uses `output(MnaItemId)`
- advanced stack output, if retained, stays on a distinct name such as `outputStack(ItemStack)`

### Transmutation

Chosen public contract:

- `targetBlock(MnaBlockId)`
- `replaceBlock(MnaBlockId)`
- `lootTable(MnaLootTableId)`
- `representationItem(MnaItemId)`

Validation contract:

- `targetBlock` required
- exactly one of `replaceBlock` or `lootTable` path must be configured
- `lootTable` path requires `representationItem`

### Ritual

Chosen public contract:

- `pattern(int[][])`
- `patternRows(String...)`
- `displayPattern(int[][])`
- `displayPatternRows(String...)`
- `reagents(String...)`
- `reagentRows(String...)`
- `reagent(char, MnaItemOrTag)`
- `reagent(char, MnaItemOrTag, boolean optional, boolean consume)`
- `dynamicReagent(char, MnaItemOrTag)`
- `dynamicSourceReagent(char, MnaItemOrTag)`
- `manualReturnReagent(char, MnaItemOrTag)`
- `manaweavePatterns(MnaManaweavePatternId...)`
- `addManaweavePattern(MnaManaweavePatternId)`
- `outputItem(MnaItemId)`

Ritual validation contract:

- pattern required
- reagents required
- output item required
- reagent key definitions required
- reagent rows must match pattern footprint
- reagent symbols used in rows must all be defined
- required-field errors must fail loudly and specifically

### Manaweaving Pattern

Chosen public contract:

- `pattern(int[][])`
- optional readable row import/export methods such as `patternRows(String...)`

Storage contract:

- public authoring uses `int[][]`
- no JS-visible `byte[][]`

The row-oriented methods are import/export convenience only.

They are not the recommended primary authoring path.

## Probe, TypeId, And Snippet Contract

Probe must cover:

- all wrapper classes above
- item/block/item-or-tag/loot-table/texture surfaces
- MNA id families
- typed texture completion in the same spirit as PassiveSTJS

Snippets should support editor-generated workflows, not helper mythology.

Approved snippet targets:

- ritual chain DSL skeletons
- typed reagent declarations
- readable `patternRows(...)` and `reagentRows(...)` blocks
- manaweave `int[][]` block scaffolds
- texture-facing snippet support

Rejected snippet target:

- large geometry-helper APIs that pretend manual line scripting is the main authoring route

## Toolkit Contract

`toolkit-box-page` must be updated in the same batch as the KJS stabilization work.

### Ritual Generator

It must emit:

- the approved chain DSL
- typed id surfaces where applicable
- exports consistent with the runtime contract

### Pattern Editor

It must:

- support the same pattern document model as the in-game editor
- support ritual and manaweave modes, or at minimum support manaweave first with ritual planned immediately after
- export readable KJS and JSON forms
- be linked from both locale index pages

### Shared Export Contract

The in-game editor and the toolkit editor should converge on the same export flavors:

- readable KJS chain DSL
- readable editor JSON/document payload
- copy-paste-friendly row/int-grid form

## Approval Gate

Implementation after this point should be judged against this design, not against ad-hoc local experiments.

Reject immediately if a proposed change reintroduces any of the following:

- JS-visible `Object`
- loose string ids where wrappers are required
- new ambiguous overloads
- JS-visible `byte[][]`
- helper-led pattern authoring as the main solution
- toolkit output that differs from the approved chain DSL
- untyped `lootTable` or `representationItem`
