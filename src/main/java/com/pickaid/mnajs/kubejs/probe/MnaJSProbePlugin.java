package com.pickaid.mnajs.kubejs.probe;

import com.probejs.ProbeJSPlugin;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.util.ClassFilter;

public final class MnaJSProbePlugin extends ProbeJSPlugin {
    @Override
    public void onServerReload() {
        MnaJSProbeTypes.installAssignments();
    }

    @Override
    public void registerClasses(ScriptType scriptType, ClassFilter filter) {
        MnaJSLegacyProbeJava.providedClasses().forEach(filter::allow);
    }
}
