package org.auioc.mcmod.extrachampions.common.affix.impl.haruhiism;

import org.auioc.mcmod.extrachampions.api.affix.ExtraAffix;
import net.minecraft.world.damagesource.DamageSource;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;

public class ShinjinAffix extends ExtraAffix<ShinjinAffix.Config> {

    public ShinjinAffix() {
        super("shinjin", AffixCategory.DEFENSE, Config::new);
    }

    @Override
    public float onDamage(IChampion champion, DamageSource source, float amount, float newAmount) {
        if (amount < this.config.demarcationPoint) {
            newAmount = (float) (amount * this.config.leftMultiplier);
        } else {
            newAmount = (float) (amount * this.config.rightMultiplier);
        }
        return newAmount;
    }

    protected static class Config {
        public double demarcationPoint = 28.0D;
        public double leftMultiplier = 0.5D;
        public double rightMultiplier = 2.0D;
    }

}
