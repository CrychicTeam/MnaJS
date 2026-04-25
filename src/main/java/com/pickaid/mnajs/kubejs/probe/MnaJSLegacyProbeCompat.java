package com.pickaid.mnajs.kubejs.probe;

import zzzank.probejs.plugin.ProbeJSPlugins;

public final class MnaJSLegacyProbeCompat {
    private static boolean installed;

    private MnaJSLegacyProbeCompat() {
    }

    public static synchronized void install() {
        if (installed) {
            return;
        }
        installed = true;
        ProbeJSPlugins.remove(MnaJSLegacyProbePlugin.class);
        ProbeJSPlugins.register(new MnaJSLegacyProbePlugin());
    }
}
