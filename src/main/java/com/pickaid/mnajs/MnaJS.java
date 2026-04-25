package com.pickaid.mnajs;

import com.mojang.logging.LogUtils;
import com.pickaid.mnajs.kubejs.id.MnaTypedIdPiSerializers;
import com.pickaid.mnajs.kubejs.probe.MnaJSLegacyProbeCompat;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.ModList;
import org.slf4j.Logger;

@Mod(MnaJS.MOD_ID)
public final class MnaJS {
    public static final String MOD_ID = "mnajs";
    public static final String ID = MOD_ID;
    public static final Logger LOGGER = LogUtils.getLogger();

    public MnaJS() {
        LOGGER.info("Initializing {}", MOD_ID);
        MnaTypedIdPiSerializers.bootstrap();
        if (ModList.get().isLoaded("probejs_legacy")) {
            MnaJSLegacyProbeCompat.install();
        }
    }
}
