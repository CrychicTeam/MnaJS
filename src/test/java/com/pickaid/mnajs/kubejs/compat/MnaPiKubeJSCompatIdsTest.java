package com.pickaid.mnajs.kubejs.compat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.google.gson.JsonParser;
import com.pickaid.mnajs.kubejs.id.MnaCastingResourceId;
import com.pickaid.mnajs.kubejs.id.MnaConstructTaskId;
import com.pickaid.mnajs.kubejs.id.MnaFactionId;
import com.pickaid.mnajs.kubejs.id.MnaLootTableId;
import com.pickaid.mnajs.kubejs.id.MnaModifierId;
import com.pickaid.mnajs.kubejs.id.MnaMobEffectId;
import com.pickaid.mnajs.kubejs.id.MnaProgressionEventId;
import com.pickaid.mnajs.kubejs.id.MnaRitualEffectId;
import com.pickaid.mnajs.kubejs.id.MnaShapeId;
import com.pickaid.mnajs.kubejs.id.MnaSpellEffectId;
import com.pickaid.mnajs.kubejs.id.MnaStructureId;
import com.pickaid.mnajs.kubejs.texture.MnaTexture;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;
import org.pickaid.pikubejscompat.api.id.PiCommonIdSpecs;
import org.pickaid.pikubejscompat.api.probe.PiProbeTypeSpec;
import org.pickaid.pikubejscompat.api.spec.PiIdSpec;

class MnaPiKubeJSCompatIdsTest {
    @Test
    void progressionEventIdSpecDerivesParserSerializerRecipeComponentAndProbeType() {
        PiIdSpec<MnaProgressionEventId> spec = MnaPiKubeJSCompatIds.progressionEvent(
                () -> List.of("mna:eldrin_power", "mna:ritual_complete")
        );

        MnaProgressionEventId parsed = spec.parser().apply("eldrin_power");

        assertEquals("mna:progression_event", spec.role());
        assertEquals(MnaProgressionEventId.class, spec.targetType());
        assertEquals(new MnaProgressionEventId(new ResourceLocation("mna", "eldrin_power")), parsed);
        assertEquals("mna:eldrin_power", spec.stringSerializer().apply(parsed));
        assertNotNull(spec.piSerializer());
        assertEquals("\"mna:eldrin_power\" | \"mna:ritual_complete\"",
                spec.toRecipeComponent().constructorDescription(null).build());
        assertEquals("\"mna:eldrin_power\" | \"mna:ritual_complete\"", PiProbeTypeSpec.fromId(spec).typeExpression());
    }

    @Test
    void factionIdSpecDerivesParserSerializerRecipeComponentAndProbeType() {
        PiIdSpec<MnaFactionId> spec = MnaPiKubeJSCompatIds.faction(
                () -> List.of("mna:council", "mna:demons")
        );

        MnaFactionId parsed = spec.parser().apply(JsonParser.parseString("{\"name\":\"council\"}"));

        assertEquals("mna:faction", spec.role());
        assertEquals(MnaFactionId.class, spec.targetType());
        assertEquals(new MnaFactionId(new ResourceLocation("mna", "council")), parsed);
        assertEquals("mna:council", spec.stringSerializer().apply(parsed));
        assertNotNull(spec.piSerializer());
        assertEquals("\"mna:council\" | \"mna:demons\"", spec.toRecipeComponent().constructorDescription(null).build());
        assertEquals("\"mna:council\" | \"mna:demons\"", PiProbeTypeSpec.fromId(spec).typeExpression());
    }

    @Test
    void castingResourceIdSpecDerivesParserSerializerRecipeComponentAndProbeType() {
        PiIdSpec<MnaCastingResourceId> spec = MnaPiKubeJSCompatIds.castingResource(
                () -> List.of("mna:mana", "mna:souls")
        );

        MnaCastingResourceId parsed = spec.parser().apply("mana");

        assertEquals("mna:casting_resource", spec.role());
        assertEquals(MnaCastingResourceId.class, spec.targetType());
        assertEquals(new MnaCastingResourceId(new ResourceLocation("mna", "mana")), parsed);
        assertEquals("mna:mana", spec.stringSerializer().apply(parsed));
        assertNotNull(spec.piSerializer());
        assertEquals("\"mna:mana\" | \"mna:souls\"", spec.toRecipeComponent().constructorDescription(null).build());
        assertEquals("\"mna:mana\" | \"mna:souls\"", PiProbeTypeSpec.fromId(spec).typeExpression());
    }

    @Test
    void mobEffectIdSpecDerivesParserSerializerRecipeComponentAndProbeType() {
        PiIdSpec<MnaMobEffectId> spec = MnaPiKubeJSCompatIds.mobEffect(
                () -> List.of("minecraft:speed", "mna:mana_burn")
        );

        MnaMobEffectId parsed = spec.parser().apply("speed");

        assertEquals("mna:mob_effect", spec.role());
        assertEquals(MnaMobEffectId.class, spec.targetType());
        assertEquals(new MnaMobEffectId(new ResourceLocation("minecraft", "speed")), parsed);
        assertEquals("minecraft:speed", spec.stringSerializer().apply(parsed));
        assertNotNull(spec.piSerializer());
        assertEquals("\"minecraft:speed\" | \"mna:mana_burn\"", spec.toRecipeComponent().constructorDescription(null).build());
        assertEquals("\"minecraft:speed\" | \"mna:mana_burn\"", PiProbeTypeSpec.fromId(spec).typeExpression());
    }

    @Test
    void ritualEffectIdSpecDerivesParserSerializerRecipeComponentAndProbeType() {
        PiIdSpec<MnaRitualEffectId> spec = MnaPiKubeJSCompatIds.ritualEffect(
                () -> List.of("mna:altar", "mna:moonwell")
        );

        MnaRitualEffectId parsed = spec.parser().apply("altar");

        assertEquals("mna:ritual_effect", spec.role());
        assertEquals(MnaRitualEffectId.class, spec.targetType());
        assertEquals(new MnaRitualEffectId(new ResourceLocation("mna", "altar")), parsed);
        assertEquals("mna:altar", spec.stringSerializer().apply(parsed));
        assertNotNull(spec.piSerializer());
        assertEquals("\"mna:altar\" | \"mna:moonwell\"", spec.toRecipeComponent().constructorDescription(null).build());
        assertEquals("\"mna:altar\" | \"mna:moonwell\"", PiProbeTypeSpec.fromId(spec).typeExpression());
    }

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
    void shapeIdSpecDerivesParserSerializerRecipeComponentAndProbeType() {
        PiIdSpec<MnaShapeId> spec = MnaPiKubeJSCompatIds.shape(
                () -> List.of("mna:self", "mna:beam")
        );

        MnaShapeId parsed = spec.parser().apply("beam");

        assertEquals("mna:shape", spec.role());
        assertEquals(MnaShapeId.class, spec.targetType());
        assertEquals(new MnaShapeId(new ResourceLocation("mna", "beam")), parsed);
        assertEquals("mna:beam", spec.stringSerializer().apply(parsed));
        assertNotNull(spec.piSerializer());
        assertEquals("\"mna:self\" | \"mna:beam\"", spec.toRecipeComponent().constructorDescription(null).build());
        assertEquals("\"mna:self\" | \"mna:beam\"", PiProbeTypeSpec.fromId(spec).typeExpression());
    }

    @Test
    void modifierIdSpecDerivesParserSerializerRecipeComponentAndProbeType() {
        PiIdSpec<MnaModifierId> spec = MnaPiKubeJSCompatIds.modifier(
                () -> List.of("mna:damage", "mna:range")
        );

        MnaModifierId parsed = spec.parser().apply("range");

        assertEquals("mna:modifier", spec.role());
        assertEquals(MnaModifierId.class, spec.targetType());
        assertEquals(new MnaModifierId(new ResourceLocation("mna", "range")), parsed);
        assertEquals("mna:range", spec.stringSerializer().apply(parsed));
        assertNotNull(spec.piSerializer());
        assertEquals("\"mna:damage\" | \"mna:range\"", spec.toRecipeComponent().constructorDescription(null).build());
        assertEquals("\"mna:damage\" | \"mna:range\"", PiProbeTypeSpec.fromId(spec).typeExpression());
    }

    @Test
    void constructTaskIdSpecDerivesParserSerializerRecipeComponentAndProbeType() {
        PiIdSpec<MnaConstructTaskId> spec = MnaPiKubeJSCompatIds.constructTask(
                () -> List.of("mna:chop_tree", "mna:haul")
        );

        MnaConstructTaskId parsed = spec.parser().apply("haul");

        assertEquals("mna:construct_task", spec.role());
        assertEquals(MnaConstructTaskId.class, spec.targetType());
        assertEquals(new MnaConstructTaskId(new ResourceLocation("mna", "haul")), parsed);
        assertEquals("mna:haul", spec.stringSerializer().apply(parsed));
        assertNotNull(spec.piSerializer());
        assertEquals("\"mna:chop_tree\" | \"mna:haul\"", spec.toRecipeComponent().constructorDescription(null).build());
        assertEquals("\"mna:chop_tree\" | \"mna:haul\"", PiProbeTypeSpec.fromId(spec).typeExpression());
    }

    @Test
    void lootTableIdSpecKeepsMnaWrapperAndUsesSpecialProbeType() {
        PiIdSpec<MnaLootTableId> spec = MnaPiKubeJSCompatIds.lootTable(
                () -> List.of("minecraft:chests/simple_dungeon")
        );

        MnaLootTableId parsed = spec.parser().apply(JsonParser.parseString("{\"id\":\"chests/simple_dungeon\"}"));

        assertEquals("mna:loot_table", spec.role());
        assertEquals(MnaLootTableId.class, spec.targetType());
        assertEquals(new MnaLootTableId(new ResourceLocation("minecraft", "chests/simple_dungeon")), parsed);
        assertEquals("minecraft:chests/simple_dungeon", spec.stringSerializer().apply(parsed));
        assertNotNull(spec.piSerializer());
        assertEquals("Special.LootTable", spec.toRecipeComponent().constructorDescription(null).build());
        assertEquals("Special.LootTable", PiProbeTypeSpec.fromId(spec).typeExpression());
    }

    @Test
    void structureIdSpecDerivesParserSerializerRecipeComponentAndProbeType() {
        PiIdSpec<MnaStructureId> spec = MnaPiKubeJSCompatIds.structure(
                () -> List.of("mna:tower", "mna:well")
        );

        MnaStructureId parsed = spec.parser().apply("well");

        assertEquals("mna:structure", spec.role());
        assertEquals(MnaStructureId.class, spec.targetType());
        assertEquals(new MnaStructureId(new ResourceLocation("mna", "well")), parsed);
        assertEquals("mna:well", spec.stringSerializer().apply(parsed));
        assertNotNull(spec.piSerializer());
        assertEquals("\"mna:tower\" | \"mna:well\"", spec.toRecipeComponent().constructorDescription(null).build());
        assertEquals("\"mna:tower\" | \"mna:well\"", PiProbeTypeSpec.fromId(spec).typeExpression());
    }

    @Test
    void textureSpecKeepsMnaWrapperAndUsesSpecialProbeType() {
        PiIdSpec<MnaTexture> spec = MnaPiKubeJSCompatIds.texture(
                () -> List.of("mna:textures/gui/guide_book.png")
        );

        MnaTexture parsed = spec.parser().apply(JsonParser.parseString("{\"name\":\"textures/gui/guide_book.png\"}"));

        assertEquals("mna:texture", spec.role());
        assertEquals(MnaTexture.class, spec.targetType());
        assertEquals(new MnaTexture(new ResourceLocation("mna", "textures/gui/guide_book.png")), parsed);
        assertEquals("mna:textures/gui/guide_book.png", spec.stringSerializer().apply(parsed));
        assertNotNull(spec.piSerializer());
        assertEquals("Special.Texture", spec.toRecipeComponent().constructorDescription(null).build());
        assertEquals("Special.Texture", PiProbeTypeSpec.fromId(spec).typeExpression());
    }

    @Test
    void spellEffectSpecKeepsMnaParserInputShapes() {
        PiIdSpec<MnaSpellEffectId> spec = MnaPiKubeJSCompatIds.spellEffect(List::of);

        assertEquals(new MnaSpellEffectId(new ResourceLocation("mna", "frost")),
                spec.toRecipeComponent().read(null, JsonParser.parseString("{\"id\":\"frost\"}")));
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
