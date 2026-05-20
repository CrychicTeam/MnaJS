package com.pickaid.mnajs.kubejs.compat;

import java.util.Objects;
import org.pickaid.pikubejscompat.api.bootstrap.PiKubeJSCompatBootstrap;
import org.pickaid.pikubejscompat.api.bootstrap.PiKubeJSCompatModule;

public final class MnaPiKubeJSCompatModule implements PiKubeJSCompatModule {
    private final MnaPiKubeJSCompatCandidates candidates;

    public MnaPiKubeJSCompatModule(MnaPiKubeJSCompatCandidates candidates) {
        this.candidates = Objects.requireNonNull(candidates, "candidates");
    }

    @Override
    public void contribute(PiKubeJSCompatBootstrap bootstrap) {
        Objects.requireNonNull(bootstrap, "bootstrap");
        bootstrap.addId(MnaPiKubeJSCompatIds.progressionEvent(candidates.progressionEvents()));
        bootstrap.addId(MnaPiKubeJSCompatIds.faction(candidates.factions()));
        bootstrap.addId(MnaPiKubeJSCompatIds.castingResource(candidates.castingResources()));
        bootstrap.addId(MnaPiKubeJSCompatIds.mobEffect(candidates.mobEffects()));
        bootstrap.addId(MnaPiKubeJSCompatIds.ritualEffect(candidates.ritualEffects()));
        bootstrap.addId(MnaPiKubeJSCompatIds.spellEffect(candidates.spellEffects()));
        bootstrap.addId(MnaPiKubeJSCompatIds.shape(candidates.shapes()));
        bootstrap.addId(MnaPiKubeJSCompatIds.modifier(candidates.modifiers()));
        bootstrap.addId(MnaPiKubeJSCompatIds.constructTask(candidates.constructTasks()));
        bootstrap.addId(MnaPiKubeJSCompatIds.lootTable(candidates.lootTables()));
        bootstrap.addId(MnaPiKubeJSCompatIds.structure(candidates.structures()));
        bootstrap.addId(MnaPiKubeJSCompatIds.texture(candidates.textures()));
        bootstrap.addId(MnaPiKubeJSCompatIds.sound(candidates.sounds()));
    }
}
