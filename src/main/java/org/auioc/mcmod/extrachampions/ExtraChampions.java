package org.auioc.mcmod.extrachampions;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.auioc.mcmod.arnicalib.utils.LogUtil;
import org.auioc.mcmod.arnicalib.utils.java.VersionUtils;
import net.minecraftforge.fml.common.Mod;

@Mod(ExtraChampions.MOD_ID)
public final class ExtraChampions {

    public static final String MOD_ID = "extrachampions";
    public static final String MOD_NAME = "ExtraChampions";
    public static final String MAIN_VERSION;
    public static final String FULL_VERSION;

    public static final Logger LOGGER = LogUtil.getLogger(MOD_NAME);
    private static final Marker CORE = LogUtil.getMarker("CORE");

    public ExtraChampions() {}

    static {
        Pair<String, String> version = VersionUtils.getModVersion(ExtraChampions.class);
        MAIN_VERSION = version.getLeft();
        FULL_VERSION = version.getRight();
        LOGGER.info(CORE, "Version: " + MAIN_VERSION + " (" + FULL_VERSION + ")");
    }

}

