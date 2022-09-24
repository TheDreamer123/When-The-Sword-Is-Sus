package net.dreamer.wtsis.item;

import net.dreamer.wtsis.misc.WtsisDamageSources;
import net.dreamer.wtsis.misc.WtsisItemTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

public class SculkSwordItem extends EdibleSwordItem {
    public SculkSwordItem(ToolMaterial toolMaterial,int attackDamage,float attackSpeed,FoodComponent component,SoundEvent eatSound) {
        super(toolMaterial,attackDamage,attackSpeed,component,eatSound);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack,World world,LivingEntity user) {
        if(user instanceof PlayerEntity player)
            for(int i = 0; i < player.getInventory().size(); i++) {
                ItemStack stack1 = player.getInventory().getStack(i);
                if(!stack1.isEmpty() && !stack1.isIn(WtsisItemTags.SCULK)) {
                    ItemStack stack2 = new ItemStack(stack1.getItem() instanceof SwordItem ? WtsisItemRegistry.SCULK_SWORD : stack1.getItem() instanceof BlockItem ? Items.SCULK : Items.SCULK_VEIN);
                    int j = stack1.getCount();
                    stack2.setCount(j);
                    if(stack1.getNbt() != null) stack2.getOrCreateNbt().copyFrom(stack1.getNbt());
                    player.getInventory().setStack(i, stack2);
                }
            }

        user.damage(WtsisDamageSources.SCULKIFY, Float.MAX_VALUE);

        return super.finishUsing(stack,world,user);
    }
}
