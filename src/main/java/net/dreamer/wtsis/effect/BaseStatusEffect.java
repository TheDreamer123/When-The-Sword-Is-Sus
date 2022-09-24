package net.dreamer.wtsis.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class BaseStatusEffect extends StatusEffect {
    protected BaseStatusEffect(StatusEffectCategory statusEffectCategory,int color) {
        super(statusEffectCategory,color);
    }
}
