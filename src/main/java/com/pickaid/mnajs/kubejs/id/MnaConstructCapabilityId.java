package com.pickaid.mnajs.kubejs.id;

import com.mna.api.entities.construct.ConstructCapability;

import java.util.Objects;

public record MnaConstructCapabilityId(String value) {
    public MnaConstructCapabilityId {
        Objects.requireNonNull(value, "value");
        value = MnaEnumIds.normalizeName(value, "constructCapabilityId");
    }

    public static MnaConstructCapabilityId of(String value) {
        return new MnaConstructCapabilityId(value);
    }

    public static MnaConstructCapabilityId parse(Object value) {
        if (value instanceof MnaConstructCapabilityId id) {
            return id;
        }
        if (value instanceof ConstructCapability capability) {
            return of(MnaEnumIds.externalName(capability));
        }
        return of(MnaEnumIds.parseName(value, "constructCapabilityId"));
    }

    public String id() {
        return value;
    }
}
