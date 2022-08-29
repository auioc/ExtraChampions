package org.auioc.mcmod.extrachampions.api.affix;

import static org.auioc.mcmod.extrachampions.ExtraChampions.LOGGER;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.function.Supplier;
import org.apache.logging.log4j.Marker;
import org.auioc.mcmod.arnicalib.utils.LogUtil;
import org.auioc.mcmod.extrachampions.api.exception.AutoConfigBuildingException;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.electronwill.nightconfig.core.conversion.ObjectConverter;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;
import top.theillusivec4.champions.common.affix.core.BasicAffix;

public abstract class ExtraAffix<C> extends BasicAffix {

    private static final Marker MARKER = LogUtil.getMarker(ExtraAffix.class);

    protected final C config;
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
        if (config != null) {
            new ObjectConverter().toObject(config, this.config);
            onExtraConfigUpdate();
        }
    }

    protected void onExtraConfigUpdate() {}

    public void buildConfig(ForgeConfigSpec.Builder builder) {
        this.basicConfig.build(builder);
        if (this.config != null) {
            builder.push("extra");
            buildExtraConfig(builder);
            builder.pop();
        }
    };

    protected void buildExtraConfig(ForgeConfigSpec.Builder builder) {
        try {
            for (Field filed : this.config.getClass().getFields()) {
                var name = filed.getName();
                var value = filed.get(this.config);
                if (value instanceof Integer || value instanceof Double || value instanceof Boolean || value instanceof String) {
                    builder.define(name, value);
                } else if (value instanceof ArrayList<?>) {
                    builder.defineList(name, (ArrayList<?>) value, (o) -> true);
                } else {
                    throw new AutoConfigBuildingException("Unsupported value type: " + value.getClass());
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new AutoConfigBuildingException("Build config of extra affix '" + this.getIdentifier() + "' failed", e);
        }
    }

    protected void warn(IChampion champion) {
        LOGGER.warn(MARKER, "Affix '" + this.getIdentifier() + "' should not be applied to entity '" + ForgeRegistries.ENTITIES.getKey(champion.getLivingEntity().getType()) + "', please add it to the blacklist");
    }

}
