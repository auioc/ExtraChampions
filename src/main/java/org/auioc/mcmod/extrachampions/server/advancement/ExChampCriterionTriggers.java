package org.auioc.mcmod.extrachampions.server.advancement;

import org.auioc.mcmod.extrachampions.server.advancement.criterion.ItemStolenTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class ExChampCriterionTriggers {

    public static void init() {}

    public static final ItemStolenTrigger ITEM_STOLEN = CriteriaTriggers.register(new ItemStolenTrigger());

}
