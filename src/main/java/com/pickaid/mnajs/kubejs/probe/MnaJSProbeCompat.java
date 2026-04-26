package com.pickaid.mnajs.kubejs.probe;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.util.KubeJSPlugins;

import java.lang.reflect.Field;
import java.util.List;

public final class MnaJSProbeCompat {
    private static boolean installed;

    private MnaJSProbeCompat() {
    }

    public static synchronized void install() {
        if (installed) {
            return;
        }

        try {
            Field listField = KubeJSPlugins.class.getDeclaredField("LIST");
            listField.setAccessible(true);

            @SuppressWarnings("unchecked")
            List<KubeJSPlugin> plugins = (List<KubeJSPlugin>) listField.get(null);

            plugins.removeIf(MnaJSProbePlugin.class::isInstance);
            plugins.add(new MnaJSProbePlugin());
            installed = true;
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Failed to install MnaJS ProbeJS plugin", exception);
        }
    }
}
