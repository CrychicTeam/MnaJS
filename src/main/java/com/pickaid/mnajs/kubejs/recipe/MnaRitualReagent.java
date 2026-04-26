package com.pickaid.mnajs.kubejs.recipe;

import com.pickaid.mnajs.kubejs.id.MnaItemOrTag;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import dev.latvian.mods.rhino.util.HideFromJS;

import java.util.Objects;

public final class MnaRitualReagent {
    private final char symbol;
    private final MnaItemOrTag item;
    private boolean optional;
    private boolean consume = true;
    private boolean manualReturn;
    private boolean dynamic;
    private boolean dynamicSource;

    public MnaRitualReagent(String symbol, MnaItemOrTag item) {
        this.symbol = parseSymbol(symbol);
        this.item = Objects.requireNonNull(item, "item");
    }

    @HideFromJS
    public MnaRitualReagent(char symbol, MnaItemOrTag item) {
        this(String.valueOf(symbol), item);
    }

    @Info(value = "Create a ritual reagent config object for ritual recipe chains.", params = {
            @Param(name = "symbol", value = "Single-character symbol used in reagent rows."),
            @Param(name = "item", value = "Item id or item tag used by this reagent key.")
    })
    public static MnaRitualReagent of(String symbol, MnaItemOrTag item) {
        return new MnaRitualReagent(symbol, item);
    }

    @HideFromJS
    public static MnaRitualReagent of(char symbol, MnaItemOrTag item) {
        return new MnaRitualReagent(symbol, item);
    }

    @Info("Mark this ritual reagent as optional.")
    public MnaRitualReagent optional() {
        this.optional = true;
        return this;
    }

    @Info("Mark this ritual reagent as not consumed by the ritual.")
    public MnaRitualReagent keep() {
        this.consume = false;
        return this;
    }

    @Info("Mark this ritual reagent as manually returned.")
    public MnaRitualReagent manualReturn() {
        this.manualReturn = true;
        return this;
    }

    @Info("Mark this ritual reagent as dynamic.")
    public MnaRitualReagent dynamic() {
        if (dynamicSource) {
            throw new IllegalStateException("A ritual reagent cannot be both dynamic and dynamicSource");
        }
        this.dynamic = true;
        return this;
    }

    @Info("Mark this ritual reagent as the ritual's dynamic source.")
    public MnaRitualReagent dynamicSource() {
        if (dynamic) {
            throw new IllegalStateException("A ritual reagent cannot be both dynamic and dynamicSource");
        }
        this.dynamicSource = true;
        return this;
    }

    public String symbol() {
        return String.valueOf(symbol);
    }

    public MnaItemOrTag item() {
        return item;
    }

    public boolean isOptional() {
        return optional;
    }

    public boolean consumes() {
        return consume;
    }

    public boolean isManualReturn() {
        return manualReturn;
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public boolean isDynamicSource() {
        return dynamicSource;
    }

    @HideFromJS
    public char symbolChar() {
        return symbol;
    }

    private static char parseSymbol(String value) {
        Objects.requireNonNull(value, "symbol");
        if (value.length() != 1) {
            throw new IllegalArgumentException("Ritual reagent symbol must be exactly one character");
        }

        char symbol = value.charAt(0);
        if (Character.isWhitespace(symbol)) {
            throw new IllegalArgumentException("Ritual reagent symbol can't be whitespace");
        }
        return symbol;
    }
}
