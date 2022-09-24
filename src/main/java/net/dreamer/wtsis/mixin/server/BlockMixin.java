package net.dreamer.wtsis.mixin.server;

import net.dreamer.wtsis.util.EffectPredicates;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class BlockMixin extends AbstractBlock {
    public BlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(at = @At("HEAD"), method = "onLandedUpon", cancellable = true)
    public void onLandedUpon(World world,BlockState state,BlockPos pos,Entity entity,float fallDistance,CallbackInfo info) {
        if(entity instanceof LivingEntity living && EffectPredicates.shouldBehaveLikeSlime(living)) {
            living.handleFallDamage(fallDistance, 0.0F, DamageSource.FALL);

            info.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "onEntityLand", cancellable = true)
    public void onEntityLandInject(BlockView world,Entity entity,CallbackInfo info) {
        if (entity instanceof LivingEntity living && EffectPredicates.shouldBehaveLikeSlime(living)) {
            Vec3d vec3d = living.getVelocity();
            if (vec3d.y < 0.0D) {
                living.setVelocity(vec3d.x,-vec3d.y,vec3d.z);

                info.cancel();
            }
        }
    }
}
