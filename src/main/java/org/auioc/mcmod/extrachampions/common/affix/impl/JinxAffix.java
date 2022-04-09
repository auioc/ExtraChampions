package org.auioc.mcmod.extrachampions.common.affix.impl;

import java.util.function.ToIntFunction;
import org.auioc.mcmod.arnicalib.utils.java.RandomUtils;
import org.auioc.mcmod.extrachampions.api.affix.ExtraAffix;
import org.auioc.mcmod.extrachampions.utils.ChampionHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;
import top.theillusivec4.champions.common.rank.Rank;

public class JinxAffix extends ExtraAffix<JinxAffix.Config> {

    private static final ToIntFunction<Rank> EFFECT_AMPLIFIER = (rank) -> RandomUtils.nextInt(0, rank.getTier() + 1); // effectLevel∈[1, tier+2)

    public JinxAffix() {
        super("jinx", AffixCategory.OFFENSE, Config::new);
    }

    @Override
    public float onDamage(IChampion champion, DamageSource source, float amount, float newAmount) {
        if (source.getEntity() instanceof LivingEntity) {
            ((LivingEntity) source.getEntity()).addEffect(
                new MobEffectInstance(
                    MobEffects.UNLUCK,
                    this.config.effectDuration,
                    EFFECT_AMPLIFIER.applyAsInt(ChampionHelper.getRank(champion))
                )
            );
        }
        return super.onDamage(champion, source, amount, newAmount);
    }

    protected static class Config {
        public int effectDuration = 5 * 20;
    }

}

