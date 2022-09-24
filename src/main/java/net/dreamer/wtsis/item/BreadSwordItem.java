package net.dreamer.wtsis.item;

import net.dreamer.wtsis.WhenTheSwordIsSus;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BreadSwordItem extends EdibleSwordItem {
    public boolean sandy = false;

    public BreadSwordItem(ToolMaterial toolMaterial,int attackDamage,float attackSpeed,FoodComponent component,SoundEvent eatSound) {
        super(toolMaterial,attackDamage,attackSpeed,component,eatSound);
    }

    @Override
    public void appendStacks(ItemGroup group,DefaultedList<ItemStack> stacks) {
        if(this.getGroup() == group) {
            ItemStack stack = new ItemStack(this);
            stack.getOrCreateNbt().putBoolean("sandy", sandy);
            stacks.add(stack);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack,@Nullable World world,List<Text> tooltip,TooltipContext context) {
        if(stack.getOrCreateNbt().getBoolean("sandy"))
            tooltip.add(Text.translatable(WhenTheSwordIsSus.MOD_ID + ".bread_sword.sandy").formatted(Formatting.GOLD));
    }
}
