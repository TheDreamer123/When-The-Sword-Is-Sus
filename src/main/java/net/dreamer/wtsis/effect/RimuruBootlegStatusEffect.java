package net.dreamer.wtsis.effect;

import net.dreamer.wtsis.WhenTheSwordIsSus;
import net.dreamer.wtsis.misc.WtsisDamageSources;
import net.dreamer.wtsis.util.IntColorCalculator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class RimuruBootlegStatusEffect extends BaseStatusEffect {
    public boolean damage;

    protected RimuruBootlegStatusEffect(boolean damage) {
        super(StatusEffectCategory.BENEFICIAL,IntColorCalculator.rgbToInt(255,99,71));
        this.damage = damage;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration,int amplifier) {
        int i = 50 >> amplifier;
        if (i > 0) return duration % i == 0;
        else return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity,int amplifier) {
        if(damage)
            entity.damage(WtsisDamageSources.SLIMIFY, 1.0F);
        super.applyUpdateEffect(entity,amplifier);
    }

    @Override
    public String getTranslationKey() {
        return Util.createTranslationKey("effect", new Identifier(WhenTheSwordIsSus.MOD_ID, "rimuru_bootleg"));
    }
}
