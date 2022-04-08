package org.auioc.mcmod.extrachampions;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.auioc.mcmod.arnicalib.utils.LogUtil;
import org.auioc.mcmod.arnicalib.utils.java.VersionUtils;
import org.auioc.mcmod.extrachampions.common.affix.AffixRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ExtraChampions.MOD_ID)
public final class ExtraChampions {

    public static final String MOD_ID = "extrachampions";
    public static final String MOD_NAME = "ExtraChampions";
    public static final String MAIN_VERSION;
    public static final String FULL_VERSION;

    public static final Logger LOGGER = LogUtil.getLogger(MOD_NAME);
    private static final Marker CORE = LogUtil.getMarker("CORE");

    public ExtraChampions() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(
            (final FMLCommonSetupEvent event) -> {
                AffixRegistry.register();
            }
        );
    }

    static {
        Pair<String, String> version = VersionUtils.getModVersion(ExtraChampions.class);
        MAIN_VERSION = version.getLeft();
        FULL_VERSION = version.getRight();
        LOGGER.info(CORE, "Version: " + MAIN_VERSION + " (" + FULL_VERSION + ")");
    }

}

