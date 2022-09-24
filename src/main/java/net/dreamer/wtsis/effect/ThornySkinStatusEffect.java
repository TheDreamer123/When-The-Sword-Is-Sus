package net.dreamer.wtsis.effect;

import net.dreamer.wtsis.misc.WtsisDamageSources;
import net.dreamer.wtsis.util.IntColorCalculator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffectCategory;

public class ThornySkinStatusEffect extends BaseStatusEffect {
    public ThornySkinStatusEffect() {
        super(StatusEffectCategory.HARMFUL,IntColorCalculator.rgbToInt(0,180,16));
    }

    @Override
    public void onRemoved(LivingEntity entity,AttributeContainer attributes,int amplifier) {
        entity.damage(WtsisDamageSources.EAT_GLASS,Float.MAX_VALUE);

        super.onRemoved(entity,attributes,amplifier);
    }
}
