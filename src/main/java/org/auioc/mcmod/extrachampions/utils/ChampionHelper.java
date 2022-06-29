package org.auioc.mcmod.extrachampions.utils;

import java.util.List;
import java.util.Random;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import org.auioc.mcmod.arnicalib.utils.java.RandomUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import top.theillusivec4.champions.api.IChampion;
import top.theillusivec4.champions.common.rank.Rank;
import top.theillusivec4.champions.common.rank.RankManager;

public class ChampionHelper extends top.theillusivec4.champions.common.util.ChampionHelper {

    public static Rank getRank(IChampion champion) {
        return champion.getServer().getRank().orElse(RankManager.getLowestRank());
    }

    public static int getTier(IChampion champion) {
        return getRank(champion).getTier();
    }

    public static Random getRandom(IChampion champion) {
        return champion.getLivingEntity().getRandom();
    }

    public static boolean chanceBasedOnTier(IChampion champion, IntUnaryOperator chance) {
        return RandomUtils.percentageChance(chance.applyAsInt(ChampionHelper.getTier(champion)), getRandom(champion));
    }

    public static AABB createAABB(IChampion champion, double radius) {
        return (new AABB(champion.getLivingEntity().blockPosition())).inflate(radius).expandTowards(0.0D, radius, 0.0D);
    }

    public static List<LivingEntity> getLivingFromLevel(IChampion champion, double radius, Predicate<? super LivingEntity> predicate) {
        return champion.getLivingEntity().getLevel().getEntitiesOfClass(LivingEntity.class, createAABB(champion, radius), predicate);
    }

}
