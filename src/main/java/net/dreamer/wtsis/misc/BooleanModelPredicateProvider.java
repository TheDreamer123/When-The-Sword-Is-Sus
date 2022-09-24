package net.dreamer.wtsis.misc;

import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface BooleanModelPredicateProvider extends ModelPredicateProvider {
    default float call(ItemStack stack,@Nullable ClientWorld world,@Nullable LivingEntity entity,int seed) {
        return callBool(stack,world,entity,seed) ? 1.0F : 0.0F;
    }

    boolean callBool(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity, int seed);
}
