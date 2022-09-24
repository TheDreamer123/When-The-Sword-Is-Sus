package net.dreamer.wtsis.item;

import com.mojang.datafixers.util.Pair;
import net.dreamer.wtsis.criteria.WtsisCriteriaRegistry;
import net.dreamer.wtsis.entity.EnchantedGoldenAppleEntity;
import net.dreamer.wtsis.sound.WtsisSoundRegistry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.List;

public class GoldenAppleSwordItem extends AppleSwordItem {
    public GoldenAppleSwordItem(ToolMaterial toolMaterial,int attackDamage,float attackSpeed,FoodComponent component,SoundEvent eatSound) {
        super(toolMaterial,attackDamage,attackSpeed,component,eatSound);
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        if(!stack.hasEnchantments()) return super.getTranslationKey(stack);

        return super.getTranslationKey(stack) + ".enchanted";
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return !stack.hasEnchantments() ? Rarity.RARE : Rarity.EPIC;
    }

    @Override
    public boolean canRepair(ItemStack stack,ItemStack ingredient) {
        return !stack.hasEnchantments() ? ingredient.isOf(Items.GOLDEN_APPLE) : ingredient.isOf(Items.ENCHANTED_GOLDEN_APPLE);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        if(stack.getOrCreateNbt().contains("bullet")) return UseAction.BOW;

        return super.getUseAction(stack);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        if(stack.getOrCreateNbt().contains("bullet")) return 30;

        return super.getMaxUseTime(stack);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack,World world,LivingEntity user) {
        if(stack.getOrCreateNbt().contains("bullet") && user instanceof PlayerEntity player) {
            world.playSound(null,user.getX(),user.getY(),user.getZ(),WtsisSoundRegistry.ENTITY_ENCHANTED_GOLDEN_APPLE_THROW,SoundCategory.NEUTRAL,0.5F,0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
            player.getItemCooldownManager().set(this,20);
            if (!world.isClient) {
                EnchantedGoldenAppleEntity enchantedGoldenApple = new EnchantedGoldenAppleEntity(user,world);
                enchantedGoldenApple.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
                int j = EnchantmentHelper.getLevel(Enchantments.SHARPNESS, stack);
                if (j > 0) enchantedGoldenApple.setDamage(j * 0.5F + 0.5F);
                int k = EnchantmentHelper.getLevel(Enchantments.KNOCKBACK, stack);
                if(k > 0) enchantedGoldenApple.setKnockback(k);
                int l = EnchantmentHelper.getLevel(Enchantments.FIRE_ASPECT, stack);
                if(l > 0) enchantedGoldenApple.setFireAspect(l);

                world.spawnEntity(enchantedGoldenApple);
            }

            player.incrementStat(Stats.USED.getOrCreateStat(this));
            if(player instanceof ServerPlayerEntity serverPlayer) WtsisCriteriaRegistry.THROW_ITEM.trigger(serverPlayer, new ItemStack(this));
            stack.damage(22,player,(playerx) -> playerx.sendToolBreakStatus(player.getActiveHand()));
            return stack;
        }

        List<Pair<StatusEffectInstance, Float>> potions = !stack.hasEnchantments() ? FoodComponents.GOLDEN_APPLE.getStatusEffects() : FoodComponents.ENCHANTED_GOLDEN_APPLE.getStatusEffects();
        for(Pair<StatusEffectInstance, Float> pair : potions) {
            StatusEffectInstance base = pair.getFirst();
            StatusEffectInstance effectInstance = new StatusEffectInstance(base.getEffectType(),base.getDuration() / 5,base.getAmplifier(),base.isAmbient(),base.shouldShowParticles(),base.shouldShowIcon());
            if (effectInstance.getEffectType().isInstant())
                effectInstance.getEffectType().applyInstantEffect(user,user,user,effectInstance.getAmplifier(),1.0D);
            else user.addStatusEffect(effectInstance);
        }

        return super.finishUsing(stack,world,user);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world,PlayerEntity user,Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if(stack.hasEnchantments() && user.isSneaky()) {
            stack.getOrCreateNbt().putBoolean("bullet", true);
            return ItemUsage.consumeHeldItem(world,user,hand);
        }
        stack.getOrCreateNbt().remove("bullet");

        return super.use(world,user,hand);
    }
}
