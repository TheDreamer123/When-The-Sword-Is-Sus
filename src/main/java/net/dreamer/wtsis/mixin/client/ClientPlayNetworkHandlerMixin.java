package net.dreamer.wtsis.mixin.client;

import net.dreamer.wtsis.item.WtsisItemRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin implements ClientPlayPacketListener {
    @Shadow @Final private MinecraftClient client;
    @Shadow private ClientWorld world;

    private static ItemStack getAttackerTotemSword(PlayerEntity player) {
        LivingEntity attacker = player.getAttacker();
        if(attacker != null) {
            ItemStack itemStack = attacker.getStackInHand(Hand.MAIN_HAND);
            if (itemStack.isOf(WtsisItemRegistry.TOTEM_SWORD)) return itemStack;
        }

        return new ItemStack(WtsisItemRegistry.TOTEM_SWORD);
    }

    @Inject(at = @At("HEAD"), method = "onEntityStatus", cancellable = true)
    private void getActiveTotemOfUndyingInject(EntityStatusS2CPacket packet,CallbackInfo info) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Entity entity = packet.getEntity(this.world);

        if(entity != null) {
            if (packet.getStatus() == (byte) 909438957) {
                this.client.particleManager.addEmitter(entity,ParticleTypes.TOTEM_OF_UNDYING,30);
                this.world.playSound(entity.getX(),entity.getY(),entity.getZ(),SoundEvents.ITEM_TOTEM_USE,entity.getSoundCategory(),1.0F,1.0F,false);
                if (entity == this.client.player)
                    this.client.gameRenderer.showFloatingItem(getAttackerTotemSword(this.client.player));

                info.cancel();
            }
            if (packet.getStatus() == (byte) 1232446678) {
                this.client.particleManager.addEmitter(entity,ParticleTypes.TOTEM_OF_UNDYING,30);
                this.world.playSound(entity.getX(),entity.getY(),entity.getZ(),SoundEvents.ITEM_TOTEM_USE,entity.getSoundCategory(),1.0F,1.0F,false);
                if (entity == this.client.player)
                    this.client.gameRenderer.showFloatingItem(new ItemStack(WtsisItemRegistry.TOTEM_SWORD));

                info.cancel();
            }
        }
    }
}
