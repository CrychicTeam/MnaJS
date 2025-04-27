package com.pickaid.mnajs;

import com.mna.api.capabilities.ChronoAnchorData;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(MnaJS.ID)
public class MnaJS {
    public static final String ID = "mnajs";
    public static final Logger LOGGER = LogManager.getLogger();
    public static ChronoAnchorData ManaManger;

    public MnaJS() {
        ManaManger = new ChronoAnchorData();
    }

    public static ResourceLocation source(String path) {
        return new ResourceLocation(MnaJS.ID, path);
    }
}
