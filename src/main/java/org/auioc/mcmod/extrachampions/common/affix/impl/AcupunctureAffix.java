package org.auioc.mcmod.extrachampions.common.affix.impl;

import java.util.List;
import java.util.function.Supplier;
import org.auioc.mcmod.extrachampions.api.affix.AffixBasicConfig;
import org.auioc.mcmod.extrachampions.api.affix.ExtraAffix;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;

public class AcupunctureAffix extends ExtraAffix<AcupunctureAffix.Config> {

    private static final Supplier<AffixBasicConfig> BASIC_CONFIG = () -> new AffixBasicConfig().setMobList(List.of("minecraft:creeper"));

    private AttributeModifier modifier;

    public AcupunctureAffix() {
        super("acupuncture", AffixCategory.OFFENSE, BASIC_CONFIG, Config::new);
    }

    @Override
    public void onSpawn(IChampion champion) {
        var attr = champion.getLivingEntity().getAttribute(Attributes.ATTACK_DAMAGE);
        if (attr == null) {
            this.warn(champion);
            return;
        }

        var modifier = getModifier();
        if (!attr.hasModifier(modifier)) {
            attr.addPermanentModifier(modifier);
        }
    }

    @Override
    public boolean onAttack(IChampion champion, LivingEntity target, DamageSource source, float amount) {
        source.bypassMagic();
        return super.onAttack(champion, target, source, amount);
    }

    private AttributeModifier getModifier() {
        if (this.modifier == null) {
            this.modifier = new AttributeModifier(
                "Acupuncture affix bonus", this.config.attackDamageMultiplier, AttributeModifier.Operation.MULTIPLY_TOTAL
            );
        }
        return this.modifier;
    }

    protected static class Config {
        public double attackDamageMultiplier = 1.0D - 0.25D;
    }

}
