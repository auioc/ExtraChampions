package org.auioc.mcmod.extrachampions.common.affix;

import static org.auioc.mcmod.extrachampions.ExtraChampions.LOGGER;
import org.apache.logging.log4j.Marker;
import org.auioc.mcmod.arnicalib.utils.LogUtil;
import org.auioc.mcmod.extrachampions.common.affix.impl.AcupunctureAffix;
import org.auioc.mcmod.extrachampions.common.affix.impl.DispelAffix;
import org.auioc.mcmod.extrachampions.common.affix.impl.HurricaneAffix;
import org.auioc.mcmod.extrachampions.common.affix.impl.JinxAffix;
import org.auioc.mcmod.extrachampions.common.affix.impl.RealityAffix;
import top.theillusivec4.champions.Champions;

public final class AffixRegistry {

    public static final Marker MARKER = LogUtil.getMarker("Affix");

    public static void register() {
        LOGGER.info(MARKER, "Register extra affixes");

        Champions.API.registerAffixes(
            new JinxAffix(),
            new AcupunctureAffix(),
            new DispelAffix(),
            new RealityAffix(),
            new HurricaneAffix()
        );
    }

}