<div align="center"><img height="200" src="https://docs.mihono.cn/logo.png" width="200"/></div>

# MnaJS

KubeJS support surface for Mana and Artifice on Forge 1.20.1.

This repository exposes typed-id driven KubeJS registration, recipe builders, runtime events, state helpers, pattern utilities, and ProbeJS completion support for M&A content.

## Docs

- [Documentation Overview](docs/introduction.mdx)
- [KubeJS Support Reference](docs/reference/mnajs-kubejs/overview.mdx)
- [Bindings](docs/reference/mnajs-kubejs/bindings.mdx)
- [Registries](docs/reference/mnajs-kubejs/registries.mdx)
- [Recipes](docs/reference/mnajs-kubejs/recipes.mdx)
- [Events](docs/reference/mnajs-kubejs/events.mdx)
- [Helpers And Probe](docs/reference/mnajs-kubejs/helpers-and-probe.mdx)

## Design Docs

- [Surface Stabilization Brainstorming](design/superpowers/plans/2026-04-24-mnajs-kjs-surface-stabilization-brainstorming-and-implementation.md)
- [Surface Stabilization Design](design/superpowers/specs/2026-04-24-mnajs-kjs-surface-stabilization-design.md)

## Validation Status

Last reviewed: 2026-04-26

Verified in this repo:

- `./gradlew compileJava`
- `./gradlew runServer`
- example startup registration for `kubejs:moonsteel`
- example construct part registration for `kubejs:moonsteel_basic_head`

## Example Scripts

- Startup examples: `run/client/kubejs/startup_scripts/example.js`
- Server examples: `run/client/kubejs/server_scripts/example.js`
- Minimal ritual example: `run/server/kubejs/server_scripts/example.js`

## Documentation Scope

The reference docs aim to cover the current user-facing MnaJS support surface:

- startup registries
- startup and server events
- recipe chains and `event.custom()` helpers
- typed ids and script bindings
- player/world helpers
- ProbeJS completion wiring

Where a feature is intentionally limited, the docs say so explicitly instead of pretending the surface is deeper than it is.
