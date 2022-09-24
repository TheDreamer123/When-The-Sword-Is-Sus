package net.dreamer.wtsis.item;

import net.dreamer.wtsis.effect.WtsisStatusEffectRegistry;
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
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GunpowderSwordItem extends EdibleSwordItem {
    public GunpowderSwordItem(ToolMaterial toolMaterial,int attackDamage,float attackSpeed,FoodComponent component,SoundEvent eatSound) {
        super(toolMaterial,attackDamage,attackSpeed,component,eatSound);
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        String translation = super.getTranslationKey(stack);
        return stack.getOrCreateNbt().contains("explosionTicks") ? translation + ".ignited" : translation;
    }

    public void explode(Entity entity,ItemStack stack,World world,int startingExplosionTime) {
        NbtCompound nbt = stack.getOrCreateNbt();
        boolean bl = nbt.contains("explosionTicks");
        boolean bl2 = entity.wasOnFire;

        float i = stack.getOrCreateNbt().getInt("explosionTicks") / 20.0F;
        i /= Math.pow(10, (int) Math.log10(i));
        i = ((int) (i * 10)) / 10.0f;
        if(bl && i < 0.1F && !world.isClient) {
            stack.decrement(1);
            if(!nbt.contains("exploded")) {
                nbt.putBoolean("exploded", true);
                world.createExplosion(null,entity.getX(),entity.getY(),entity.getZ(),3.0F,Explosion.DestructionType.DESTROY);
            }
        }

        if(bl2 && !bl) {
            world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
            nbt.putInt("explosionTicks", startingExplosionTime);
        }
        ServerTickEvents.EndTick tick = server -> {
            if(bl) {
                nbt.putInt("explosionTicks", nbt.getInt("explosionTicks") - 1);
            }
        };
        tick.onEndTick(world.getServer());
    }

    @Override
    public ItemStack finishUsing(ItemStack stack,World world,LivingEntity user) {
        NbtCompound nbt = stack.getOrCreateNbt();
        boolean bl = nbt.contains("explosionTicks");
        int i = (bl ? nbt.getInt("explosionTicks") : SecondsToTicksConverter.secondsToTicks(30));
        StatusEffect effect = (bl ? WtsisStatusEffectRegistry.EXPLODE : WtsisStatusEffectRegistry.CREEPER_CURSE);
        user.addStatusEffect(new StatusEffectInstance(effect,i));
        return super.finishUsing(stack,world,user);
    }

    @Override
    public void inventoryTick(ItemStack stack,World world,Entity entity,int slot,boolean selected) {
        explode(entity,stack,world,80);
        super.inventoryTick(stack,world,entity,slot,selected);
    }

    @Override
    public void appendTooltip(ItemStack stack,@Nullable World world,List<Text> tooltip,TooltipContext context) {
        if(stack.getOrCreateNbt().contains("explosionTicks")) {
            float i = stack.getOrCreateNbt().getInt("explosionTicks") / 20.0F;
            String format = String.format("%.1f", i);
            char[] asChar = format.toCharArray();
            for(int j = 0; j < asChar.length; j++) if(asChar[j] == ',') asChar[j] = '.';
            format = String.valueOf(asChar);
            tooltip.add(Text.translatable(this.getTranslationKey() + ".explosionTime", format));
        }
    }
}
