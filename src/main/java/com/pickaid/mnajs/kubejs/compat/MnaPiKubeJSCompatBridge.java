package com.pickaid.mnajs.kubejs.compat;

import org.pickaid.pikubejscompat.PiKubeJSCompat;

public final class MnaPiKubeJSCompatBridge {
    public static final String MODULE_ID = "mnajs:default";

    private MnaPiKubeJSCompatBridge() {
    }

    public static void install() {
        PiKubeJSCompat.registerModule(MODULE_ID, new MnaPiKubeJSCompatModule(
                MnaPiKubeJSCompatIdCandidates::spellEffectIds,
                MnaPiKubeJSCompatIdCandidates::soundIds
        ));
    }
}
