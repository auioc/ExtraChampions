package org.auioc.mcmod.extrachampions.common.config;

import static org.auioc.mcmod.extrachampions.ExtraChampions.LOGGER;
import java.util.ArrayList;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import org.apache.logging.log4j.Marker;
import org.auioc.mcmod.arnicalib.utils.LogUtil;
import org.auioc.mcmod.extrachampions.ExtraChampions;
import org.auioc.mcmod.extrachampions.common.affix.AffixRegistry;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import top.theillusivec4.champions.common.affix.core.AffixManager;
import top.theillusivec4.champions.common.config.AffixesConfig;
import top.theillusivec4.champions.common.config.ChampionsConfig;

public class ExAffixConfig {

    private static final Marker MARKER = LogUtil.getMarker(ExAffixConfig.class);

    public static final ForgeConfigSpec SPEC;

    static {
        ForgeConfigSpec.Builder b = new ForgeConfigSpec.Builder();

        AffixRegistry.AFFIXES.forEach((affix) -> {
            b.push(affix.getIdentifier());
            {
                b.define("enabled", true);
                b.define("minTier", 1);
                b.define("maxTier", -1); //? TOML dose not support NULL value
                b.defineList("mobList", new ArrayList<String>(), (o) -> o instanceof String);
                b.define("mobPermission", "BLACKLIST");
            }
            b.pop();
        });

        SPEC = b.build();
    }

    public static void rebuildAffixSettings(final ModConfigEvent event) {
        ModConfig config = event.getConfig();
        if (
            config.getModId().equals(ExtraChampions.MOD_ID)
                && config.getType() == ModConfig.Type.SERVER
                && config.getSpec() == ExAffixConfig.SPEC
        ) {
            LOGGER.info(MARKER, "Rebuild affix settings");
            AffixRegistry.AFFIXES.forEach((affix) -> {
                UnmodifiableConfig rawConfig = event.getConfig().getConfigData().get(affix.getIdentifier());
                {
                    var affixConfig = new AffixesConfig.AffixConfig();
                    {
                        affixConfig.identifier = affix.getIdentifier();
                        affixConfig.enabled = rawConfig.get("enabled");
                        affixConfig.minTier = rawConfig.get("minTier");
                        //? TOML dose not support NULL value
                        affixConfig.maxTier = ((Integer) rawConfig.get("minTier")) == -1 ? null : rawConfig.get("minTier");
                        affixConfig.mobList = rawConfig.get("mobList");
                        affixConfig.mobPermission = rawConfig.get("mobPermission");
                    }
                    ChampionsConfig.affixes.add(affixConfig);
                }
            });
            AffixManager.buildAffixSettings();
        }
    }

}
