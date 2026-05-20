package com.pickaid.mnajs.kubejs.compat;

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
import com.pickaid.mnajs.kubejs.id.MnaTypedIdPiSerializers;
import com.pickaid.mnajs.kubejs.texture.MnaTexture;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import org.pickaid.pikubejscompat.api.id.PiCommonIdSpecs;
import org.pickaid.pikubejscompat.api.spec.PiIdSpec;
import org.pickaid.piserializekit.api.convert.PiResourceLocationConverter;
import org.pickaid.piserializekit.api.convert.PiTypedValueConverters;
import org.pickaid.piserializekit.api.service.PiSerializer;

public final class MnaPiKubeJSCompatIds {
    public static final String PROGRESSION_EVENT = "mna:progression_event";
    public static final String FACTION = "mna:faction";
    public static final String CASTING_RESOURCE = "mna:casting_resource";
    public static final String MOB_EFFECT = "mna:mob_effect";
    public static final String RITUAL_EFFECT = "mna:ritual_effect";
    public static final String SPELL_EFFECT = "mna:spell_effect";
    public static final String SHAPE = "mna:shape";
    public static final String MODIFIER = "mna:modifier";
    public static final String CONSTRUCT_TASK = "mna:construct_task";
    public static final String LOOT_TABLE = "mna:loot_table";
    public static final String STRUCTURE = "mna:structure";
    public static final String TEXTURE = "mna:texture";

    private MnaPiKubeJSCompatIds() {
    }

    public static PiIdSpec<MnaProgressionEventId> progressionEvent(Supplier<List<String>> candidates) {
        return resourceId(
                PROGRESSION_EVENT,
                MnaProgressionEventId.class,
                "progressionEventId",
                "mna",
                MnaProgressionEventId::of,
                MnaProgressionEventId::location,
                MnaProgressionEventId::parse,
                MnaTypedIdPiSerializers.requireSerializer(MnaTypedIdPiSerializers.PROGRESSION_EVENT_ID),
                candidates
        );
    }

    public static PiIdSpec<MnaFactionId> faction(Supplier<List<String>> candidates) {
        return resourceId(
                FACTION,
                MnaFactionId.class,
                "factionId",
                "mna",
                MnaFactionId::of,
                MnaFactionId::location,
                MnaFactionId::parse,
                MnaTypedIdPiSerializers.requireSerializer(MnaTypedIdPiSerializers.FACTION_ID),
                candidates
        );
    }

    public static PiIdSpec<MnaCastingResourceId> castingResource(Supplier<List<String>> candidates) {
        return resourceId(
                CASTING_RESOURCE,
                MnaCastingResourceId.class,
                "castingResourceId",
                "mna",
                MnaCastingResourceId::of,
                MnaCastingResourceId::location,
                MnaCastingResourceId::parse,
                MnaTypedIdPiSerializers.requireSerializer(MnaTypedIdPiSerializers.CASTING_RESOURCE_ID),
                candidates
        );
    }

    public static PiIdSpec<MnaMobEffectId> mobEffect(Supplier<List<String>> candidates) {
        return resourceId(
                MOB_EFFECT,
                MnaMobEffectId.class,
                "mobEffectId",
                "minecraft",
                MnaMobEffectId::of,
                MnaMobEffectId::location,
                MnaMobEffectId::parse,
                MnaTypedIdPiSerializers.requireSerializer(MnaTypedIdPiSerializers.MOB_EFFECT_ID),
                candidates
        );
    }

    public static PiIdSpec<MnaRitualEffectId> ritualEffect(Supplier<List<String>> candidates) {
        return resourceId(
                RITUAL_EFFECT,
                MnaRitualEffectId.class,
                "ritualEffectId",
                "mna",
                MnaRitualEffectId::of,
                MnaRitualEffectId::location,
                MnaRitualEffectId::parse,
                MnaTypedIdPiSerializers.requireSerializer(MnaTypedIdPiSerializers.RITUAL_EFFECT_ID),
                candidates
        );
    }

    public static PiIdSpec<MnaSpellEffectId> spellEffect(Supplier<List<String>> candidates) {
        return resourceId(
                SPELL_EFFECT,
                MnaSpellEffectId.class,
                "spellEffectId",
                "mna",
                MnaSpellEffectId::of,
                MnaSpellEffectId::location,
                MnaSpellEffectId::parse,
                MnaTypedIdPiSerializers.requireSerializer(MnaTypedIdPiSerializers.SPELL_EFFECT_ID),
                candidates
        );
    }

    public static PiIdSpec<MnaShapeId> shape(Supplier<List<String>> candidates) {
        return resourceId(
                SHAPE,
                MnaShapeId.class,
                "shapeId",
                "mna",
                MnaShapeId::of,
                MnaShapeId::location,
                MnaShapeId::parse,
                MnaTypedIdPiSerializers.requireSerializer(MnaTypedIdPiSerializers.SHAPE_ID),
                candidates
        );
    }

    public static PiIdSpec<MnaModifierId> modifier(Supplier<List<String>> candidates) {
        return resourceId(
                MODIFIER,
                MnaModifierId.class,
                "modifierId",
                "mna",
                MnaModifierId::of,
                MnaModifierId::location,
                MnaModifierId::parse,
                MnaTypedIdPiSerializers.requireSerializer(MnaTypedIdPiSerializers.MODIFIER_ID),
                candidates
        );
    }

    public static PiIdSpec<MnaConstructTaskId> constructTask(Supplier<List<String>> candidates) {
        return resourceId(
                CONSTRUCT_TASK,
                MnaConstructTaskId.class,
                "constructTaskId",
                "mna",
                MnaConstructTaskId::of,
                MnaConstructTaskId::location,
                MnaConstructTaskId::parse,
                MnaTypedIdPiSerializers.requireSerializer(MnaTypedIdPiSerializers.CONSTRUCT_TASK_ID),
                candidates
        );
    }

    public static PiIdSpec<MnaLootTableId> lootTable(Supplier<List<String>> candidates) {
        return resourceIdBuilder(
                LOOT_TABLE,
                MnaLootTableId.class,
                "lootTableId",
                "minecraft",
                MnaLootTableId::of,
                MnaLootTableId::location,
                MnaLootTableId::parse,
                MnaTypedIdPiSerializers.requireSerializer(MnaTypedIdPiSerializers.LOOT_TABLE_ID)
        )
                .candidates(candidates)
                .specialTypeRef("Special.LootTable")
                .build();
    }

    public static PiIdSpec<MnaStructureId> structure(Supplier<List<String>> candidates) {
        return resourceId(
                STRUCTURE,
                MnaStructureId.class,
                "structureId",
                "mna",
                MnaStructureId::of,
                MnaStructureId::location,
                MnaStructureId::parse,
                MnaTypedIdPiSerializers.requireSerializer(MnaTypedIdPiSerializers.STRUCTURE_ID),
                candidates
        );
    }

    public static PiIdSpec<MnaTexture> texture(Supplier<List<String>> candidates) {
        return resourceIdBuilder(
                TEXTURE,
                MnaTexture.class,
                "texture",
                "mna",
                MnaTexture::of,
                MnaTexture::location,
                MnaTexture::parse,
                MnaTypedIdPiSerializers.requireSerializer(MnaTypedIdPiSerializers.TEXTURE)
        )
                .candidates(candidates)
                .specialTypeRef("Special.Texture")
                .build();
    }

    public static PiIdSpec<ResourceLocation> sound(Supplier<List<String>> candidates) {
        return PiCommonIdSpecs.sound(candidates);
    }

    private static <T> PiIdSpec<T> resourceId(
            String role,
            Class<T> targetType,
            String fieldName,
            String defaultNamespace,
            Function<ResourceLocation, T> factory,
            Function<T, ResourceLocation> location,
            Function<Object, T> parser,
            PiSerializer<T> serializer,
            Supplier<List<String>> candidates
    ) {
        return resourceIdBuilder(role, targetType, fieldName, defaultNamespace, factory, location, parser, serializer)
                .candidates(candidates)
                .build();
    }

    private static <T> PiIdSpec.Builder<T> resourceIdBuilder(
            String role,
            Class<T> targetType,
            String fieldName,
            String defaultNamespace,
            Function<ResourceLocation, T> factory,
            Function<T, ResourceLocation> location,
            Function<Object, T> parser,
            PiSerializer<T> serializer
    ) {
        return PiIdSpec.builder(role, targetType)
                .converter(fieldName, PiTypedValueConverters.resourceLocationBacked(
                        targetType,
                        factory,
                        location,
                        PiResourceLocationConverter.builder().defaultNamespace(defaultNamespace).build()
                ))
                .parser(parser)
                .piSerializer(serializer);
    }
}
