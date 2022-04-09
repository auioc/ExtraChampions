package org.auioc.mcmod.extrachampions.common.config;

import static org.auioc.mcmod.extrachampions.ExtraChampions.LOGGER;
import java.util.ArrayList;
import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import org.apache.logging.log4j.Marker;
import org.auioc.mcmod.arnicalib.utils.LogUtil;
import org.auioc.mcmod.extrachampions.api.affix.ExtraAffix;
import org.auioc.mcmod.extrachampions.common.affix.AffixRegistry;
import net.minecraftforge.common.ForgeConfigSpec;
import top.theillusivec4.champions.common.affix.core.AffixManager;
import top.theillusivec4.champions.common.config.AffixesConfig;
import top.theillusivec4.champions.common.config.ChampionsConfig;

@SuppressWarnings("rawtypes")
public class ExtraAffixConfig {

    private static final Marker MARKER = LogUtil.getMarker(ExtraAffixConfig.class);

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
                {
                    b.push("extra");
                    if (affix instanceof ExtraAffix) ((ExtraAffix) affix).buildConfig(b);
                    b.pop();
                }
            }
            b.pop();
        });

        SPEC = b.build();
    }

    public static void buildExtraAffixSettings(CommentedConfig rawConfig) {
        LOGGER.info(MARKER, "Build extra affix settings");
        for (var affix : AffixRegistry.AFFIXES) {
            UnmodifiableConfig config = rawConfig.get(affix.getIdentifier());
            {
                var affixConfig = new AffixesConfig.AffixConfig();
                {
                    affixConfig.identifier = affix.getIdentifier();
                    affixConfig.enabled = config.get("enabled");
                    affixConfig.minTier = config.get("minTier");
                    //? TOML dose not support NULL value
                    affixConfig.maxTier = ((Integer) config.get("minTier")) == -1 ? null : config.get("minTier");
                    affixConfig.mobList = config.get("mobList");
                    affixConfig.mobPermission = config.get("mobPermission");
                }
                ChampionsConfig.affixes.add(affixConfig);
            }
            {
                if (affix instanceof ExtraAffix) ((ExtraAffix) affix).setConfig(config.get("extra"));
            }
        }
        AffixManager.buildAffixSettings();
    }

}
