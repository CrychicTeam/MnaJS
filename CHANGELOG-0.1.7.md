# MnaJS 0.1.7

Released for Minecraft Forge 1.20.1.

## Highlights

- Expanded KubeJS support for Mana and Artifice startup registration, including custom modifiers, construct tasks, construct materials, construct parts, and deeper spell part builders.
- Added typed id wrappers and lookup support across advancements, casting resources, construct ids, sounds, structures, rituals, spell parts, loot tables, items, blocks, and textures.
- Improved recipe chains for rituals, manaweaving, progression, eldrin altar/fume, cache effects, transmutation, and multiblock JSON generation.
- Added modern ProbeJS support alongside ProbeJS Legacy, including typed id aliases, snippets, and generated Java surfaces.
- Added PiSerializeKit serializers for MnaJS typed ids.
- Added player/world helper surfaces for magic state, progression state, and world magic state.
- Added ModdedMC/Sinytra Wiki documentation under `docs/`, including Chinese translations under `.translated/zh_cn`.
- Enabled JEI runtime dependency for integration testing.

## Notes

- `construct_task` scripting currently supports metadata and reuse of existing Java AI tasks through `basedOn(...)` or `aiTask(...)`; it is not a JS-native AI authoring layer.
- `registerGuideBook` exposes the raw M&A GuideBook registry. Deep guidebook authoring remains JSON/tooling-driven.
- ProbeJS support is wired for modern ProbeJS and ProbeJS Legacy, but not every completion path has been manually checked.

## Validation

- `./gradlew build`
