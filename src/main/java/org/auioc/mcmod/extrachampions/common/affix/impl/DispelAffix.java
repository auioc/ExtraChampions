package org.auioc.mcmod.extrachampions.common.affix.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;
import org.apache.commons.lang3.ObjectUtils.Null;
import org.auioc.mcmod.arnicalib.game.effect.MobEffectPredicates;
import org.auioc.mcmod.extrachampions.api.affix.AffixBasicConfig;
import org.auioc.mcmod.extrachampions.api.affix.ExtraAffix;
import org.auioc.mcmod.extrachampions.utils.ChampionHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;

public class DispelAffix extends ExtraAffix<Null> {

    private static final Supplier<AffixBasicConfig> BASIC_CONFIG = () -> new AffixBasicConfig().setMobList(List.of("minecraft:creeper"));

    private static final IntUnaryOperator CHANCE = (tier) -> 50 + tier * 10;

    public DispelAffix() {
        super("dispel", AffixCategory.OFFENSE, BASIC_CONFIG, () -> null);
    }

    @Override
    public boolean onAttack(IChampion champion, LivingEntity target, DamageSource source, float amount) {
        if (ChampionHelper.chanceBasedOnTier(champion, CHANCE)) {
            var effects = target.getActiveEffects();
            if (effects.isEmpty()) {
                return super.onAttack(champion, target, source, amount);
            }
            try {
                target.removeEffect(
                    (effects
                        .stream()
                        .filter(MobEffectPredicates.IS_BENEFICIAL)
                        .skip(ChampionHelper.getRandom(champion).nextLong(effects.size()))
                        .findFirst()
                        .get())
                            .getEffect()
                );
            } catch (NoSuchElementException e) {
            }
        }
        return super.onAttack(champion, target, source, amount);
    }

}
