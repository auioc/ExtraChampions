package org.auioc.mcmod.extrachampions.common.affix.impl;

import java.util.NoSuchElementException;
import java.util.function.ToIntFunction;
import org.auioc.mcmod.arnicalib.utils.game.EffectUtils;
import org.auioc.mcmod.arnicalib.utils.java.RandomUtils;
import org.auioc.mcmod.extrachampions.utils.ChampionHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;
import top.theillusivec4.champions.common.affix.core.BasicAffix;
import top.theillusivec4.champions.common.rank.Rank;


public class DispelAffix extends BasicAffix {

    private static final ToIntFunction<Rank> CHANCE = (rank) -> 50 + rank.getTier() * 10;

    public DispelAffix() {
        super("dispel", AffixCategory.OFFENSE);
    }

    @Override
    public boolean onAttack(IChampion champion, LivingEntity target, DamageSource source, float amount) {
        var random = champion.getLivingEntity().getRandom();
        if (RandomUtils.percentageChance(CHANCE.applyAsInt(ChampionHelper.getRank(champion)), random)) {
            var effects = target.getActiveEffects();
            if (effects.isEmpty()) {
                return super.onAttack(champion, target, source, amount);
            }
            try {
                target.removeEffect(
                    (effects
                        .stream()
                        .filter(EffectUtils.IS_BENEFICIAL)
                        .skip(random.nextLong(effects.size()))
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
