package net.dreamer.wtsis.effect;

import net.dreamer.wtsis.util.IntColorCalculator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;

public class FieryPassionStatusEffect extends BaseStatusEffect {
    protected FieryPassionStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL,IntColorCalculator.rgbToInt(240,230,140));
    }

    @Override
    public boolean canApplyUpdateEffect(int duration,int amplifier) {
        int i = 25 >> amplifier;

        if (i > 0) return duration % i == 0;
        else return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity,int amplifier) {
        entity.setFireTicks(30);

        super.applyUpdateEffect(entity,amplifier);
    }
}
