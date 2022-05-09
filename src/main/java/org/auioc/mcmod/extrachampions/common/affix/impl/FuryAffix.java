package org.auioc.mcmod.extrachampions.common.affix.impl;

import java.util.List;
import java.util.function.Supplier;
import org.auioc.mcmod.extrachampions.api.affix.AffixBasicConfig;
import org.auioc.mcmod.extrachampions.api.affix.ExtraAffix;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;

public class FuryAffix extends ExtraAffix<FuryAffix.Config> {

    private static final Supplier<AffixBasicConfig> BASIC_CONFIG = () -> new AffixBasicConfig().setMobList(List.of("minecraft:creeper"));

    private AttributeModifier modifier;

    public FuryAffix() {
        super("fury", AffixCategory.OFFENSE, true, BASIC_CONFIG, Config::new);
    }

    @Override
    public void onServerUpdate(IChampion champion) {
        var living = champion.getLivingEntity();
        var attr = living.getAttribute(Attributes.ATTACK_DAMAGE);
        var modifier = getModifier();
        if (isHealthLow(living)) {
            if (!attr.hasModifier(modifier)) {
                attr.addTransientModifier(modifier);
            }
        } else {
            attr.removeModifier(modifier);
        }
    }

    private boolean isHealthLow(LivingEntity living) {
        return living.getHealth() < living.getMaxHealth() * this.config.ratioOfHealth;
    }

    private AttributeModifier getModifier() {
        if (this.modifier == null) {
            this.modifier = new AttributeModifier(
                "Fury affix bonus", this.config.attackDamageMultiplier, AttributeModifier.Operation.MULTIPLY_TOTAL
            );
        }
        return this.modifier;
    }

    protected static class Config {
        public double ratioOfHealth = 0.2D;
        public double attackDamageMultiplier = 2.0D;
    }

}
