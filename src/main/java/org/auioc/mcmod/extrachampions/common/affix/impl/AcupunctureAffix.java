package org.auioc.mcmod.extrachampions.common.affix.impl;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;
import top.theillusivec4.champions.common.affix.core.BasicAffix;

public class AcupunctureAffix extends BasicAffix {

    private static final double ATTACK_DAMAGE_MULTIPLIER = 1.0D - 0.25D;
    private static final AttributeModifier ATTACK_DAMAGE_MODIFIER = new AttributeModifier(
        "Acupuncture affix bonus",
        ATTACK_DAMAGE_MULTIPLIER,
        AttributeModifier.Operation.MULTIPLY_TOTAL
    );

    public AcupunctureAffix() {
        super("acupuncture", AffixCategory.OFFENSE);
    }

    @Override
    public void onSpawn(IChampion champion) {
        champion
            .getLivingEntity()
            .getAttribute(Attributes.ATTACK_DAMAGE)
            .addPermanentModifier(ATTACK_DAMAGE_MODIFIER);
    }

    @Override
    public boolean onAttack(IChampion champion, LivingEntity target, DamageSource source, float amount) {
        source.bypassMagic();
        return super.onAttack(champion, target, source, amount);
    }

}
