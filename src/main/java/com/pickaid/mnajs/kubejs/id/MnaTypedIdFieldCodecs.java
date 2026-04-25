package com.pickaid.mnajs.kubejs.id;

import com.pickaid.mnajs.kubejs.texture.MnaTexture;
import org.pickaid.piserializekit.api.schema.PiFieldCodecProvider;
import org.pickaid.piserializekit.api.service.PiSerializer;
import org.pickaid.piserializekit.api.service.PiSerializerType;
import org.pickaid.piserializekit.api.service.PiSerializers;

import java.util.Optional;

public final class MnaTypedIdFieldCodecs {
    private MnaTypedIdFieldCodecs() {
    }

    public abstract static class IdProvider<T> implements PiFieldCodecProvider<T> {
        private final PiSerializerType<T> type;

        protected IdProvider(PiSerializerType<T> type) {
            this.type = type;
        }

        @Override
        public final PiSerializer<T> serializer() {
            return MnaTypedIdPiSerializers.requireSerializer(type);
        }
    }

    public abstract static class OptionalIdProvider<T> implements PiFieldCodecProvider<Optional<T>> {
        private final PiSerializerType<T> type;

        protected OptionalIdProvider(PiSerializerType<T> type) {
            this.type = type;
        }

        @Override
        public final PiSerializer<Optional<T>> serializer() {
            return MnaTypedIdPiSerializers.requireOptionalSerializer(type);
        }
    }

    public static final class FactionIdCodec extends IdProvider<MnaFactionId> {
        public FactionIdCodec() {
            super(MnaTypedIdPiSerializers.FACTION_ID);
        }
    }

    public static final class RitualEffectIdCodec extends IdProvider<MnaRitualEffectId> {
        public RitualEffectIdCodec() {
            super(MnaTypedIdPiSerializers.RITUAL_EFFECT_ID);
        }
    }

    public static final class SpellEffectIdCodec extends IdProvider<MnaSpellEffectId> {
        public SpellEffectIdCodec() {
            super(MnaTypedIdPiSerializers.SPELL_EFFECT_ID);
        }
    }

    public static final class ShapeIdCodec extends IdProvider<MnaShapeId> {
        public ShapeIdCodec() {
            super(MnaTypedIdPiSerializers.SHAPE_ID);
        }
    }

    public static final class ModifierIdCodec extends IdProvider<MnaModifierId> {
        public ModifierIdCodec() {
            super(MnaTypedIdPiSerializers.MODIFIER_ID);
        }
    }

    public static final class RitualIdCodec extends IdProvider<MnaRitualId> {
        public RitualIdCodec() {
            super(MnaTypedIdPiSerializers.RITUAL_ID);
        }
    }

    public static final class ManaweavePatternIdCodec extends IdProvider<MnaManaweavePatternId> {
        public ManaweavePatternIdCodec() {
            super(MnaTypedIdPiSerializers.MANAWEAVE_PATTERN_ID);
        }
    }

    public static final class OptionalManaweavePatternIdCodec extends OptionalIdProvider<MnaManaweavePatternId> {
        public OptionalManaweavePatternIdCodec() {
            super(MnaTypedIdPiSerializers.MANAWEAVE_PATTERN_ID);
        }
    }

    public static final class CantripIdCodec extends IdProvider<MnaCantripId> {
        public CantripIdCodec() {
            super(MnaTypedIdPiSerializers.CANTRIP_ID);
        }
    }

    public static final class ItemIdCodec extends IdProvider<MnaItemId> {
        public ItemIdCodec() {
            super(MnaTypedIdPiSerializers.ITEM_ID);
        }
    }

    public static final class BlockIdCodec extends IdProvider<MnaBlockId> {
        public BlockIdCodec() {
            super(MnaTypedIdPiSerializers.BLOCK_ID);
        }
    }

    public static final class LootTableIdCodec extends IdProvider<MnaLootTableId> {
        public LootTableIdCodec() {
            super(MnaTypedIdPiSerializers.LOOT_TABLE_ID);
        }
    }

    public static final class TextureCodec extends IdProvider<MnaTexture> {
        public TextureCodec() {
            super(MnaTypedIdPiSerializers.TEXTURE);
        }
    }
}
