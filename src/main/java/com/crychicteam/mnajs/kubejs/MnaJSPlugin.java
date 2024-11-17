package com.crychicteam.mnajs.kubejs;

import com.crychicteam.mnajs.content.CustomFaction;
import com.mna.Registries;
import com.mna.api.faction.IFaction;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.registry.RegistryInfo;

public class MnaJSPlugin extends KubeJSPlugin {
    public static final RegistryInfo<IFaction> FACTION_REGISTRY = RegistryInfo.of(Registries.Factions.get().getRegistryKey(), IFaction.class);

    @Override
    public void registerEvents() {
        MnaJSEvents.GROUP.register();
    }

    @Override
    public void init() {
        FACTION_REGISTRY.addType("basic", CustomFaction.Builder.class, CustomFaction.Builder::new);
    }
}

