package net.dreamer.wtsis.effect;

import net.dreamer.wtsis.WhenTheSwordIsSus;
import net.dreamer.wtsis.util.IntColorCalculator;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WtsisStatusEffectRegistry {
    public static final StatusEffect THORNY_SKIN = new ThornySkinStatusEffect();
    public static final StatusEffect COMICAL_WEIGHT = new BaseStatusEffect(StatusEffectCategory.HARMFUL,IntColorCalculator.rgbToInt(74,74,74)).addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "b655cbee-2863-11ed-a261-0242ac120002", -(0.2000000596046448D * 4.0D), EntityAttributeModifier.Operation.MULTIPLY_BASE);
    public static final StatusEffect DEATH_SCARE = new BaseStatusEffect(StatusEffectCategory.BENEFICIAL,IntColorCalculator.rgbToInt(0,187,70));
    public static final StatusEffect CREEPER_CURSE = new BaseStatusEffect(StatusEffectCategory.HARMFUL,IntColorCalculator.rgbToInt(0,100,0));
    public static final StatusEffect EXPLODE = new ExplosionStatusEffect();
    public static final StatusEffect RIMURU_BOOTLEG_DAMAGE = new RimuruBootlegStatusEffect(true);
    public static final StatusEffect RIMURU_BOOTLEG_DAMAGEN_T = new RimuruBootlegStatusEffect(false);
    public static final StatusEffect FIERY_PASSION = new FieryPassionStatusEffect();

    public static void register() {
        Registry.register(Registry.STATUS_EFFECT, new Identifier(WhenTheSwordIsSus.MOD_ID, "thorny_skin"), THORNY_SKIN);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(WhenTheSwordIsSus.MOD_ID, "comical_weight"), COMICAL_WEIGHT);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(WhenTheSwordIsSus.MOD_ID, "death_scare"), DEATH_SCARE);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(WhenTheSwordIsSus.MOD_ID, "creeper_curse"), CREEPER_CURSE);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(WhenTheSwordIsSus.MOD_ID, "explode"), EXPLODE);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(WhenTheSwordIsSus.MOD_ID, "rimuru_bootleg_damage"), RIMURU_BOOTLEG_DAMAGE);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(WhenTheSwordIsSus.MOD_ID, "rimuru_bootleg_damagen_t"), RIMURU_BOOTLEG_DAMAGEN_T);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(WhenTheSwordIsSus.MOD_ID, "fiery_passion"), FIERY_PASSION);
    }
}
