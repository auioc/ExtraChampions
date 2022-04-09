package org.auioc.mcmod.extrachampions.common.affix.impl;

import org.auioc.mcmod.extrachampions.api.affix.ExtraAffix;
import net.minecraft.world.damagesource.DamageSource;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;

public class RealityAffix extends ExtraAffix<RealityAffix.Config> {

    public RealityAffix() {
        super("reality", AffixCategory.DEFENSE, Config::new);
    }

    @Override
    public boolean onAttacked(IChampion champion, DamageSource source, float amount) {
        if (source.isMagic()) return false;
        return super.onAttacked(champion, source, amount);
    }

    @Override
    public float onDamage(IChampion champion, DamageSource source, float amount, float newAmount) {
        if (source.isMagic()) return 0.0F;
        return amount * (float) this.config.nonMagicDamageMultiplier;
    }

    protected static class Config {
        public double nonMagicDamageMultiplier = 1.0D + 0.25D;
    }

}
