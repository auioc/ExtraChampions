package org.auioc.mcmod.extrachampions.common.affix.impl;

import static org.auioc.mcmod.extrachampions.ExtraChampions.LOGGER;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Marker;
import org.auioc.mcmod.arnicalib.utils.LogUtil;
import org.auioc.mcmod.arnicalib.utils.java.RandomUtils;
import org.auioc.mcmod.extrachampions.api.affix.ExtraAffix;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.champions.api.AffixCategory;
import top.theillusivec4.champions.api.IChampion;

public class ThiefAffix extends ExtraAffix<ThiefAffix.Config> {

    private static final Marker MARKER = LogUtil.getMarker(ThiefAffix.class);

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
            var list = new ArrayList<Item>();
            this.config.stealableItems
                .stream()
                .map(ResourceLocation::new)
                .forEach((id) -> {
                    var item = ForgeRegistries.ITEMS.getValue(id);
                    if (item == null) LOGGER.warn(MARKER, "Unknown item '" + id + "'");
                    else list.add(item);
                });
            this.stealableItems = list;
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
