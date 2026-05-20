package com.pickaid.mnajs.kubejs.compat;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public record MnaPiKubeJSCompatCandidates(
        Supplier<List<String>> progressionEvents,
        Supplier<List<String>> factions,
        Supplier<List<String>> castingResources,
        Supplier<List<String>> mobEffects,
        Supplier<List<String>> ritualEffects,
        Supplier<List<String>> spellEffects,
        Supplier<List<String>> shapes,
        Supplier<List<String>> modifiers,
        Supplier<List<String>> constructTasks,
        Supplier<List<String>> lootTables,
        Supplier<List<String>> structures,
        Supplier<List<String>> textures,
        Supplier<List<String>> sounds
) {
    public MnaPiKubeJSCompatCandidates {
        progressionEvents = Objects.requireNonNull(progressionEvents, "progressionEvents");
        factions = Objects.requireNonNull(factions, "factions");
        castingResources = Objects.requireNonNull(castingResources, "castingResources");
        mobEffects = Objects.requireNonNull(mobEffects, "mobEffects");
        ritualEffects = Objects.requireNonNull(ritualEffects, "ritualEffects");
        spellEffects = Objects.requireNonNull(spellEffects, "spellEffects");
        shapes = Objects.requireNonNull(shapes, "shapes");
        modifiers = Objects.requireNonNull(modifiers, "modifiers");
        constructTasks = Objects.requireNonNull(constructTasks, "constructTasks");
        lootTables = Objects.requireNonNull(lootTables, "lootTables");
        structures = Objects.requireNonNull(structures, "structures");
        textures = Objects.requireNonNull(textures, "textures");
        sounds = Objects.requireNonNull(sounds, "sounds");
    }
}
