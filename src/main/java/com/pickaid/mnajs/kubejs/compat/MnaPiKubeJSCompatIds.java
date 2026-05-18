package com.pickaid.mnajs.kubejs.compat;

import com.pickaid.mnajs.kubejs.id.MnaFactionId;
import com.pickaid.mnajs.kubejs.id.MnaLootTableId;
import com.pickaid.mnajs.kubejs.id.MnaModifierId;
import com.pickaid.mnajs.kubejs.id.MnaRitualEffectId;
import com.pickaid.mnajs.kubejs.id.MnaShapeId;
import com.pickaid.mnajs.kubejs.id.MnaSpellEffectId;
import com.pickaid.mnajs.kubejs.id.MnaTypedIdPiSerializers;
import com.pickaid.mnajs.kubejs.texture.MnaTexture;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import org.pickaid.pikubejscompat.api.id.PiCommonIdSpecs;
import org.pickaid.pikubejscompat.api.spec.PiIdSpec;
import org.pickaid.piserializekit.api.convert.PiResourceLocationConverter;
import org.pickaid.piserializekit.api.convert.PiTypedValueConverters;

public final class MnaPiKubeJSCompatIds {
    public static final String FACTION = "mna:faction";
    public static final String RITUAL_EFFECT = "mna:ritual_effect";
    public static final String SPELL_EFFECT = "mna:spell_effect";
    public static final String SHAPE = "mna:shape";
    public static final String MODIFIER = "mna:modifier";
    public static final String LOOT_TABLE = "mna:loot_table";
    public static final String TEXTURE = "mna:texture";

    private MnaPiKubeJSCompatIds() {
    }

    public static PiIdSpec<MnaFactionId> faction(Supplier<List<String>> candidates) {
        return PiIdSpec.builder(FACTION, MnaFactionId.class)
                .converter("factionId", PiTypedValueConverters.resourceLocationBacked(
                        MnaFactionId.class,
                        MnaFactionId::of,
                        MnaFactionId::location,
                        PiResourceLocationConverter.builder().defaultNamespace("mna").build()
                ))
                .parser(MnaFactionId::parse)
                .piSerializer(MnaTypedIdPiSerializers.requireSerializer(MnaTypedIdPiSerializers.FACTION_ID))
                .candidates(candidates)
                .build();
    }

    public static PiIdSpec<MnaRitualEffectId> ritualEffect(Supplier<List<String>> candidates) {
        return PiIdSpec.builder(RITUAL_EFFECT, MnaRitualEffectId.class)
                .converter("ritualEffectId", PiTypedValueConverters.resourceLocationBacked(
                        MnaRitualEffectId.class,
                        MnaRitualEffectId::of,
                        MnaRitualEffectId::location,
                        PiResourceLocationConverter.builder().defaultNamespace("mna").build()
                ))
                .parser(MnaRitualEffectId::parse)
                .piSerializer(MnaTypedIdPiSerializers.requireSerializer(MnaTypedIdPiSerializers.RITUAL_EFFECT_ID))
                .candidates(candidates)
                .build();
    }

    public static PiIdSpec<MnaSpellEffectId> spellEffect(Supplier<List<String>> candidates) {
        return PiIdSpec.builder(SPELL_EFFECT, MnaSpellEffectId.class)
                .converter("spellEffectId", PiTypedValueConverters.resourceLocationBacked(
                        MnaSpellEffectId.class,
                        MnaSpellEffectId::of,
                        MnaSpellEffectId::location,
                        PiResourceLocationConverter.builder().defaultNamespace("mna").build()
                ))
                .parser(MnaSpellEffectId::parse)
                .piSerializer(MnaTypedIdPiSerializers.requireSerializer(MnaTypedIdPiSerializers.SPELL_EFFECT_ID))
                .candidates(candidates)
                .build();
    }

    public static PiIdSpec<MnaShapeId> shape(Supplier<List<String>> candidates) {
        return PiIdSpec.builder(SHAPE, MnaShapeId.class)
                .converter("shapeId", PiTypedValueConverters.resourceLocationBacked(
                        MnaShapeId.class,
                        MnaShapeId::of,
                        MnaShapeId::location,
                        PiResourceLocationConverter.builder().defaultNamespace("mna").build()
                ))
                .parser(MnaShapeId::parse)
                .piSerializer(MnaTypedIdPiSerializers.requireSerializer(MnaTypedIdPiSerializers.SHAPE_ID))
                .candidates(candidates)
                .build();
    }

    public static PiIdSpec<MnaModifierId> modifier(Supplier<List<String>> candidates) {
        return PiIdSpec.builder(MODIFIER, MnaModifierId.class)
                .converter("modifierId", PiTypedValueConverters.resourceLocationBacked(
                        MnaModifierId.class,
                        MnaModifierId::of,
                        MnaModifierId::location,
                        PiResourceLocationConverter.builder().defaultNamespace("mna").build()
                ))
                .parser(MnaModifierId::parse)
                .piSerializer(MnaTypedIdPiSerializers.requireSerializer(MnaTypedIdPiSerializers.MODIFIER_ID))
                .candidates(candidates)
                .build();
    }

    public static PiIdSpec<MnaLootTableId> lootTable(Supplier<List<String>> candidates) {
        return PiIdSpec.builder(LOOT_TABLE, MnaLootTableId.class)
                .converter("lootTableId", PiTypedValueConverters.resourceLocationBacked(
                        MnaLootTableId.class,
                        MnaLootTableId::of,
                        MnaLootTableId::location,
                        PiResourceLocationConverter.builder().defaultNamespace("minecraft").build()
                ))
                .parser(MnaLootTableId::parse)
                .piSerializer(MnaTypedIdPiSerializers.requireSerializer(MnaTypedIdPiSerializers.LOOT_TABLE_ID))
                .candidates(candidates)
                .specialTypeRef("Special.LootTable")
                .build();
    }

    public static PiIdSpec<MnaTexture> texture(Supplier<List<String>> candidates) {
        return PiIdSpec.builder(TEXTURE, MnaTexture.class)
                .converter("texture", PiTypedValueConverters.resourceLocationBacked(
                        MnaTexture.class,
                        MnaTexture::of,
                        MnaTexture::location,
                        PiResourceLocationConverter.builder().defaultNamespace("mna").build()
                ))
                .parser(MnaTexture::parse)
                .piSerializer(MnaTypedIdPiSerializers.requireSerializer(MnaTypedIdPiSerializers.TEXTURE))
                .candidates(candidates)
                .specialTypeRef("Special.Texture")
                .build();
    }

    public static PiIdSpec<ResourceLocation> sound(Supplier<List<String>> candidates) {
        return PiCommonIdSpecs.sound(candidates);
    }
}
