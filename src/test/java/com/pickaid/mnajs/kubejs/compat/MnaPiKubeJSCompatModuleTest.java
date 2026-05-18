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
                () -> List.of("mna:council"),
                () -> List.of("mna:altar"),
                () -> List.of("mna:fireball"),
                () -> List.of("mna:self"),
                () -> List.of("mna:range"),
                () -> List.of("minecraft:chests/simple_dungeon"),
                () -> List.of("mna:textures/gui/guide_book.png"),
                () -> List.of("minecraft:block.note_block.harp")
        ).contribute(bootstrap);

        assertEquals(List.of(
                        MnaPiKubeJSCompatIds.FACTION,
                        MnaPiKubeJSCompatIds.RITUAL_EFFECT,
                        MnaPiKubeJSCompatIds.SPELL_EFFECT,
                        MnaPiKubeJSCompatIds.SHAPE,
                        MnaPiKubeJSCompatIds.MODIFIER,
                        MnaPiKubeJSCompatIds.LOOT_TABLE,
                        MnaPiKubeJSCompatIds.TEXTURE,
                        PiCommonIdSpecs.SOUND
                ),
                bootstrap.ids().stream().map(PiIdSpec::role).toList());
        assertEquals("\"mna:council\"",
                bootstrap.ids().get(0).toRecipeComponent().constructorDescription(null).build());
        assertEquals("\"mna:altar\"",
                bootstrap.ids().get(1).toRecipeComponent().constructorDescription(null).build());
        assertEquals("\"mna:fireball\"",
                bootstrap.ids().get(2).toRecipeComponent().constructorDescription(null).build());
        assertEquals("\"mna:fireball\"",
                PiProbeTypeSpec.fromId(bootstrap.ids().get(2)).typeExpression());
        assertEquals("\"mna:self\"",
                bootstrap.ids().get(3).toRecipeComponent().constructorDescription(null).build());
        assertEquals("\"mna:range\"",
                bootstrap.ids().get(4).toRecipeComponent().constructorDescription(null).build());
        assertEquals("Special.LootTable",
                bootstrap.ids().get(5).toRecipeComponent().constructorDescription(null).build());
        assertEquals("Special.Texture",
                bootstrap.ids().get(6).toRecipeComponent().constructorDescription(null).build());
        assertEquals("\"minecraft:block.note_block.harp\"",
                bootstrap.ids().get(7).toRecipeComponent().constructorDescription(null).build());
    }
}
