package org.auioc.mcmod.extrachampions.common.affix.impl;

import java.util.NoSuchElementException;
import java.util.function.IntUnaryOperator;
import org.auioc.mcmod.arnicalib.utils.game.EffectUtils;
import org.auioc.mcmod.extrachampions.utils.ChampionHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;
import top.theillusivec4.champions.common.affix.core.BasicAffix;


public class DispelAffix extends BasicAffix {

    private static final IntUnaryOperator CHANCE = (tier) -> 50 + tier * 10;

    public DispelAffix() {
        super("dispel", AffixCategory.OFFENSE);
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
                        .filter(EffectUtils.IS_BENEFICIAL)
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
