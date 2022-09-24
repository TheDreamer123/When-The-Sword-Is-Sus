package net.dreamer.wtsis.mixin.server;

import net.dreamer.wtsis.criteria.WtsisCriteriaRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType,World world) {
        super(entityType,world);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/advancement/criterion/ConsumeItemCriterion;trigger(Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/item/ItemStack;)V"), method = "eatFood")
    public void eatFoodInject(World world,ItemStack stack,CallbackInfoReturnable<ItemStack> cir) {
        if((PlayerEntity) (LivingEntity) this instanceof ServerPlayerEntity serverPlayer)
            WtsisCriteriaRegistry.CONSUME_ITEM_WITH_ENCHANTMENT.trigger(serverPlayer, stack);
    }
}
