package org.auioc.mcmod.extrachampions.common.affix;

import static org.auioc.mcmod.extrachampions.ExtraChampions.LOGGER;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Marker;
import org.auioc.mcmod.arnicalib.base.log.LogUtil;
import org.auioc.mcmod.extrachampions.common.affix.impl.*;
import org.auioc.mcmod.extrachampions.common.affix.impl.haruhiism.ShinjinAffix;
import org.auioc.mcmod.extrachampions.common.affix.impl.nel.JinxAffix;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.champions.Champions;
import top.theillusivec4.champions.api.IAffix;

public final class AffixRegistry {

    public static final Marker MARKER = LogUtil.getMarker("Affix");

    public static final List<IAffix> AFFIXES = new ArrayList<IAffix>() {
        {
            add(new AcupunctureAffix());
            add(new DispelAffix());
            add(new RealityAffix());
            add(new HurricaneAffix());
            add(new FireeaterAffix());
            add(new AssassinAffix());
            add(new NecromancerAffix());
            add(new LeaderAffix());
            add(new ShadowAffix());
            add(new StickyAffix());
            add(new DisarmoringAffix());
            add(new FuryAffix());
            add(new ThiefAffix());

            if (isLoaded("notenoughluck")) {
                add(new JinxAffix());
            }

            if (isLoaded("haruhiism")) {
                add(new ShinjinAffix());
            }
        }
    };

    private static boolean isLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    public static void register() {
        LOGGER.info(MARKER, "Register extra affixes");
        AFFIXES.forEach((affix) -> Champions.API.registerAffix(affix));
    }

}
