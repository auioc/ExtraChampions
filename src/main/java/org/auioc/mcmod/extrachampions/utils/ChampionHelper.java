package org.auioc.mcmod.extrachampions.utils;

import java.util.Random;
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

}
