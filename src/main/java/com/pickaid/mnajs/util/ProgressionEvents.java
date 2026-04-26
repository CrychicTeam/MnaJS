package com.pickaid.mnajs.util;

import com.mna.api.events.ProgressionEventIDs;
import com.pickaid.mnajs.kubejs.id.MnaProgressionEventId;
import dev.latvian.mods.kubejs.typings.Info;

public final class ProgressionEvents {
    public static final MnaProgressionEventId CONSTRUCT_LODESTAR_ASSIGNED = MnaProgressionEventId.of(ProgressionEventIDs.CONSTRUCT_LODESTAR_ASSIGNED);
    public static final MnaProgressionEventId APPLY_POUCH_PATCH = MnaProgressionEventId.of(ProgressionEventIDs.APPLY_POUCH_PATCH);
    public static final MnaProgressionEventId ELDRIN_FUME_LIT = MnaProgressionEventId.of(ProgressionEventIDs.ELDRIN_FUME_LIT);
    public static final MnaProgressionEventId STUDY_DESK_USED = MnaProgressionEventId.of(ProgressionEventIDs.STUDY_DESK_USED);
    public static final MnaProgressionEventId SPELL_AFFINITY_TINKERED = MnaProgressionEventId.of(ProgressionEventIDs.SPELL_AFFINITY_TINKERED);
    public static final MnaProgressionEventId REMOVE_ENCHANTMENT = MnaProgressionEventId.of(ProgressionEventIDs.REMOVE_ENCHANTMENT);
    public static final MnaProgressionEventId TRANSCRIBE_SPELL = MnaProgressionEventId.of(ProgressionEventIDs.TRANSCRIBE_SPELL);
    public static final MnaProgressionEventId CAPTURE_WELLSPRING = MnaProgressionEventId.of(ProgressionEventIDs.CAPTURE_WELLSPRING);
    public static final MnaProgressionEventId OPEN_CACHE = MnaProgressionEventId.of(ProgressionEventIDs.OPEN_CACHE);

    private ProgressionEvents() {
    }

    @Info("Return all built-in Mana and Artifice progression event ids as typed ids.")
    public static MnaProgressionEventId[] values() {
        return new MnaProgressionEventId[]{
                CONSTRUCT_LODESTAR_ASSIGNED,
                APPLY_POUCH_PATCH,
                ELDRIN_FUME_LIT,
                STUDY_DESK_USED,
                SPELL_AFFINITY_TINKERED,
                REMOVE_ENCHANTMENT,
                TRANSCRIBE_SPELL,
                CAPTURE_WELLSPRING,
                OPEN_CACHE
        };
    }
}
