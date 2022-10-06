package org.auioc.mcmod.extrachampions.common.affix.impl;

import java.util.Random;
import javax.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;
import org.auioc.mcmod.arnicalib.base.random.RandomUtils;
import org.auioc.mcmod.extrachampions.api.affix.ExtraAffix;
import org.auioc.mcmod.extrachampions.utils.ChampionHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;
import top.theillusivec4.champions.common.rank.RankManager;

public class RealityAffix extends ExtraAffix<RealityAffix.Config> {

    public RealityAffix() {
        super("reality", AffixCategory.DEFENSE, Config::new);
    }

    @Override
    public boolean onAttack(IChampion champion, LivingEntity target, DamageSource source, float amount) {
        if (target instanceof ServerPlayer player && shouldRemoveEnchantment(champion)) removeEnchantment(player, champion);
        return super.onAttack(champion, target, source, amount);
    }

    @Override
    public boolean onAttacked(IChampion champion, DamageSource source, float amount) {
        if (source.getEntity() instanceof ServerPlayer player && shouldRemoveEnchantment(champion)) removeEnchantment(player, champion);
        if (source.isMagic()) return false;
        return super.onAttacked(champion, source, amount);
    }

    @Override
    public float onDamage(IChampion champion, DamageSource source, float amount, float newAmount) {
        if (source.isMagic()) return 0.0F;
        return amount * (float) this.config.nonMagicDamageMultiplier;
    }

    private boolean shouldRemoveEnchantment(IChampion champion) {
        return ChampionHelper.chanceBasedOnTier(champion, (tier) -> this.config.baseEnchRemovalChance + tier * this.config.bonusEnchRemovalChancePreTier);
    }

    protected static class Config {
        public int baseEnchRemovalChance = 6;
        public int bonusEnchRemovalChancePreTier = 3;
        public double nonMagicDamageMultiplier = 1.0D + 0.25D;
    }


    private static void removeEnchantment(ServerPlayer player, IChampion champion) {
        var random = ChampionHelper.getRandom(champion);

        InteractionHand a = InteractionHand.MAIN_HAND;
        InteractionHand b = InteractionHand.OFF_HAND;
        if (random.nextBoolean()) {
            a = InteractionHand.OFF_HAND;
            b = InteractionHand.MAIN_HAND;
        }
        if (removeEnchantment(player.getItemInHand(a), random)) return;
        if (removeEnchantment(player.getItemInHand(b), random)) return;

        var inventory = player.getInventory();

        var armors = inventory.armor.toArray(new ItemStack[inventory.armor.size()]);
        ArrayUtils.shuffle(armors, random);
        for (var stack : armors) if (removeEnchantment(stack, random)) return;

        if (ChampionHelper.getRank(champion).equals(RankManager.getHighestRank())) {
            var invitems = inventory.items.toArray(new ItemStack[inventory.items.size()]);
            ArrayUtils.shuffle(invitems, random);
            for (var stack : invitems) if (removeEnchantment(stack, random)) return;
        }
    }

    private static boolean removeEnchantment(ItemStack stack, @Nullable Random random) {
        boolean isBook = stack.is(Items.ENCHANTED_BOOK);
        var ench = isBook ? EnchantedBookItem.getEnchantments(stack) : stack.getEnchantmentTags();
        if (ench.isEmpty()) return false;
        if (ench.size() == 1) {
            if (isBook) stack.setCount(0);
            else stack.removeTagKey("Enchantments");
        } else {
            ench.remove(((random != null) ? random : RandomUtils.RANDOM).nextInt(0, ench.size()));
        }
        return true;
    }

}
