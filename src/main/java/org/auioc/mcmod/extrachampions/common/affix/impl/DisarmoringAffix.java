package org.auioc.mcmod.extrachampions.common.affix.impl;

import java.util.List;
import java.util.function.Supplier;
import org.apache.commons.lang3.ObjectUtils.Null;
import org.auioc.mcmod.extrachampions.api.affix.AffixBasicConfig;
import org.auioc.mcmod.extrachampions.api.affix.ExtraAffix;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;

public class DisarmoringAffix extends ExtraAffix<Null> {

    private static final Supplier<AffixBasicConfig> BASIC_CONFIG = () -> new AffixBasicConfig()
        .setEnabled(false)
        .setMinTier(2)
        .setMobList(List.of("minecraft:creeper"));

    public DisarmoringAffix() {
        super("disarmoring", AffixCategory.OFFENSE, BASIC_CONFIG, () -> null);
    }

    @Override
    public boolean onAttack(IChampion champion, LivingEntity target, DamageSource source, float amount) {
        if (target instanceof ServerPlayer player) {
            var inventory = player.getInventory();
            var itemToDrop = inventory.getArmor(champion.getLivingEntity().getRandom().nextInt(4));
            if (!EnchantmentHelper.hasBindingCurse(itemToDrop)) {
                inventory.removeItem(itemToDrop);
                player.drop(itemToDrop, false);
            }
        }
        return super.onAttack(champion, target, source, amount);
    }

}
