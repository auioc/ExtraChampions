package org.auioc.mcmod.extrachampions.common.affix.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import org.auioc.mcmod.arnicalib.base.random.RandomUtils;
import org.auioc.mcmod.arnicalib.game.item.ItemRegistry;
import org.auioc.mcmod.extrachampions.api.affix.AffixBasicConfig;
import org.auioc.mcmod.extrachampions.api.affix.ExtraAffix;
import org.auioc.mcmod.extrachampions.server.advancement.ExChampCriterionTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;

public class ThiefAffix extends ExtraAffix<ThiefAffix.Config> {

    private static final Supplier<AffixBasicConfig> BASIC_CONFIG = () -> new AffixBasicConfig()
        .setEnabled(false)
        .setMinTier(2)
        .setMobList(List.of("minecraft:creeper"));

    private List<Item> stealableItems;

    public ThiefAffix() {
        super("thief", AffixCategory.OFFENSE, BASIC_CONFIG, Config::new);
    }

    @Override
    public boolean onAttack(IChampion champion, LivingEntity target, DamageSource source, float amount) {
        if (target instanceof ServerPlayer player) {
            var inventory = player.getInventory();
            var list = inventory.items
                .stream()
                .filter((itemStack) -> getStealableItems().contains(itemStack.getItem()))
                .toList();
            if (!list.isEmpty()) {
                var stolenItem = RandomUtils.pickOneFromList(list);
                inventory.removeItem(stolenItem);
                ExChampCriterionTriggers.ITEM_STOLEN.trigger(player, stolenItem);
            }
        }
        return super.onAttack(champion, target, source, amount);
    }

    private List<Item> getStealableItems() {
        if (this.stealableItems == null) {
            this.stealableItems = ItemRegistry.getItems(this.config.stealableItems);
        }
        return this.stealableItems;
    }

    protected static class Config {
        public List<String> stealableItems = new ArrayList<String>() {
            {
                add("minecraft:amethyst_shard");
                add("minecraft:beacon");
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
