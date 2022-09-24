package net.dreamer.wtsis.item;

import net.dreamer.wtsis.effect.WtsisStatusEffectRegistry;
import net.dreamer.wtsis.util.SecondsToTicksConverter;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

public class AnvilSwordItem extends EdibleSwordItem {
    public AnvilSwordItem(ToolMaterial toolMaterial,int attackDamage,float attackSpeed,FoodComponent component,SoundEvent eatSound) {
        super(toolMaterial,attackDamage,attackSpeed,component,eatSound);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 120;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack,World world,LivingEntity user) {
        user.addStatusEffect(new StatusEffectInstance(WtsisStatusEffectRegistry.COMICAL_WEIGHT,SecondsToTicksConverter.secondsToTicks(30)));

        return super.finishUsing(stack,world,user);
    }
}
