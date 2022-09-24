package net.dreamer.wtsis.item;

import net.dreamer.wtsis.misc.WtsisDamageSources;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

public class WetBreadSwordItem extends BreadSwordItem {
    public WetBreadSwordItem(ToolMaterial toolMaterial,int attackDamage,float attackSpeed,FoodComponent component,SoundEvent eatSound) {
        super(toolMaterial,attackDamage,attackSpeed,component,eatSound);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack,World world,LivingEntity user) {
        user.damage(WtsisDamageSources.EAT_WET_BREAD, Float.MAX_VALUE);

        return super.finishUsing(stack,world,user);
    }
}
