package org.auioc.mcmod.extrachampions.common.affix.impl;

import org.auioc.mcmod.extrachampions.api.affix.ExtraAffix;
import net.minecraft.world.damagesource.DamageSource;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;

public class FireeaterAffix extends ExtraAffix<FireeaterAffix.Config> {

    public FireeaterAffix() {
        super("fireeater", AffixCategory.DEFENSE, Config::new);
    }

    @Override
    public boolean onAttacked(IChampion champion, DamageSource source, float amount) {
        if (source.isFire()) {
            return false;
        }
        return super.onAttacked(champion, source, amount);
    }

    @Override
    public void onServerUpdate(IChampion champion) {
        var living = champion.getLivingEntity();
        if (living.tickCount % 20 != 0) return;
        if (living.isOnFire()) {
            living.heal((float) this.config.healthHealedPreSecond);
            living.clearFire();
        }
    }

    protected static class Config {
        public double healthHealedPreSecond = 10.0D;
    }

}
