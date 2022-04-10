package org.auioc.mcmod.extrachampions.common.affix.impl;

import java.util.function.ToIntFunction;
import org.auioc.mcmod.arnicalib.utils.java.RandomUtils;
import org.auioc.mcmod.extrachampions.utils.ChampionHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;
import top.theillusivec4.champions.common.affix.core.BasicAffix;
import top.theillusivec4.champions.common.rank.Rank;

public class StickyAffix extends BasicAffix {

    private static final ToIntFunction<Rank> CHANCE = (rank) -> 50 + rank.getTier() * 10;

    public StickyAffix() {
        super("sticky", AffixCategory.OFFENSE);
    }

    @Override
    public boolean onAttacked(IChampion champion, DamageSource source, float amount) {
        if (
            source.getEntity() instanceof ServerPlayer
                && RandomUtils.percentageChance(CHANCE.applyAsInt(ChampionHelper.getRank(champion)), champion.getLivingEntity().getRandom())
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
