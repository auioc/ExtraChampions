package org.auioc.mcmod.extrachampions.common.affix.impl;

import java.util.Random;
import java.util.function.ToDoubleBiFunction;
import org.auioc.mcmod.extrachampions.api.affix.ExtraAffix;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;

public class HurricaneAffix extends ExtraAffix<HurricaneAffix.Config> {

    private static final ToDoubleBiFunction<Double, Random> TELEPORT_OFFSET = (radius, random) -> random.nextDouble(radius) * (random.nextBoolean() ? 1 : -1);

    public HurricaneAffix() {
        super("hurricane", AffixCategory.OFFENSE, Config::new);
    }

    @Override
    public boolean onDeath(IChampion champion, DamageSource source) {
        if (source.getEntity() instanceof LivingEntity target) {
            target.addEffect(new MobEffectInstance(MobEffects.CONFUSION, this.config.effectDuration));
            randomTeleport(target, this.config.teleportRaduis);
        }
        return super.onDeath(champion, source);
    }

    private static void randomTeleport(LivingEntity living, double radius) {
        var random = living.getRandom();
        var level = living.level;
        double entityHeight = living.getBbHeight();
        double x = living.getX() + TELEPORT_OFFSET.applyAsDouble(radius, random);
        double y = Mth.clamp(living.getY() + TELEPORT_OFFSET.applyAsDouble(radius, random), level.getMinBuildHeight() + entityHeight, level.getMaxBuildHeight() - entityHeight);
        double z = living.getZ() + TELEPORT_OFFSET.applyAsDouble(radius, random);
        living.teleportTo(x, y, z);
    }

    protected static class Config {
        public int effectDuration = 15 * 20;
        public double teleportRaduis = 32.0D;
    }

}
