package net.dreamer.wtsis.item;

import net.dreamer.wtsis.effect.WtsisStatusEffectRegistry;
import net.dreamer.wtsis.util.SecondsToTicksConverter;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class FlintAndSteelSwordItem extends EdibleSwordItem {
    public FlintAndSteelSwordItem(ToolMaterial toolMaterial,int attackDamage,float attackSpeed,FoodComponent component,SoundEvent eatSound) {
        super(toolMaterial,attackDamage,attackSpeed,component,eatSound);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity playerEntity = context.getPlayer();
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        if (!CampfireBlock.canBeLit(blockState) && !CandleBlock.canBeLit(blockState) && !CandleCakeBlock.canBeLit(blockState) && !blockState.isOf(Blocks.TNT)) {
            BlockPos blockPos2 = blockPos.offset(context.getSide());
            if (AbstractFireBlock.canPlaceAt(world, blockPos2, context.getPlayerFacing())) {
                world.playSound(playerEntity, blockPos2, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
                BlockState blockState2 = AbstractFireBlock.getState(world, blockPos2);
                world.setBlockState(blockPos2, blockState2, 11);
                world.emitGameEvent(playerEntity, GameEvent.BLOCK_PLACE, blockPos);
                ItemStack itemStack = context.getStack();
                if (playerEntity instanceof ServerPlayerEntity) {
                    Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)playerEntity, blockPos2, itemStack);
                    itemStack.damage(1, playerEntity, (p) -> p.sendToolBreakStatus(context.getHand()));
                }

                return ActionResult.success(world.isClient());
            } else return ActionResult.FAIL;
        } else {
            if(!blockState.isOf(Blocks.TNT)) {
                world.playSound(playerEntity,blockPos,SoundEvents.ITEM_FLINTANDSTEEL_USE,SoundCategory.BLOCKS,1.0F,world.getRandom().nextFloat() * 0.4F + 0.8F);
                world.setBlockState(blockPos,blockState.with(Properties.LIT,true),11);
                world.emitGameEvent(playerEntity,GameEvent.BLOCK_CHANGE,blockPos);
            } else {
                TntBlock.primeTnt(world,blockPos);
                world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 11);
            }
            if (playerEntity != null)
                context.getStack().damage(1, playerEntity, (p) -> p.sendToolBreakStatus(context.getHand()));


            return ActionResult.success(world.isClient());
        }
    }

    @Override
    public ItemStack finishUsing(ItemStack stack,World world,LivingEntity user) {
        user.addStatusEffect(new StatusEffectInstance(WtsisStatusEffectRegistry.FIERY_PASSION,SecondsToTicksConverter.secondsToTicks(30)));

        return super.finishUsing(stack,world,user);
    }
}
