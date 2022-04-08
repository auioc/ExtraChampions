package org.auioc.mcmod.extrachampions.common.affix.impl;

import java.util.Random;
import java.util.function.ToDoubleFunction;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;
import top.theillusivec4.champions.common.affix.core.BasicAffix;

public class HurricaneAffix extends BasicAffix {

    private static final int CONFUSION_EFFECT_DURATION = 15 * 20;
    private static final double TELEPORT_RADIUS = 32.0D;
    private static final ToDoubleFunction<Random> TELEPORT_OFFSET = (random) -> random.nextDouble(TELEPORT_RADIUS) * (random.nextBoolean() ? 1 : -1);

    public HurricaneAffix() {
        super("hurricane", AffixCategory.OFFENSE);
    }

    @Override
    public boolean onDeath(IChampion champion, DamageSource source) {
        if (source.getEntity() instanceof LivingEntity) {
            var target = (LivingEntity) source.getEntity();
            target.addEffect(new MobEffectInstance(MobEffects.CONFUSION, CONFUSION_EFFECT_DURATION));
            randomTeleport(target);
        }
        return super.onDeath(champion, source);
    }

    private static void randomTeleport(LivingEntity living) {
        var random = living.getRandom();
        var level = living.level;
        double entityHeight = living.getBbHeight();
        double x = living.getX() + TELEPORT_OFFSET.applyAsDouble(random);
        double y = Mth.clamp(living.getY() + TELEPORT_OFFSET.applyAsDouble(random), level.getMinBuildHeight() + entityHeight, level.getMaxBuildHeight() - entityHeight);
        double z = living.getZ() + TELEPORT_OFFSET.applyAsDouble(random);
        living.teleportTo(x, y, z);
    }

}
