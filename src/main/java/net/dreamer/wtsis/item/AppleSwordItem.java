package net.dreamer.wtsis.item;

import net.dreamer.wtsis.WhenTheSwordIsSus;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AppleSwordItem extends EdibleSwordItem {
    public AppleSwordItem(ToolMaterial toolMaterial,int attackDamage,float attackSpeed,FoodComponent component,SoundEvent eatSound) {
        super(toolMaterial,attackDamage,attackSpeed,component,eatSound);
    }

    @Override
    public void appendTooltip(ItemStack stack,@Nullable World world,List<Text> tooltip,TooltipContext context) {
        Formatting formatting = getRarity(stack).formatting;

        if(stack.isOf(WtsisItemRegistry.GOLDEN_APPLE_SWORD) && !stack.hasEnchantments())
            tooltip.add(Text.translatable(WhenTheSwordIsSus.MOD_ID + ".golden_apple_sword.quality").formatted(formatting));
        else if(stack.isOf(WtsisItemRegistry.GOLDEN_APPLE_SWORD) && stack.hasEnchantments())
            tooltip.add(Text.translatable(WhenTheSwordIsSus.MOD_ID + ".enchanted_golden_apple_sword.quality").formatted(formatting));
        else tooltip.add(Text.translatable(WhenTheSwordIsSus.MOD_ID + ".apple_sword.quality").formatted(formatting));
    }
}
