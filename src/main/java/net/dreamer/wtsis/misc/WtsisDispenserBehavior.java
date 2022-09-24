package net.dreamer.wtsis.misc;

import net.dreamer.wtsis.entity.EnchantedGoldenAppleEntity;
import net.dreamer.wtsis.item.WtsisItemRegistry;
import net.minecraft.block.*;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public interface WtsisDispenserBehavior {
    static void register() {
        DispenserBlock.registerBehavior(WtsisItemRegistry.GOLDEN_APPLE_SWORD, new ItemDispenserBehavior() {
            public ItemStack dispenseSilently(BlockPointer pointer,ItemStack stack) {
                if(!stack.hasEnchantments()) {
                    Direction direction = pointer.getBlockState().get(DispenserBlock.FACING);
                    Position position = DispenserBlock.getOutputLocation(pointer);
                    ItemStack itemStack = stack.split(1);
                    spawnItem(pointer.getWorld(), itemStack, 6, direction, position);
                    return stack;
                }

                ProjectileDispenserBehavior behavior = new ProjectileDispenserBehavior() {
                    protected ProjectileEntity createProjectile(World world,Position position,ItemStack stack) {
                        stack.increment(1);
                        EnchantedGoldenAppleEntity enchantedGoldenApple = new EnchantedGoldenAppleEntity(world,position.getX(),position.getY(),position.getZ());
                        if(stack.hasEnchantments()) {
                            int j = EnchantmentHelper.getLevel(Enchantments.SHARPNESS,stack);
                            if (j > 0) enchantedGoldenApple.setDamage(j * 0.5F + 0.5F);
                            int k = EnchantmentHelper.getLevel(Enchantments.KNOCKBACK,stack);
                            if (k > 0) enchantedGoldenApple.setKnockback(k);
                            int l = EnchantmentHelper.getLevel(Enchantments.FIRE_ASPECT,stack);
                            if (l > 0) enchantedGoldenApple.setOnFireFor(l * 2);
                            if (stack.damage(22,world.random,null))
                                stack.setCount(0);
                        } else enchantedGoldenApple.discard();

                        return enchantedGoldenApple;
                    }
                };
                return behavior.dispenseSilently(pointer,stack);
            }
        });
        DispenserBlock.registerBehavior(WtsisItemRegistry.FLINT_AND_STEEL_SWORD, new FallibleItemDispenserBehavior() {
            protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                World world = pointer.getWorld();
                this.setSuccess(true);
                Direction direction = pointer.getBlockState().get(DispenserBlock.FACING);
                BlockPos blockPos = pointer.getPos().offset(direction);
                BlockState blockState = world.getBlockState(blockPos);
                if (AbstractFireBlock.canPlaceAt(world, blockPos, direction)) {
                    world.setBlockState(blockPos, AbstractFireBlock.getState(world, blockPos));
                    world.emitGameEvent(null, GameEvent.BLOCK_PLACE, blockPos);
                } else if (!CampfireBlock.canBeLit(blockState) && !CandleBlock.canBeLit(blockState) && !CandleCakeBlock.canBeLit(blockState)) {
                    if (blockState.getBlock() instanceof TntBlock) {
                        TntBlock.primeTnt(world, blockPos);
                        world.removeBlock(blockPos, false);
                    } else {
                        this.setSuccess(false);
                    }
                } else {
                    world.setBlockState(blockPos,blockState.with(Properties.LIT, true));
                    world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, blockPos);
                }

                if (this.isSuccess() && stack.damage(1, world.random,null)) {
                    stack.setCount(0);
                }

                return stack;
            }
        });
    }
}
