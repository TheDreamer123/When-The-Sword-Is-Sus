package net.dreamer.wtsis.item;

import net.dreamer.wtsis.effect.WtsisStatusEffectRegistry;
import net.dreamer.wtsis.util.IntColorCalculator;
import net.dreamer.wtsis.util.SecondsToTicksConverter;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SlimeSwordItem extends EdibleSwordItem {
    public SlimeSwordItem(ToolMaterial toolMaterial,int attackDamage,float attackSpeed,FoodComponent component,SoundEvent eatSound) {
        super(toolMaterial,attackDamage,attackSpeed,component,eatSound);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack,World world,LivingEntity user) {
        NbtCompound nbt = stack.getOrCreateNbt();
        StatusEffect effect;
        StatusEffect effectRem;
        if(nbt.contains("moltenTime")) {
            effect = WtsisStatusEffectRegistry.RIMURU_BOOTLEG_DAMAGEN_T;
            effectRem = WtsisStatusEffectRegistry.RIMURU_BOOTLEG_DAMAGE;
        }
        else {
            effect = WtsisStatusEffectRegistry.RIMURU_BOOTLEG_DAMAGE;
            effectRem = WtsisStatusEffectRegistry.RIMURU_BOOTLEG_DAMAGEN_T;
        }
        user.addStatusEffect(new StatusEffectInstance(effect,SecondsToTicksConverter.secondsToTicks(30)));
        user.removeStatusEffect(effectRem);

        return super.finishUsing(stack,world,user);
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return !stack.getOrCreateNbt().contains("moltenTime") ? super.getTranslationKey(stack) : super.getTranslationKey(stack) + ".molten";
    }

    @Override
    public void inventoryTick(ItemStack stack,World world,Entity entity,int slot,boolean selected) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if(nbt.contains("moltenTime")) {
            if(nbt.getInt("moltenTime") > 0) {
                ServerTickEvents.EndTick tick = server -> nbt.putInt("moltenTime",nbt.getInt("moltenTime") - 1);
                tick.onEndTick(world.getServer());
            } else nbt.remove("moltenTime");
        }

        super.inventoryTick(stack,world,entity,slot,selected);
    }

    @Override
    public void appendTooltip(ItemStack stack,@Nullable World world,List<Text> tooltip,TooltipContext context) {
        if(stack.getOrCreateNbt().contains("moltenTime")) {
            float i = stack.getOrCreateNbt().getInt("moltenTime") / 20.0F;
            String format = String.format("%.1f", i);
            char[] asChar = format.toCharArray();
            for(int j = 0; j < asChar.length; j++) if(asChar[j] == ',') asChar[j] = '.';
            format = String.valueOf(asChar);
            Text key = Text.translatable(this.getTranslationKey() + ".moltenTime", format);
            Style style = key.getStyle().withColor(IntColorCalculator.rgbToInt(255,165,0));
            tooltip.add(Text.translatable(this.getTranslationKey() + ".moltenTime", format).setStyle(style));
        }
    }
}
