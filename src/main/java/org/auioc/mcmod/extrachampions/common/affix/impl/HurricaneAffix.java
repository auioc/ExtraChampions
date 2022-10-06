package org.auioc.mcmod.extrachampions.common.affix.impl;

import org.auioc.mcmod.arnicalib.game.world.position.RandomTeleporter;
import org.auioc.mcmod.extrachampions.api.affix.ExtraAffix;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;

public class HurricaneAffix extends ExtraAffix<HurricaneAffix.Config> {

    public HurricaneAffix() {
        super("hurricane", AffixCategory.OFFENSE, Config::new);
    }

    @Override
    public boolean onDeath(IChampion champion, DamageSource source) {
        if (source.getEntity() instanceof LivingEntity target) {
            if (
                RandomTeleporter.teleport(
                    target,
                    champion.getLivingEntity().blockPosition(),
                    this.config.teleportRadius,
                    false,
                    this.config.teleportMaxTries
                )
            ) {
                target.addEffect(new MobEffectInstance(MobEffects.CONFUSION, this.config.effectDuration));
            }
        }
        return super.onDeath(champion, source);
    }

    protected static class Config {
        public int effectDuration = 15 * 20;
        public int teleportRadius = 48;
        public int teleportMaxTries = 16;
    }

}
