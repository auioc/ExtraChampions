package org.auioc.mcmod.extrachampions;

import org.auioc.mcmod.extrachampions.common.affix.AffixRegistry;
import org.auioc.mcmod.extrachampions.common.config.ExtraAffixConfig;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public final class Initialization {

    public static void init() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        register(modEventBus);
        handleConfig(modEventBus);
    }

    private static void register(final IEventBus modEventBus) {
        modEventBus.addListener(
            (final FMLCommonSetupEvent event) -> {
                AffixRegistry.register();
            }
        );
    }

    private static void handleConfig(final IEventBus modEventBus) {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ExtraAffixConfig.SPEC, ExtraChampions.MOD_ID + "-affixes.toml");
        modEventBus.addListener(EventPriority.LOWEST, ExtraAffixConfig::rebuildAffixSettings);
    }

}
