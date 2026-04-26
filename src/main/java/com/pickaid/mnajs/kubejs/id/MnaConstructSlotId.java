package com.pickaid.mnajs.kubejs.id;

import com.mna.api.entities.construct.ConstructSlot;

import java.util.Objects;

public record MnaConstructSlotId(String value) {
    public MnaConstructSlotId {
        Objects.requireNonNull(value, "value");
        value = MnaEnumIds.normalizeName(value, "constructSlotId");
    }

    public static MnaConstructSlotId of(String value) {
        return new MnaConstructSlotId(value);
    }

    public static MnaConstructSlotId parse(Object value) {
        if (value instanceof MnaConstructSlotId id) {
            return id;
        }
        if (value instanceof ConstructSlot slot) {
            return of(MnaEnumIds.externalName(slot));
        }
        return of(MnaEnumIds.parseName(value, "constructSlotId"));
    }

    public String id() {
        return value;
    }
}
