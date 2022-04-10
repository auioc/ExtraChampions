package org.auioc.mcmod.extrachampions.common.affix.impl;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;
import top.theillusivec4.champions.common.affix.core.BasicAffix;

public class DisarmoringAffix extends BasicAffix {

    public DisarmoringAffix() {
        super("disarmoring", AffixCategory.OFFENSE);
    }

    @Override
    public boolean onAttack(IChampion champion, LivingEntity target, DamageSource source, float amount) {
        if (target instanceof ServerPlayer) {
            var player = (ServerPlayer) target;
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
