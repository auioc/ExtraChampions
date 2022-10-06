package org.auioc.mcmod.extrachampions.common.affix.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import org.auioc.mcmod.arnicalib.game.item.ItemRegistry;
import org.auioc.mcmod.extrachampions.api.affix.AffixBasicConfig;
import org.auioc.mcmod.extrachampions.api.affix.ExtraAffix;
import org.auioc.mcmod.extrachampions.utils.ChampionHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;

public class StickyAffix extends ExtraAffix<StickyAffix.Config> {

    private static final Supplier<AffixBasicConfig> BASIC_CONFIG = () -> new AffixBasicConfig().setEnabled(false).setMinTier(2);

    private List<Item> blacklist;

    public StickyAffix() {
        super("sticky", AffixCategory.OFFENSE, BASIC_CONFIG, Config::new);
    }

    @Override
    @SuppressWarnings("null")
    public boolean onAttacked(IChampion champion, DamageSource source, float amount) {
        if (source.getEntity() instanceof ServerPlayer && shouldTryPerform(champion)) {
            var player = (ServerPlayer) source.getEntity();
            var inventory = player.getInventory();
            var itemToDrop = inventory.getSelected();
            if (shouldDrop(itemToDrop)) {
                if (shouldApplyCurse(champion, itemToDrop)) {
                    itemToDrop.enchant(Enchantments.BINDING_CURSE, 1);
                }
                inventory.removeItem(itemToDrop);
                player.drop(itemToDrop, false);
            }
        }
        return super.onAttacked(champion, source, amount);
    }

    private boolean shouldTryPerform(IChampion champion) {
        return ChampionHelper.chanceBasedOnTier(champion, (tier) -> this.config.baseChance + tier * this.config.bonusChancePreTier);
    }

    private boolean shouldDrop(ItemStack itemToDrop) {
        return !getBlacklist().contains(itemToDrop.getItem()) && !EnchantmentHelper.hasBindingCurse(itemToDrop);
    }

    private boolean shouldApplyCurse(IChampion champion, ItemStack itemToDrop) {
        return itemToDrop.getItemEnchantability() > 0 && ChampionHelper.chanceBasedOnTier(champion, (tier) -> this.config.baseCurseChance + tier * this.config.bonusCurseChancePreTier);
    }

    private List<Item> getBlacklist() {
        if (this.blacklist == null) {
            this.blacklist = ItemRegistry.getItems(this.config.blacklist);
        }
        return this.blacklist;
    }

    protected static class Config {
        public int baseChance = 30;
        public int bonusChancePreTier = 15;
        public int baseCurseChance = 0;
        public int bonusCurseChancePreTier = 1;
        public List<String> blacklist = new ArrayList<String>();
    }

}
