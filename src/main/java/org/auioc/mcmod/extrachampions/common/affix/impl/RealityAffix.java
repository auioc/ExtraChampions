package org.auioc.mcmod.extrachampions.common.affix.impl;

import net.minecraft.world.damagesource.DamageSource;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;
import top.theillusivec4.champions.common.affix.core.BasicAffix;

public class RealityAffix extends BasicAffix {

    private static final float NON_MAGIC_DAMAGE_MULTIPLIER = 1.0F + 0.25F;

    public RealityAffix() {
        super("reality", AffixCategory.DEFENSE);
    }

    @Override
    public boolean onAttacked(IChampion champion, DamageSource source, float amount) {
        if (source.isMagic()) return false;
        return super.onAttacked(champion, source, amount);
    }

    @Override
    public float onDamage(IChampion champion, DamageSource source, float amount, float newAmount) {
        if (source.isMagic()) return 0.0F;
        return amount * NON_MAGIC_DAMAGE_MULTIPLIER;
    }

}
