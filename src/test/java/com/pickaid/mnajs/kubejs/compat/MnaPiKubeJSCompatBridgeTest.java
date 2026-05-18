package com.pickaid.mnajs.kubejs.compat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.pickaid.pikubejscompat.PiKubeJSCompat;
import org.pickaid.pikubejscompat.api.spec.PiIdSpec;

class MnaPiKubeJSCompatBridgeTest {
    @Test
    void installRegistersMnaModuleOnce() {
        MnaPiKubeJSCompatBridge.install();
        MnaPiKubeJSCompatBridge.install();

        long moduleCount = PiKubeJSCompat.modules().stream()
                .filter(MnaPiKubeJSCompatModule.class::isInstance)
                .count();
        List<String> idRoles = PiKubeJSCompat.createBootstrap().ids().stream()
                .map(PiIdSpec::role)
                .toList();

        assertEquals(1, moduleCount);
        assertEquals(List.of(
                MnaPiKubeJSCompatIds.FACTION,
                MnaPiKubeJSCompatIds.RITUAL_EFFECT,
                MnaPiKubeJSCompatIds.SPELL_EFFECT,
                MnaPiKubeJSCompatIds.SHAPE,
                MnaPiKubeJSCompatIds.MODIFIER,
                "minecraft:sound"
        ), idRoles);
    }
}
