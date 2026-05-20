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

        new MnaPiKubeJSCompatModule(new MnaPiKubeJSCompatCandidates(
                () -> List.of("mna:eldrin_power"),
                () -> List.of("mna:council"),
                () -> List.of("mna:mana"),
                () -> List.of("minecraft:speed"),
                () -> List.of("mna:altar"),
                () -> List.of("mna:fireball"),
                () -> List.of("mna:self"),
                () -> List.of("mna:range"),
                () -> List.of("mna:haul"),
                () -> List.of("minecraft:chests/simple_dungeon"),
                () -> List.of("mna:tower"),
                () -> List.of("mna:textures/gui/guide_book.png"),
                () -> List.of("minecraft:block.note_block.harp")
        )).contribute(bootstrap);

        assertEquals(List.of(
                        MnaPiKubeJSCompatIds.PROGRESSION_EVENT,
                        MnaPiKubeJSCompatIds.FACTION,
                        MnaPiKubeJSCompatIds.CASTING_RESOURCE,
                        MnaPiKubeJSCompatIds.MOB_EFFECT,
                        MnaPiKubeJSCompatIds.RITUAL_EFFECT,
                        MnaPiKubeJSCompatIds.SPELL_EFFECT,
                        MnaPiKubeJSCompatIds.SHAPE,
                        MnaPiKubeJSCompatIds.MODIFIER,
                        MnaPiKubeJSCompatIds.CONSTRUCT_TASK,
                        MnaPiKubeJSCompatIds.LOOT_TABLE,
                        MnaPiKubeJSCompatIds.STRUCTURE,
                        MnaPiKubeJSCompatIds.TEXTURE,
                        PiCommonIdSpecs.SOUND
                ),
                bootstrap.ids().stream().map(PiIdSpec::role).toList());
        assertEquals("\"mna:eldrin_power\"",
                bootstrap.ids().get(0).toRecipeComponent().constructorDescription(null).build());
        assertEquals("\"mna:council\"",
                bootstrap.ids().get(1).toRecipeComponent().constructorDescription(null).build());
        assertEquals("\"mna:mana\"",
                bootstrap.ids().get(2).toRecipeComponent().constructorDescription(null).build());
        assertEquals("\"minecraft:speed\"",
                bootstrap.ids().get(3).toRecipeComponent().constructorDescription(null).build());
        assertEquals("\"mna:altar\"",
                bootstrap.ids().get(4).toRecipeComponent().constructorDescription(null).build());
        assertEquals("\"mna:fireball\"",
                bootstrap.ids().get(5).toRecipeComponent().constructorDescription(null).build());
        assertEquals("\"mna:fireball\"",
                PiProbeTypeSpec.fromId(bootstrap.ids().get(5)).typeExpression());
        assertEquals("\"mna:self\"",
                bootstrap.ids().get(6).toRecipeComponent().constructorDescription(null).build());
        assertEquals("\"mna:range\"",
                bootstrap.ids().get(7).toRecipeComponent().constructorDescription(null).build());
        assertEquals("\"mna:haul\"",
                bootstrap.ids().get(8).toRecipeComponent().constructorDescription(null).build());
        assertEquals("Special.LootTable",
                bootstrap.ids().get(9).toRecipeComponent().constructorDescription(null).build());
        assertEquals("\"mna:tower\"",
                bootstrap.ids().get(10).toRecipeComponent().constructorDescription(null).build());
        assertEquals("Special.Texture",
                bootstrap.ids().get(11).toRecipeComponent().constructorDescription(null).build());
        assertEquals("\"minecraft:block.note_block.harp\"",
                bootstrap.ids().get(12).toRecipeComponent().constructorDescription(null).build());
    }
}
