package com.pickaid.mnajs.kubejs.id;

import com.pickaid.mnajs.MnaJS;
import com.pickaid.mnajs.kubejs.texture.MnaTexture;
import net.minecraft.resources.ResourceLocation;
import org.pickaid.piserializekit.api.nbt.PiNbtCodec;
import org.pickaid.piserializekit.api.packet.PiPacketCodec;
import org.pickaid.piserializekit.api.schema.PiDecodeContext;
import org.pickaid.piserializekit.api.service.PiSerializeService;
import org.pickaid.piserializekit.api.service.PiSerializeServices;
import org.pickaid.piserializekit.api.service.PiSerializer;
import org.pickaid.piserializekit.api.service.PiSerializerType;
import org.pickaid.piserializekit.api.service.PiSerializers;
import org.pickaid.piserializekit.runtime.service.PiBuiltInSerializers;
import org.pickaid.piserializekit.runtime.service.PiSerializeRuntime;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public final class MnaTypedIdPiSerializers {
    public static final PiSerializerType<MnaAdvancementId> ADVANCEMENT_ID = type("advancement_id", MnaAdvancementId.class);
    public static final PiSerializerType<MnaProgressionEventId> PROGRESSION_EVENT_ID = type("progression_event_id", MnaProgressionEventId.class);
    public static final PiSerializerType<MnaFactionId> FACTION_ID = type("faction_id", MnaFactionId.class);
    public static final PiSerializerType<MnaCastingResourceId> CASTING_RESOURCE_ID = type("casting_resource_id", MnaCastingResourceId.class);
    public static final PiSerializerType<MnaConstructMaterialId> CONSTRUCT_MATERIAL_ID = type("construct_material_id", MnaConstructMaterialId.class);
    public static final PiSerializerType<MnaConstructSlotId> CONSTRUCT_SLOT_ID = type("construct_slot_id", MnaConstructSlotId.class);
    public static final PiSerializerType<MnaConstructCapabilityId> CONSTRUCT_CAPABILITY_ID = type("construct_capability_id", MnaConstructCapabilityId.class);
    public static final PiSerializerType<MnaMobEffectId> MOB_EFFECT_ID = type("mob_effect_id", MnaMobEffectId.class);
    public static final PiSerializerType<MnaRitualEffectId> RITUAL_EFFECT_ID = type("ritual_effect_id", MnaRitualEffectId.class);
    public static final PiSerializerType<MnaSpellEffectId> SPELL_EFFECT_ID = type("spell_effect_id", MnaSpellEffectId.class);
    public static final PiSerializerType<MnaShapeId> SHAPE_ID = type("shape_id", MnaShapeId.class);
    public static final PiSerializerType<MnaModifierId> MODIFIER_ID = type("modifier_id", MnaModifierId.class);
    public static final PiSerializerType<MnaConstructTaskId> CONSTRUCT_TASK_ID = type("construct_task_id", MnaConstructTaskId.class);
    public static final PiSerializerType<MnaRitualId> RITUAL_ID = type("ritual_id", MnaRitualId.class);
    public static final PiSerializerType<MnaManaweavePatternId> MANAWEAVE_PATTERN_ID = type("manaweave_pattern_id", MnaManaweavePatternId.class);
    public static final PiSerializerType<MnaCantripId> CANTRIP_ID = type("cantrip_id", MnaCantripId.class);
    public static final PiSerializerType<MnaItemId> ITEM_ID = type("item_id", MnaItemId.class);
    public static final PiSerializerType<MnaBlockId> BLOCK_ID = type("block_id", MnaBlockId.class);
    public static final PiSerializerType<MnaLootTableId> LOOT_TABLE_ID = type("loot_table_id", MnaLootTableId.class);
    public static final PiSerializerType<MnaSoundId> SOUND_ID = type("sound_id", MnaSoundId.class);
    public static final PiSerializerType<MnaStructureId> STRUCTURE_ID = type("structure_id", MnaStructureId.class);
    public static final PiSerializerType<MnaTexture> TEXTURE = type("texture", MnaTexture.class);

    private static final AtomicBoolean BOOTSTRAPPED = new AtomicBoolean(false);

    private MnaTypedIdPiSerializers() {
    }

    public static void bootstrap() {
        if (!BOOTSTRAPPED.compareAndSet(false, true)) {
            return;
        }

        PiSerializeService service = PiSerializeServices.find().orElseGet(() -> {
            PiSerializeRuntime runtime = new PiSerializeRuntime();
            PiBuiltInSerializers.install(runtime);
            PiSerializeServices.install(runtime);
            return runtime;
        });

        registerIfMissing(service, ADVANCEMENT_ID, resourceLocationBacked(service, MnaAdvancementId::of, MnaAdvancementId::location));
        registerIfMissing(service, PROGRESSION_EVENT_ID, resourceLocationBacked(service, MnaProgressionEventId::of, MnaProgressionEventId::location));
        registerIfMissing(service, FACTION_ID, resourceLocationBacked(service, MnaFactionId::of, MnaFactionId::location));
        registerIfMissing(service, CASTING_RESOURCE_ID, resourceLocationBacked(service, MnaCastingResourceId::of, MnaCastingResourceId::location));
        registerIfMissing(service, CONSTRUCT_MATERIAL_ID, resourceLocationBacked(service, MnaConstructMaterialId::of, MnaConstructMaterialId::location));
        registerIfMissing(service, CONSTRUCT_SLOT_ID, stringBacked(service, MnaConstructSlotId::of, MnaConstructSlotId::id));
        registerIfMissing(service, CONSTRUCT_CAPABILITY_ID, stringBacked(service, MnaConstructCapabilityId::of, MnaConstructCapabilityId::id));
        registerIfMissing(service, MOB_EFFECT_ID, resourceLocationBacked(service, MnaMobEffectId::of, MnaMobEffectId::location));
        registerIfMissing(service, RITUAL_EFFECT_ID, resourceLocationBacked(service, MnaRitualEffectId::of, MnaRitualEffectId::location));
        registerIfMissing(service, SPELL_EFFECT_ID, resourceLocationBacked(service, MnaSpellEffectId::of, MnaSpellEffectId::location));
        registerIfMissing(service, SHAPE_ID, resourceLocationBacked(service, MnaShapeId::of, MnaShapeId::location));
        registerIfMissing(service, MODIFIER_ID, resourceLocationBacked(service, MnaModifierId::of, MnaModifierId::location));
        registerIfMissing(service, CONSTRUCT_TASK_ID, resourceLocationBacked(service, MnaConstructTaskId::of, MnaConstructTaskId::location));
        registerIfMissing(service, RITUAL_ID, resourceLocationBacked(service, MnaRitualId::of, MnaRitualId::location));
        registerIfMissing(service, MANAWEAVE_PATTERN_ID, resourceLocationBacked(service, MnaManaweavePatternId::of, MnaManaweavePatternId::location));
        registerIfMissing(service, CANTRIP_ID, resourceLocationBacked(service, MnaCantripId::of, MnaCantripId::location));
        registerIfMissing(service, ITEM_ID, resourceLocationBacked(service, MnaItemId::of, MnaItemId::location));
        registerIfMissing(service, BLOCK_ID, resourceLocationBacked(service, MnaBlockId::of, MnaBlockId::location));
        registerIfMissing(service, LOOT_TABLE_ID, resourceLocationBacked(service, MnaLootTableId::of, MnaLootTableId::location));
        registerIfMissing(service, SOUND_ID, resourceLocationBacked(service, MnaSoundId::of, MnaSoundId::location));
        registerIfMissing(service, STRUCTURE_ID, resourceLocationBacked(service, MnaStructureId::of, MnaStructureId::location));
        registerIfMissing(service, TEXTURE, resourceLocationBacked(service, MnaTexture::of, MnaTexture::location));
    }

    private static <T> PiSerializerType<T> type(String path, Class<T> javaType) {
        return new PiSerializerType<>(new ResourceLocation(MnaJS.MOD_ID, path), javaType);
    }

    private static <T> void registerIfMissing(PiSerializeService service, PiSerializerType<T> type, PiSerializer<T> serializer) {
        if (service.lookup(type).isPresent()) {
            return;
        }
        service.register(type, serializer);
    }

    private static <T> PiSerializer<T> resourceLocationBacked(
            PiSerializeService service,
            Function<ResourceLocation, T> factory,
            Function<T, ResourceLocation> extractor
    ) {
        Objects.requireNonNull(service, "service");
        Objects.requireNonNull(factory, "factory");
        Objects.requireNonNull(extractor, "extractor");

        PiSerializer<ResourceLocation> resourceLocationSerializer = service.require(PiSerializers.RESOURCE_LOCATION);
        PiNbtCodec<ResourceLocation> resourceLocationNbt = resourceLocationSerializer.nbtCodec();
        PiPacketCodec<ResourceLocation> resourceLocationPacket = resourceLocationSerializer.packetCodec();

        return new PiSerializer<>() {
            @Override
            public com.mojang.serialization.Codec<T> valueCodec() {
                return resourceLocationSerializer.valueCodec().xmap(factory, extractor);
            }

            @Override
            public PiNbtCodec<T> nbtCodec() {
                return new PiNbtCodec<>() {
                    @Override
                    public net.minecraft.nbt.CompoundTag encode(T value) {
                        return resourceLocationNbt.encode(extractor.apply(value));
                    }

                    @Override
                    public T decode(net.minecraft.nbt.CompoundTag tag) {
                        return factory.apply(resourceLocationNbt.decode(tag));
                    }

                    @Override
                    public T decodeInto(net.minecraft.nbt.CompoundTag tag, T current) {
                        return decode(tag);
                    }

                    @Override
                    public net.minecraft.nbt.Tag encodeTag(T value) {
                        return resourceLocationNbt.encodeTag(extractor.apply(value));
                    }

                    @Override
                    public T decodeTag(net.minecraft.nbt.Tag tag) {
                        return factory.apply(resourceLocationNbt.decodeTag(tag));
                    }

                    @Override
                    public T decodeIntoTag(net.minecraft.nbt.Tag tag, T current) {
                        return decodeTag(tag);
                    }
                };
            }

            @Override
            public PiPacketCodec<T> packetCodec() {
                return new PiPacketCodec<>() {
                    @Override
                    public void write(org.pickaid.piserializekit.api.packet.buffer.PiPacketBuffer buffer, T value) {
                        resourceLocationPacket.write(buffer, extractor.apply(value));
                    }

                    @Override
                    public T read(org.pickaid.piserializekit.api.packet.buffer.PiPacketBuffer buffer, PiDecodeContext context) {
                        ResourceLocation value = resourceLocationPacket.read(buffer, context);
                        return value == null ? null : factory.apply(value);
                    }
                };
            }
        };
    }

    private static <T> PiSerializer<T> stringBacked(
            PiSerializeService service,
            Function<String, T> factory,
            Function<T, String> extractor
    ) {
        Objects.requireNonNull(service, "service");
        Objects.requireNonNull(factory, "factory");
        Objects.requireNonNull(extractor, "extractor");

        PiSerializer<String> stringSerializer = service.require(PiSerializers.STRING);
        PiNbtCodec<String> stringNbt = stringSerializer.nbtCodec();
        PiPacketCodec<String> stringPacket = stringSerializer.packetCodec();

        return new PiSerializer<>() {
            @Override
            public com.mojang.serialization.Codec<T> valueCodec() {
                return stringSerializer.valueCodec().xmap(factory, extractor);
            }

            @Override
            public PiNbtCodec<T> nbtCodec() {
                return new PiNbtCodec<>() {
                    @Override
                    public net.minecraft.nbt.CompoundTag encode(T value) {
                        return stringNbt.encode(extractor.apply(value));
                    }

                    @Override
                    public T decode(net.minecraft.nbt.CompoundTag tag) {
                        return factory.apply(stringNbt.decode(tag));
                    }

                    @Override
                    public T decodeInto(net.minecraft.nbt.CompoundTag tag, T current) {
                        return decode(tag);
                    }

                    @Override
                    public net.minecraft.nbt.Tag encodeTag(T value) {
                        return stringNbt.encodeTag(extractor.apply(value));
                    }

                    @Override
                    public T decodeTag(net.minecraft.nbt.Tag tag) {
                        return factory.apply(stringNbt.decodeTag(tag));
                    }

                    @Override
                    public T decodeIntoTag(net.minecraft.nbt.Tag tag, T current) {
                        return decodeTag(tag);
                    }
                };
            }

            @Override
            public PiPacketCodec<T> packetCodec() {
                return new PiPacketCodec<>() {
                    @Override
                    public void write(org.pickaid.piserializekit.api.packet.buffer.PiPacketBuffer buffer, T value) {
                        stringPacket.write(buffer, extractor.apply(value));
                    }

                    @Override
                    public T read(org.pickaid.piserializekit.api.packet.buffer.PiPacketBuffer buffer, PiDecodeContext context) {
                        String value = stringPacket.read(buffer, context);
                        return value == null ? null : factory.apply(value);
                    }
                };
            }
        };
    }

    public static <T> PiSerializer<T> requireSerializer(PiSerializerType<T> type) {
        bootstrap();
        return PiSerializeServices.requireSerializer(type);
    }

    public static <T> PiSerializer<Optional<T>> requireOptionalSerializer(PiSerializerType<T> type) {
        bootstrap();
        return PiSerializers.optionalOf(PiSerializeServices.requireSerializer(type));
    }
}
