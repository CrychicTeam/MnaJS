package com.pickaid.mnajs.kubejs.compat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.pickaid.pikubejscompat.api.bootstrap.PiKubeJSCompatBootstrap;
import org.pickaid.pikubejscompat.api.id.PiCommonIdSpecs;
import org.pickaid.pikubejscompat.api.probe.PiProbeTypeSpec;
import org.pickaid.pikubejscompat.api.spec.PiIdSpec;

class MnaPiKubeJSCompatModuleTest {
    @Test
    void contributesTypedIdsToCompatBootstrap() {
        PiKubeJSCompatBootstrap bootstrap = new PiKubeJSCompatBootstrap();

        new MnaPiKubeJSCompatModule(
                () -> List.of("mna:fireball"),
                () -> List.of("minecraft:block.note_block.harp")
        ).contribute(bootstrap);

        assertEquals(List.of(MnaPiKubeJSCompatIds.SPELL_EFFECT, PiCommonIdSpecs.SOUND),
                bootstrap.ids().stream().map(PiIdSpec::role).toList());
        assertEquals("\"mna:fireball\"",
                bootstrap.ids().get(0).toRecipeComponent().constructorDescription(null).build());
        assertEquals("\"mna:fireball\"",
                PiProbeTypeSpec.fromId(bootstrap.ids().get(0)).typeExpression());
        assertEquals("\"minecraft:block.note_block.harp\"",
                bootstrap.ids().get(1).toRecipeComponent().constructorDescription(null).build());
    }
}
