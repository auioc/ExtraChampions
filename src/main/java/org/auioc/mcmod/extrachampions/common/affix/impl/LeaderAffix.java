package org.auioc.mcmod.extrachampions.common.affix.impl;

import org.auioc.mcmod.arnicalib.game.entity.EntityPredicates;
import org.auioc.mcmod.extrachampions.api.affix.AffixBasicConfig;
import org.auioc.mcmod.extrachampions.api.affix.ExtraAffix;
import org.auioc.mcmod.extrachampions.utils.ChampionHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;

public class LeaderAffix extends ExtraAffix<LeaderAffix.Config> {

    private static final int INTERVAL = 15 * 20;

    public LeaderAffix() {
        super("leader", AffixCategory.CC, () -> new AffixBasicConfig().setMinTier(3), Config::new);
    }

    @Override
    public void onServerUpdate(IChampion champion) {
        var living = champion.getLivingEntity();
        if (living.tickCount % INTERVAL != 0) return;

        ChampionHelper.getLivingFromLevel(champion, this.config.searchRadiusOfTeammate, EntityPredicates.IS_MONSTER)
            .forEach((teammate) -> {
                if (teammate.equals(living)) return;
                teammate.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, INTERVAL, 2));
            });
    }

    protected static class Config {
        public int searchRadiusOfTeammate = 10;
    }

}
