package org.auioc.mcmod.extrachampions.common.affix.impl;

import java.util.function.Predicate;
import org.auioc.mcmod.extrachampions.api.affix.AffixBasicConfig;
import org.auioc.mcmod.extrachampions.api.affix.ExtraAffix;
import org.auioc.mcmod.extrachampions.utils.ChampionHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.phys.AABB;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;

public class NecromancerAffix extends ExtraAffix<NecromancerAffix.Config> {

    private static final int INTERVAL = 15 * 20;
    private static final Predicate<LivingEntity> IS_UNDEAD = (living) -> living.getMobType() == MobType.UNDEAD;
    private static final MobEffect[] EFFECTS = new MobEffect[] {MobEffects.DAMAGE_BOOST, MobEffects.MOVEMENT_SPEED, MobEffects.DIG_SPEED};

    public NecromancerAffix() {
        super("necromancer", AffixCategory.CC, () -> new AffixBasicConfig().setMinTier(3), Config::new);
    }

    @Override
    public void onServerUpdate(IChampion champion) {
        var living = champion.getLivingEntity();
        if (living.tickCount % INTERVAL != 0) return;

        int raduis = this.config.searchRadiusOfTeammate;
        AABB range = (new AABB(living.blockPosition())).inflate(raduis).expandTowards(0.0D, raduis, 0.0D);

        int effectAmplifier = Math.min((ChampionHelper.getTier(champion) - 1), 1) - 1;
        living.getLevel().getEntitiesOfClass(LivingEntity.class, range, IS_UNDEAD)
            .forEach((teammate) -> {
                for (MobEffect effect : EFFECTS) {
                    teammate.addEffect(new MobEffectInstance(effect, INTERVAL, effectAmplifier));
                }
            });
    }

    protected static class Config {
        public int searchRadiusOfTeammate = 10;
    }

}
