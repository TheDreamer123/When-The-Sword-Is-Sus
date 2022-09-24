package net.dreamer.wtsis.effect;

import net.dreamer.wtsis.criteria.WtsisCriteriaRegistry;
import net.dreamer.wtsis.item.WtsisItemRegistry;
import net.dreamer.wtsis.util.IntColorCalculator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.explosion.Explosion;

public class ExplosionStatusEffect extends BaseStatusEffect {
    protected ExplosionStatusEffect() {
        super(StatusEffectCategory.HARMFUL,IntColorCalculator.rgbToInt(0,100,0));
    }

    @Override
    public void onApplied(LivingEntity entity,AttributeContainer attributes,int amplifier) {
        entity.world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.PLAYERS, 1.0F, 1.0F);
        super.onApplied(entity,attributes,amplifier);
    }

    @Override
    public void onRemoved(LivingEntity entity,AttributeContainer attributes,int amplifier) {
        entity.world.createExplosion(null,entity.getX(),entity.getY(),entity.getZ(),3.0F,Explosion.DestructionType.DESTROY);
        if(entity instanceof ServerPlayerEntity serverPlayer)
            WtsisCriteriaRegistry.EXPLOSIONZILLA.trigger(serverPlayer, new ItemStack(WtsisItemRegistry.GUNPOWDER_SWORD));
        super.onRemoved(entity,attributes,amplifier);
    }
}
