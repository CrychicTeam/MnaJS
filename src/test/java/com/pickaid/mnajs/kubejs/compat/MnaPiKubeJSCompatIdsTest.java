package com.pickaid.mnajs.kubejs.compat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.pickaid.mnajs.kubejs.id.MnaSpellEffectId;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;
import org.pickaid.pikubejscompat.api.id.PiCommonIdSpecs;
import org.pickaid.pikubejscompat.api.probe.PiProbeTypeSpec;
import org.pickaid.pikubejscompat.api.spec.PiIdSpec;

class MnaPiKubeJSCompatIdsTest {
    @Test
    void spellEffectIdSpecDerivesParserSerializerRecipeComponentAndProbeType() {
        PiIdSpec<MnaSpellEffectId> spec = MnaPiKubeJSCompatIds.spellEffect(
                () -> List.of("mna:fireball", "mna:frost")
        );

        MnaSpellEffectId parsed = spec.parser().apply("fireball");

        assertEquals("mna:spell_effect", spec.role());
        assertEquals(MnaSpellEffectId.class, spec.targetType());
        assertEquals(new MnaSpellEffectId(new ResourceLocation("mna", "fireball")), parsed);
        assertEquals("mna:fireball", spec.stringSerializer().apply(parsed));
        assertNotNull(spec.piSerializer());
        assertEquals("\"mna:fireball\" | \"mna:frost\"", spec.toRecipeComponent().constructorDescription(null).build());
        assertEquals("\"mna:fireball\" | \"mna:frost\"", PiProbeTypeSpec.fromId(spec).typeExpression());
    }

    @Test
    void soundSpecReusesCommonMinecraftSoundId() {
        PiIdSpec<ResourceLocation> spec = MnaPiKubeJSCompatIds.sound(
                () -> List.of("minecraft:block.note_block.harp", "mna:moon_chime")
        );

        ResourceLocation parsed = spec.parser().apply("block.note_block.harp");

        assertEquals(PiCommonIdSpecs.SOUND, spec.role());
        assertEquals(ResourceLocation.class, spec.targetType());
        assertFalse(spec.deriveTypeWrapper());
        assertEquals(new ResourceLocation("minecraft", "block.note_block.harp"), parsed);
        assertEquals("minecraft:block.note_block.harp", spec.stringSerializer().apply(parsed));
        assertEquals("\"minecraft:block.note_block.harp\" | \"mna:moon_chime\"",
                spec.toRecipeComponent().constructorDescription(null).build());
    }
}
