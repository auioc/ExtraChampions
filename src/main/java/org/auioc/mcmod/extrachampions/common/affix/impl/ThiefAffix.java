package org.auioc.mcmod.extrachampions.common.affix.impl;

import java.util.ArrayList;
import java.util.List;
import org.auioc.mcmod.arnicalib.utils.game.ItemUtils;
import org.auioc.mcmod.arnicalib.utils.java.RandomUtils;
import org.auioc.mcmod.extrachampions.api.affix.ExtraAffix;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;

public class ThiefAffix extends ExtraAffix<ThiefAffix.Config> {

    private List<Item> stealableItems;

    public ThiefAffix() {
        super("thief", AffixCategory.OFFENSE, Config::new);
    }

    @Override
    public boolean onAttack(IChampion champion, LivingEntity target, DamageSource source, float amount) {
        if (target instanceof ServerPlayer) {
            var player = (ServerPlayer) target;
            var inventory = player.getInventory();
            var list = inventory.items
                .stream()
                .filter((itemStack) -> getStealableItems().contains(itemStack.getItem()))
                .toList();
            if (!list.isEmpty()) {
                inventory.removeItem(RandomUtils.pickOneFromList(list));
            }
        }
        return super.onAttack(champion, target, source, amount);
    }

    private List<Item> getStealableItems() {
        if (this.stealableItems == null) {
            this.stealableItems = ItemUtils.getItems(this.config.stealableItems);
        }
        return this.stealableItems;
    }

    protected static class Config {
        public List<String> stealableItems = new ArrayList<String>() {
            {
                add("minecraft:iron_ingot");
                add("minecraft:gold_ingot");
                add("minecraft:diamond");
                add("minecraft:netherite_ingot");
                add("minecraft:emerald");
            }
        };
    }

}
