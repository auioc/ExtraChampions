package org.auioc.mcmod.extrachampions.common.affix;

import static org.auioc.mcmod.extrachampions.ExtraChampions.LOGGER;
import org.apache.logging.log4j.Marker;
import org.auioc.mcmod.arnicalib.utils.LogUtil;
import top.theillusivec4.champions.Champions;

public final class AffixRegistry {

    public static final Marker MARKER = LogUtil.getMarker("Affix");

    public static void register() {
        LOGGER.info(MARKER, "Register extra affixes");

        Champions.API.registerAffixes();
    }

}
