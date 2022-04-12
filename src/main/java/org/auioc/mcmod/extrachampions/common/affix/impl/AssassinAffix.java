package org.auioc.mcmod.extrachampions.common.affix.impl;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;
import top.theillusivec4.champions.common.affix.core.BasicAffix;

public class AssassinAffix extends BasicAffix {

    public AssassinAffix() {
        super("assassin", AffixCategory.OFFENSE);
    }

    @Override
    public boolean onAttacked(IChampion champion, DamageSource source, float amount) {
        var living = champion.getLivingEntity();
        if (living.getHealth() == living.getMaxHealth() && source.getEntity() instanceof LivingEntity target) {
            var viewVec = target.getViewVector(1.0F).reverse();
            living.teleportTo(target.getX() + viewVec.x(), target.getY(), target.getZ() + viewVec.z());
            return false;
        }
        return super.onAttacked(champion, source, amount);
    }


}
