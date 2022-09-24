package net.dreamer.wtsis.criteria;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import net.dreamer.wtsis.WhenTheSwordIsSus;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ConsumeItemWithEnchantmentCriterion extends AbstractCriterion<ConsumeItemWithEnchantmentCriterion.Conditions> {
    static final Identifier ID = new Identifier(WhenTheSwordIsSus.MOD_ID,"consume_item_with_enchantment");

    public ConsumeItemWithEnchantmentCriterion() {
    }

    public Identifier getId() {
        return ID;
    }

    public ConsumeItemWithEnchantmentCriterion.Conditions conditionsFromJson(JsonObject jsonObject,EntityPredicate.Extended extended,AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
        return new ConsumeItemWithEnchantmentCriterion.Conditions(extended, ItemPredicate.fromJson(jsonObject.get("item")));
    }

    public void trigger(ServerPlayerEntity player,ItemStack stack) {
        if(stack.hasEnchantments())
            this.trigger(player, (conditions) -> conditions.matches(stack));
    }

    public static class Conditions extends AbstractCriterionConditions {
        private final ItemPredicate item;

        public Conditions(EntityPredicate.Extended player,ItemPredicate item) {
            super(ConsumeItemWithEnchantmentCriterion.ID, player);
            this.item = item;
        }

        public static ConsumeItemWithEnchantmentCriterion.Conditions predicate(ItemPredicate predicate) {
            return new ConsumeItemWithEnchantmentCriterion.Conditions(EntityPredicate.Extended.EMPTY, predicate);
        }

        public static ConsumeItemWithEnchantmentCriterion.Conditions item(ItemConvertible item) {
            return new ConsumeItemWithEnchantmentCriterion.Conditions(EntityPredicate.Extended.EMPTY, new ItemPredicate(null, ImmutableSet.of(item.asItem()), NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, EnchantmentPredicate.ARRAY_OF_ANY, EnchantmentPredicate.ARRAY_OF_ANY,null, NbtPredicate.ANY));
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
