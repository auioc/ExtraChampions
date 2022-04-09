package org.auioc.mcmod.extrachampions.common.affix;

import static org.auioc.mcmod.extrachampions.ExtraChampions.LOGGER;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Marker;
import org.auioc.mcmod.arnicalib.utils.LogUtil;
import org.auioc.mcmod.extrachampions.common.affix.impl.AcupunctureAffix;
import org.auioc.mcmod.extrachampions.common.affix.impl.AssassinAffix;
import org.auioc.mcmod.extrachampions.common.affix.impl.DispelAffix;
import org.auioc.mcmod.extrachampions.common.affix.impl.FireeaterAffix;
import org.auioc.mcmod.extrachampions.common.affix.impl.HurricaneAffix;
import org.auioc.mcmod.extrachampions.common.affix.impl.JinxAffix;
import org.auioc.mcmod.extrachampions.common.affix.impl.RealityAffix;
import top.theillusivec4.champions.Champions;
import top.theillusivec4.champions.api.IAffix;

public final class AffixRegistry {

    public static final Marker MARKER = LogUtil.getMarker("Affix");

    public static final List<IAffix> AFFIXES = new ArrayList<IAffix>() {
        {
            add(new JinxAffix());
            add(new AcupunctureAffix());
            add(new DispelAffix());
            add(new RealityAffix());
            add(new HurricaneAffix());
            add(new FireeaterAffix());
            add(new AssassinAffix());
        }
    };

    public static void register() {
        LOGGER.info(MARKER, "Register extra affixes");
        AFFIXES.forEach((affix) -> Champions.API.registerAffix(affix));
    }

}
