package net.dreamer.wtsis.mixin.server;

import net.dreamer.wtsis.item.WtsisItemRegistry;
import net.minecraft.block.AbstractCandleBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CandleCakeBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(CandleCakeBlock.class)
public abstract class CandleCakeBlockMixin extends AbstractCandleBlock {
    protected CandleCakeBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(at = @At("HEAD"), locals = LocalCapture.CAPTURE_FAILEXCEPTION, method = "onUse", cancellable = true)
    public void onUseInj1ect(BlockState state,World world,BlockPos pos,PlayerEntity player,Hand hand,BlockHitResult hit,CallbackInfoReturnable<ActionResult> cir) {
        ItemStack stack = player.getStackInHand(hand);
        if(stack.isOf(WtsisItemRegistry.FLINT_AND_STEEL_SWORD)) cir.setReturnValue(ActionResult.PASS);
    }
}
