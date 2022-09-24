package net.dreamer.wtsis.util;

import net.dreamer.wtsis.effect.WtsisStatusEffectRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;

public class EffectPredicates {
    public static boolean hasEffects(LivingEntity living,StatusEffect... effects) {
        for (StatusEffect effect : effects) if (living.hasStatusEffect(effect)) return true;

        return false;
    }

    public static boolean shouldBehaveLikeSlime(LivingEntity living) {
        return hasEffects(living,WtsisStatusEffectRegistry.RIMURU_BOOTLEG_DAMAGE,WtsisStatusEffectRegistry.RIMURU_BOOTLEG_DAMAGEN_T) && !living.bypassesLandingEffects();
    }
}
