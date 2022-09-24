package net.dreamer.wtsis.mixin.server;

import net.dreamer.wtsis.effect.WtsisStatusEffectRegistry;
import net.dreamer.wtsis.misc.WtsisDamageSources;
import net.dreamer.wtsis.util.EffectPredicates;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public World world;
    @Shadow protected abstract void playStepSound(BlockPos pos,BlockState state);

    @Inject(at = @At("HEAD"), method = "isInvulnerableTo", cancellable = true)
    public void isInvulnerableToInject(DamageSource source,CallbackInfoReturnable<Boolean> cir) {
        if(source == WtsisDamageSources.EAT_GLASS || source == WtsisDamageSources.EAT_WET_BREAD) cir.setReturnValue(false);
    }

    @Redirect(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;playStepSound(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V"))
    public void slimy(Entity instance,BlockPos pos,BlockState state) {
        if(instance instanceof LivingEntity living && EffectPredicates.hasEffects(living,WtsisStatusEffectRegistry.RIMURU_BOOTLEG_DAMAGE,WtsisStatusEffectRegistry.RIMURU_BOOTLEG_DAMAGEN_T)) {
            if (!state.getMaterial().isLiquid()) {
                BlockState blockState = instance.world.getBlockState(pos.up());
                instance.playSound(BlockSoundGroup.SLIME.getStepSound(),blockState.getSoundGroup().getVolume() * 0.15F,blockState.getSoundGroup().getPitch());

                return;
            }
        }

        this.playStepSound(pos,state);
    }
}
