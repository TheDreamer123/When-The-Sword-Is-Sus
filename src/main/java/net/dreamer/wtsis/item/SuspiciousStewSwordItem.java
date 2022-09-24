package net.dreamer.wtsis.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

public class SuspiciousStewSwordItem extends EdibleSwordItem {
    public SuspiciousStewSwordItem(ToolMaterial toolMaterial,int attackDamage,float attackSpeed,FoodComponent component,SoundEvent eatSound) {
        super(toolMaterial,attackDamage,attackSpeed,component,eatSound);
    }

    public ItemStack finishUsing(ItemStack stack,World world,LivingEntity user) {
        ItemStack stack2 = super.finishUsing(stack,world,user);
        NbtCompound nbtCompound = stack2.getNbt();
        if (nbtCompound != null && nbtCompound.contains("Effects", 9)) {
            NbtList nbtList = nbtCompound.getList("Effects", 10);

            for(int i = 0; i < nbtList.size(); ++i) {
                int j = 160;
                NbtCompound nbtCompound2 = nbtList.getCompound(i);
                if (nbtCompound2.contains("EffectDuration", 3))
                    j = nbtCompound2.getInt("EffectDuration");

                StatusEffect statusEffect = StatusEffect.byRawId(nbtCompound2.getInt("EffectId"));
                if (statusEffect != null)
                    user.addStatusEffect(new StatusEffectInstance(statusEffect, j));
            }
        }

        return stack2;
    }
}
