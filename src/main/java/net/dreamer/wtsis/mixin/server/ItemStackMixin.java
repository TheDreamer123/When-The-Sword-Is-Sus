package net.dreamer.wtsis.mixin.server;

import net.dreamer.wtsis.item.WtsisItemRegistry;
import net.dreamer.wtsis.misc.WtsisDamageSources;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract boolean isOf(Item item);

    @Inject(at = @At("HEAD"), method = "damage(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V")
    public <T extends LivingEntity> void damageInject$V$AEB(int amount,T entity,Consumer<T> breakCallback,CallbackInfo info) {
        if(this.isOf(WtsisItemRegistry.GLASS_SWORD)) {
            entity.world.playSound(null,entity.getBlockPos(),SoundEvents.BLOCK_GLASS_BREAK,SoundCategory.NEUTRAL,1.0F,new Random().nextFloat(0.5F,1.0F));
            entity.damage(WtsisDamageSources.GLASS_SHARDS,4.0F);
        }
    }

    @Inject(at = @At("HEAD"), method = "damage(ILnet/minecraft/util/math/random/Random;Lnet/minecraft/server/network/ServerPlayerEntity;)Z")
    public void damageInject$Z$ARP(int amount,net.minecraft.util.math.random.Random random,@Nullable ServerPlayerEntity player,CallbackInfoReturnable<Boolean> cir) {
        if(player != null && this.isOf(WtsisItemRegistry.GLASS_SWORD)) {
            player.world.playSound(null,player.getBlockPos(),SoundEvents.BLOCK_GLASS_BREAK,SoundCategory.NEUTRAL,1.0F,new Random().nextFloat(0.5F,1.0F));
            player.damage(WtsisDamageSources.GLASS_SHARDS,4.0F);
        }
    }
}
