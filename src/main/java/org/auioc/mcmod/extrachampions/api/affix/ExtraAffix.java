package org.auioc.mcmod.extrachampions.api.affix;

import java.lang.reflect.Field;
import java.util.function.Supplier;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.electronwill.nightconfig.core.conversion.ObjectConverter;
import net.minecraftforge.common.ForgeConfigSpec;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.common.affix.core.BasicAffix;

public abstract class ExtraAffix<C> extends BasicAffix {

    protected C config;
    private final AffixBasicConfig basicConfig;

    public ExtraAffix(String id, AffixCategory category, boolean hasSubscriptions, Supplier<AffixBasicConfig> basicConfig, Supplier<C> extraConfig) {
        super(id, category, hasSubscriptions);
        this.basicConfig = basicConfig.get();
        this.config = extraConfig.get();
    }

    public ExtraAffix(String id, AffixCategory category, boolean hasSubscriptions, Supplier<C> extraConfig) {
        super(id, category, hasSubscriptions);
        this.basicConfig = new AffixBasicConfig();
        this.config = extraConfig.get();
    }

    public ExtraAffix(String id, AffixCategory category, Supplier<AffixBasicConfig> basicConfig, Supplier<C> extraConfig) {
        this(id, category, false, basicConfig, extraConfig);
    }

    public ExtraAffix(String id, AffixCategory category, Supplier<C> extraConfig) {
        this(id, category, false, extraConfig);
    }

    public void setExtraConfig(UnmodifiableConfig config) {
        new ObjectConverter().toObject(config, this.config);
    }

    public void buildConfig(ForgeConfigSpec.Builder builder) {
        this.basicConfig.build(builder);
        {
            builder.push("extra");
            try {
                for (Field filed : this.config.getClass().getFields()) {
                    builder.define(filed.getName(), filed.get(this.config));
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new RuntimeException("Build config of extra affix '" + this.getIdentifier() + "' failed", e);
            }
            builder.pop();
        }
    };

}
