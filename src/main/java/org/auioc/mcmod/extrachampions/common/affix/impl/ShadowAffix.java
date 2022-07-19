package org.auioc.mcmod.extrachampions.common.affix.impl;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;
import top.theillusivec4.champions.common.affix.core.BasicAffix;

public class ShadowAffix extends BasicAffix {

    private static final int INTERVAL = 3 * 20;
    private static final int EFFECT_DURATION = 4 * 20;

    public ShadowAffix() {
        super("shadow", AffixCategory.DEFENSE);
    }

    @Override
    public void onServerUpdate(IChampion champion) {
        var living = champion.getLivingEntity();
        if (living.tickCount % INTERVAL != 0) return;
        if (isDarkEnough(living.getLevel(), living.blockPosition())) {
            living.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, EFFECT_DURATION));
        }
    }

    private static boolean isDarkEnough(Level level, BlockPos pos) {
        return level.getRawBrightness(pos, level.getSkyDarken()) < 8;
    }

}
