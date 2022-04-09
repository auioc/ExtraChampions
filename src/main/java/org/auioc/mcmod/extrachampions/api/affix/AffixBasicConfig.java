package org.auioc.mcmod.extrachampions.api.affix;

import java.util.ArrayList;
import java.util.List;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import top.theillusivec4.champions.common.config.AffixesConfig;

public class AffixBasicConfig {

    public boolean enabled = true;
    public int minTier = 1;
    public int maxTier = -1; //? TOML dose not support NULL value
    public List<String> mobList = new ArrayList<String>();
    public String mobPermission = "BLACKLIST";

    public AffixBasicConfig setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public AffixBasicConfig setMinTier(int minTier) {
        this.minTier = minTier;
        return this;
    }

    public AffixBasicConfig setMaxTier(int maxTier) {
        this.maxTier = maxTier;
        return this;
    }

    public AffixBasicConfig setMobList(List<String> mobList) {
        this.mobList = mobList;
        return this;
    }

    public AffixBasicConfig setMobPermission(String mobPermission) {
        this.mobPermission = mobPermission;
        return this;
    }

    public void build(ForgeConfigSpec.Builder builder) {
        builder.define("enabled", this.enabled);
        builder.define("minTier", this.minTier);
        builder.define("maxTier", this.maxTier);
        builder.defineList("mobList", this.mobList, (o) -> o instanceof String);
        builder.define("mobPermission", this.mobPermission);
    }

    public static AffixesConfig.AffixConfig transform(String affixIdentifier, UnmodifiableConfig config) {
        var affixConfig = new AffixesConfig.AffixConfig();
        affixConfig.identifier = affixIdentifier;
        affixConfig.enabled = config.get("enabled");
        affixConfig.minTier = config.get("minTier");
        if ((int) config.get("maxTier") != -1) affixConfig.maxTier = config.get("maxTier");
        affixConfig.mobList = config.get("mobList");
        affixConfig.mobPermission = config.get("mobPermission");
        return affixConfig;
    }

}
