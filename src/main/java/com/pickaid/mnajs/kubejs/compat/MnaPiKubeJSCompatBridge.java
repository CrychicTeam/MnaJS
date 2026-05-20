package com.pickaid.mnajs.kubejs.compat;

import org.pickaid.pikubejscompat.PiKubeJSCompat;

public final class MnaPiKubeJSCompatBridge {
    public static final String MODULE_ID = "mnajs:default";

    private MnaPiKubeJSCompatBridge() {
    }

    public static void install() {
        PiKubeJSCompat.registerModule(MODULE_ID, new MnaPiKubeJSCompatModule(new MnaPiKubeJSCompatCandidates(
                MnaPiKubeJSCompatIdCandidates::progressionEventIds,
                MnaPiKubeJSCompatIdCandidates::factionIds,
                MnaPiKubeJSCompatIdCandidates::castingResourceIds,
                MnaPiKubeJSCompatIdCandidates::mobEffectIds,
                MnaPiKubeJSCompatIdCandidates::ritualEffectIds,
                MnaPiKubeJSCompatIdCandidates::spellEffectIds,
                MnaPiKubeJSCompatIdCandidates::shapeIds,
                MnaPiKubeJSCompatIdCandidates::modifierIds,
                MnaPiKubeJSCompatIdCandidates::constructTaskIds,
                MnaPiKubeJSCompatIdCandidates::lootTableIds,
                MnaPiKubeJSCompatIdCandidates::structureIds,
                MnaPiKubeJSCompatIdCandidates::textureIds,
                MnaPiKubeJSCompatIdCandidates::soundIds
        )));
    }
}
