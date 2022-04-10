package org.auioc.mcmod.extrachampions.common.affix.impl;

import java.util.function.IntUnaryOperator;
import org.auioc.mcmod.extrachampions.api.affix.AffixBasicConfig;
import org.auioc.mcmod.extrachampions.api.affix.EmptyAffixExtraConfig;
import org.auioc.mcmod.extrachampions.api.affix.ExtraAffix;
import org.auioc.mcmod.extrachampions.utils.ChampionHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;

public class StickyAffix extends ExtraAffix<EmptyAffixExtraConfig> {

    private static final IntUnaryOperator CHANCE = (tier) -> 30 + tier * 10;

    public StickyAffix() {
        super("sticky", AffixCategory.OFFENSE, () -> new AffixBasicConfig().setMinTier(2), EmptyAffixExtraConfig::new);
    }

    @Override
    public boolean onAttacked(IChampion champion, DamageSource source, float amount) {
        if (
            source.getEntity() instanceof ServerPlayer
                && ChampionHelper.chanceBasedOnTier(champion, CHANCE)
        ) {
            var player = (ServerPlayer) source.getEntity();
            var inventory = player.getInventory();
            var itemToDrop = inventory.getSelected();
            if (!EnchantmentHelper.hasBindingCurse(itemToDrop)) {
                inventory.removeItem(itemToDrop);
                player.drop(itemToDrop, false);
            }
        }
        return super.onAttacked(champion, source, amount);
    }

}
