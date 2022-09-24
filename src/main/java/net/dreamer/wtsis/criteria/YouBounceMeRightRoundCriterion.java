package net.dreamer.wtsis.criteria;

import com.google.gson.JsonObject;
import net.dreamer.wtsis.WhenTheSwordIsSus;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class YouBounceMeRightRoundCriterion extends AbstractCriterion<YouBounceMeRightRoundCriterion.Conditions> {
    static final Identifier ID = new Identifier(WhenTheSwordIsSus.MOD_ID,"you_bounce_me_right_round");

    public YouBounceMeRightRoundCriterion() {
    }

    public Identifier getId() {
        return ID;
    }

    public YouBounceMeRightRoundCriterion.Conditions conditionsFromJson(JsonObject jsonObject,EntityPredicate.Extended extended,AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
        ItemPredicate itemPredicate = ItemPredicate.fromJson(jsonObject.get("item"));
        return new YouBounceMeRightRoundCriterion.Conditions(extended, itemPredicate);
    }

    public void trigger(ServerPlayerEntity player,ItemStack stack) {
        this.trigger(player, (conditions) -> conditions.matches(stack));
    }

    public static class Conditions extends AbstractCriterionConditions {
        private final ItemPredicate item;

        public Conditions(EntityPredicate.Extended player,ItemPredicate item) {
            super(YouBounceMeRightRoundCriterion.ID, player);
            this.item = item;
        }

        public boolean matches(ItemStack stack) {
            return this.item.test(stack);
        }

        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            JsonObject jsonObject = super.toJson(predicateSerializer);
            jsonObject.add("item", this.item.toJson());
            return jsonObject;
        }
    }
}
