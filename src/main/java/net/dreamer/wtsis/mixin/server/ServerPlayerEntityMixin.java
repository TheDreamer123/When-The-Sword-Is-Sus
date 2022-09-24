package net.dreamer.wtsis.mixin.server;

import com.mojang.authlib.GameProfile;
import net.dreamer.wtsis.criteria.WtsisCriteriaRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
    @Mutable @Shadow @Final private ScreenHandlerListener screenHandlerListener;

    private ScreenHandlerListener save;

    public ServerPlayerEntityMixin(World world,BlockPos pos,float yaw,GameProfile gameProfile,@Nullable PlayerPublicKey publicKey) {
        super(world,pos,yaw,gameProfile,publicKey);
    }

    @Inject(at = @At("TAIL"), method = "<init>")
    public void constructorInject(CallbackInfo info) {
        save = this.screenHandlerListener;
        this.screenHandlerListener = new ScreenHandlerListener() {
            public void onSlotUpdate(ScreenHandler handler,int slotId,ItemStack stack) {
                Slot slot = handler.getSlot(slotId);
                if (!(slot instanceof CraftingResultSlot))
                    if (slot.inventory == ServerPlayerEntityMixin.this.getInventory()) {
                        WtsisCriteriaRegistry.INVENTORY_CHANGED_WITH_ENCHANTMENTS.trigger((ServerPlayerEntity) (PlayerEntity) ServerPlayerEntityMixin.this,ServerPlayerEntityMixin.this.getInventory(),stack);
                        WtsisCriteriaRegistry.INVENTORY_CHANGED_WITHOUT_ENCHANTMENTS.trigger((ServerPlayerEntity) (PlayerEntity) ServerPlayerEntityMixin.this,ServerPlayerEntityMixin.this.getInventory(),stack);
                        WtsisCriteriaRegistry.DAT_DO_NOT_BE_HOT.trigger((ServerPlayerEntity) (PlayerEntity) ServerPlayerEntityMixin.this,ServerPlayerEntityMixin.this.getInventory(),stack);
                        WtsisCriteriaRegistry.DAT_DO_BE_HOT.trigger((ServerPlayerEntity) (PlayerEntity) ServerPlayerEntityMixin.this,ServerPlayerEntityMixin.this.getInventory(),stack);
                        ServerPlayerEntityMixin.this.save.onSlotUpdate(handler,slotId,stack);
                    }
            }

            public void onPropertyUpdate(ScreenHandler handler, int property, int value) {
            }
        };
    }
}
