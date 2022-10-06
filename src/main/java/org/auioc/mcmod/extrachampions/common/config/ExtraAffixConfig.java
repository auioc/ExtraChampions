package org.auioc.mcmod.extrachampions.common.config;

import static org.auioc.mcmod.extrachampions.ExtraChampions.LOGGER;
import org.apache.logging.log4j.Marker;
import org.auioc.mcmod.arnicalib.base.log.LogUtil;
import org.auioc.mcmod.extrachampions.api.affix.AffixBasicConfig;
import org.auioc.mcmod.extrachampions.api.affix.ExtraAffix;
import org.auioc.mcmod.extrachampions.common.affix.AffixRegistry;
import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import top.theillusivec4.champions.common.affix.core.AffixManager;
import top.theillusivec4.champions.common.config.ChampionsConfig;

public class ExtraAffixConfig {

    private static final Marker MARKER = LogUtil.getMarker(ExtraAffixConfig.class);

    public static final ForgeConfigSpec SPEC;

    public static CommentedConfig rawConfig;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        AffixRegistry.AFFIXES.forEach((affix) -> {
            builder.push(affix.getIdentifier());
            if (affix instanceof ExtraAffix) {
                ((ExtraAffix<?>) affix).buildConfig(builder);
            } else {
                (new AffixBasicConfig()).build(builder);
            }
            builder.pop();
        });

        SPEC = builder.build();
    }

    public static void buildExtraAffixSettings() {
        LOGGER.info(MARKER, "Build extra affix settings");
        for (var affix : AffixRegistry.AFFIXES) {
            UnmodifiableConfig config = ExtraAffixConfig.rawConfig.get(affix.getIdentifier());
            ChampionsConfig.affixes.add(AffixBasicConfig.transform(affix.getIdentifier(), config));
            if (affix instanceof ExtraAffix) ((ExtraAffix<?>) affix).setExtraConfig(config.get("extra"));
        }
        AffixManager.buildAffixSettings();
    }

}
