package org.auioc.mcmod.extrachampions.common.affix.impl;

import java.util.function.Function;
import org.auioc.mcmod.extrachampions.api.affix.ExtraAffix;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;

public class AcupunctureAffix extends ExtraAffix<AcupunctureAffix.Config> {

    private static final Function<Double, AttributeModifier> MODIFIER_BUILDER = (multiplier) -> new AttributeModifier(
        "Acupuncture affix bonus", multiplier, AttributeModifier.Operation.MULTIPLY_TOTAL
    );

    public AcupunctureAffix() {
        super("acupuncture", AffixCategory.OFFENSE, Config::new);
    }

    @Override
    public void onSpawn(IChampion champion) {
        champion
            .getLivingEntity()
            .getAttribute(Attributes.ATTACK_DAMAGE)
            .addPermanentModifier(MODIFIER_BUILDER.apply(this.config.attackDamageMultiplier));
    }

    @Override
    public boolean onAttack(IChampion champion, LivingEntity target, DamageSource source, float amount) {
        source.bypassMagic();
        return super.onAttack(champion, target, source, amount);
    }

    protected static class Config {
        public double attackDamageMultiplier = 1.0D - 0.25D;
    }

}
