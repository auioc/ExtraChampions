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
                add("minecraft:amethyst_shard");
                add("minecraft:creeper_head");
                add("minecraft:diamond");
                add("minecraft:dragon_egg");
                add("minecraft:dragon_head");
                add("minecraft:elytra");
                add("minecraft:emerald");
                add("minecraft:enchanted_golden_apple");
                add("minecraft:filled_map");
                add("minecraft:gold_ingot");
                add("minecraft:golden_apple");
                add("minecraft:heart_of_the_sea");
                add("minecraft:iron_ingot");
                add("minecraft:lapis_lazuli");
                add("minecraft:nether_star");
                add("minecraft:netherite_ingot");
                add("minecraft:player_head");
                add("minecraft:potion");
                add("minecraft:skeleton_skull");
                add("minecraft:totem_of_undying");
                add("minecraft:wither_skeleton_skull");
                add("minecraft:zombie_head");
            }
        };
    }

}
