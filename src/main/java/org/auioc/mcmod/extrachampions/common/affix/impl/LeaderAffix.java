package org.auioc.mcmod.extrachampions.common.affix.impl;

import java.util.function.Predicate;
import org.auioc.mcmod.extrachampions.api.affix.ExtraAffix;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.phys.AABB;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;

public class LeaderAffix extends ExtraAffix<LeaderAffix.Config> {

    private static final int INTERVAL = 15 * 20;
    private static final Predicate<LivingEntity> IS_MONSTER = (living) -> living.getType().getCategory() == MobCategory.MONSTER;

    public LeaderAffix() {
        super("leader", AffixCategory.CC, Config::new);
    }

    @Override
    public void onServerUpdate(IChampion champion) {
        var living = champion.getLivingEntity();
        if (living.tickCount % INTERVAL != 0) return;

        int raduis = this.config.searchRadiusOfTeammate;
        AABB range = (new AABB(living.blockPosition())).inflate(raduis).expandTowards(0.0D, raduis, 0.0D);

        living.getLevel().getEntitiesOfClass(LivingEntity.class, range, IS_MONSTER)
            .forEach((teammate) -> {
                if (teammate.equals(living)) return;
                teammate.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, INTERVAL, 2));
            });
    }

    protected static class Config {
        public int searchRadiusOfTeammate = 10;
    }

}
