package org.auioc.mcmod.extrachampions.server.advancement.criterion;

import com.google.gson.JsonObject;
import org.auioc.mcmod.extrachampions.ExtraChampions;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class ItemStolenTrigger extends SimpleCriterionTrigger<ItemStolenTrigger.Instance> {

    private static final ResourceLocation ID = new ResourceLocation(ExtraChampions.MOD_ID, "item_stolen");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected Instance createInstance(JsonObject json, EntityPredicate.Composite player, DeserializationContext parser) {
        return new Instance(player, ItemPredicate.fromJson(json.get("item")));
    }

    public void trigger(ServerPlayer player, ItemStack stolenItem) {
        this.trigger(player, (instance) -> instance.test(player, stolenItem));
    }

    public static class Instance extends AbstractCriterionTriggerInstance {

        private final ItemPredicate stolenItem;

        public Instance(EntityPredicate.Composite player, ItemPredicate stolenItem) {
            super(ID, player);
            this.stolenItem = stolenItem;
        }

        public boolean test(ServerPlayer player, ItemStack stolenItem) {
            return this.stolenItem.matches(stolenItem);
        }

    }

}
