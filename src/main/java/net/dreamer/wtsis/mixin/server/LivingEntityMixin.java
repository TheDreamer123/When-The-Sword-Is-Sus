package net.dreamer.wtsis.mixin.server;

import net.dreamer.wtsis.criteria.WtsisCriteriaRegistry;
import net.dreamer.wtsis.effect.WtsisStatusEffectRegistry;
import net.dreamer.wtsis.item.EdibleSwordItem;
import net.dreamer.wtsis.item.GunpowderSwordItem;
import net.dreamer.wtsis.item.SlimeSwordItem;
import net.dreamer.wtsis.item.WtsisItemRegistry;
import net.dreamer.wtsis.misc.WtsisDamageSources;
import net.dreamer.wtsis.util.EffectPredicates;
import net.dreamer.wtsis.util.SecondsToTicksConverter;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow public abstract Hand getActiveHand();
    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);
    @Shadow public abstract ItemStack getStackInHand(Hand hand);
    @Shadow public abstract void setHealth(float health);
    @Shadow public abstract boolean clearStatusEffects();
    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);
    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);
    @Shadow public abstract boolean removeStatusEffect(StatusEffect type);
    @Shadow public abstract int getXpToDrop();
    @Shadow public abstract boolean shouldDropXp();
    @Shadow public abstract void disableExperienceDropping();

    public LivingEntityMixin(EntityType<?> type,World world) {
        super(type,world);
    }

    private boolean slimifying() {
        return EffectPredicates.hasEffects((LivingEntity) (Entity) this,WtsisStatusEffectRegistry.RIMURU_BOOTLEG_DAMAGE,WtsisStatusEffectRegistry.RIMURU_BOOTLEG_DAMAGEN_T);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V"), method = "eatFood")
    public void eatFoodInject(World world,ItemStack stack,CallbackInfoReturnable<ItemStack> cir) {
        if(stack.getItem() instanceof EdibleSwordItem) {
            stack.increment(1);
            if(stack.isOf(WtsisItemRegistry.BREAD_SWORD) || stack.isOf(WtsisItemRegistry.WET_BREAD_SWORD))
                stack.damage(10,(LivingEntity) (Entity) this,(player) -> player.sendToolBreakStatus(this.getActiveHand()));
            else if(stack.isOf(WtsisItemRegistry.APPLE_SWORD))
                stack.damage(13,(LivingEntity) (Entity) this,(player) -> player.sendToolBreakStatus(this.getActiveHand()));
            else if(stack.isOf(WtsisItemRegistry.GOLDEN_APPLE_SWORD))
                stack.damage(23,(LivingEntity) (Entity) this,(player) -> player.sendToolBreakStatus(this.getActiveHand()));
            else if(stack.isOf(WtsisItemRegistry.GLASS_SWORD)) stack.decrement(1);
            else if(stack.isOf(WtsisItemRegistry.ANVIL_SWORD))
                stack.damage(124, (LivingEntity) (Entity) this, (player) -> player.sendToolBreakStatus(this.getActiveHand()));
            else if(stack.isOf(WtsisItemRegistry.TOTEM_SWORD))
                stack.damage(32, (LivingEntity) (Entity) this, (player) -> player.sendToolBreakStatus(this.getActiveHand()));
            else if(stack.isOf(WtsisItemRegistry.GUNPOWDER_SWORD))
                stack.damage(7, (LivingEntity) (Entity) this, (player) -> player.sendToolBreakStatus(this.getActiveHand()));
            else if(stack.isOf(WtsisItemRegistry.SLIME_SWORD))
                stack.damage(21, (LivingEntity) (Entity) this, (player) -> player.sendToolBreakStatus(this.getActiveHand()));
            else if(stack.isOf(WtsisItemRegistry.FLINT_AND_STEEL_SWORD))
                stack.damage(25, (LivingEntity) (Entity) this, (player) -> player.sendToolBreakStatus(this.getActiveHand()));
            else if(stack.isOf(WtsisItemRegistry.SUSPICIOUS_STEW_SWORD))
                stack.damage(14, (LivingEntity) (Entity) this, (player) -> player.sendToolBreakStatus(this.getActiveHand()));
            else if(stack.isOf(WtsisItemRegistry.SCULK_SWORD))
                stack.damage(17, (LivingEntity) (Entity) this, (player) -> player.sendToolBreakStatus(this.getActiveHand()));
        }
    }

    @Inject(at = @At("HEAD"), method = "tryUseTotem", cancellable = true)
    public void tryUseTotemInject$damageSourceBlock(DamageSource source,CallbackInfoReturnable<Boolean> cir) {
        if(source == WtsisDamageSources.EAT_GLASS || source == WtsisDamageSources.EAT_WET_BREAD) cir.setReturnValue(false);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Hand;values()[Lnet/minecraft/util/Hand;"), method = "tryUseTotem", cancellable = true)
    public void tryUseTotemInject$totemSword(DamageSource source,CallbackInfoReturnable<Boolean> cir) {
        if(this.hasStatusEffect(WtsisStatusEffectRegistry.DEATH_SCARE)) {
            if((LivingEntity) (Entity) this instanceof ServerPlayerEntity serverPlayerEntity)
                Criteria.USED_TOTEM.trigger(serverPlayerEntity, new ItemStack(Items.TOTEM_OF_UNDYING));

            this.setHealth(1.0F);
            this.clearStatusEffects();
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 600, 0));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 600, 0));
            this.world.sendEntityStatus(this, (byte) 1232446678);

            cir.setReturnValue(true);
        }
        if(!source.isMagic() && !source.isExplosive() && source.getSource() != null && source.getSource() instanceof LivingEntity living) {
            ItemStack stack = living.getMainHandStack();
            if(stack.isOf(WtsisItemRegistry.TOTEM_SWORD)) {
                if(living instanceof ServerPlayerEntity serverPlayer) {
                    WtsisCriteriaRegistry.KILL_SLASH_J.trigger(serverPlayer, stack);
                    serverPlayer.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
                }
                if((LivingEntity) (Entity) this instanceof ServerPlayerEntity serverPlayer)
                    Criteria.USED_TOTEM.trigger(serverPlayer, new ItemStack(Items.TOTEM_OF_UNDYING));

                this.setHealth(1.0F);
                this.clearStatusEffects();
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
                this.world.sendEntityStatus(this, (byte) 909438957);

                cir.setReturnValue(true);
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "damage")
    public void damageInject$head(DamageSource source,float amount,CallbackInfoReturnable<Boolean> cir) {
        if(source == WtsisDamageSources.EAT_GLASS && (LivingEntity) (Entity) this instanceof ServerPlayerEntity player)
            WtsisCriteriaRegistry.DEATH_CUZ_GLASS.trigger(player, new ItemStack(WtsisItemRegistry.GLASS_SWORD));

        if(this.hasStatusEffect(WtsisStatusEffectRegistry.THORNY_SKIN) && source.getSource() != null) {
            Entity entity = source.getSource();
            entity.damage(DamageSource.thorns(this), 2.0F);
            entity.handleStatus((byte) 33);
        }

        if(!source.isMagic() && !source.isExplosive() && source.getSource() != null && source.getSource() instanceof LivingEntity living) {
            ItemStack stack = living.getMainHandStack();
            if(stack.isOf(WtsisItemRegistry.ANVIL_SWORD)) {
                float f = living.fallDistance;
                for(int i = 0; i < f + 1.0D; i++)
                    this.teleport(this.getX(),this.getY() - 1.0D,this.getZ());
            }
            if(stack.isOf(WtsisItemRegistry.GOLDEN_APPLE_SWORD)) {
                if(!stack.hasEnchantments() && (LivingEntity) (Entity) this instanceof ZombieVillagerEntity zombieVillager && zombieVillager.hasStatusEffect(StatusEffects.WEAKNESS)) {
                    ((AccessZombieVillagerMixin) zombieVillager).runSetConverting(living.getUuid(),zombieVillager.getRandom().nextInt(1199) + 1800);
                    stack.damage(22,living,(user) -> user.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
                }
            }
            if(stack.isOf(WtsisItemRegistry.GLASS_SWORD) && !(living instanceof PlayerEntity))
                stack.damage(1,living,(user) -> user.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
            if(stack.isOf(WtsisItemRegistry.SLIME_SWORD)) {
                float f = living.getYaw() * 0.017453292F;
                    ((LivingEntity) (Entity) this).takeKnockback(5.0F,MathHelper.sin(f),-MathHelper.cos(f));
            }
            if(stack.isOf(WtsisItemRegistry.SUSPICIOUS_STEW_SWORD)) {
                NbtCompound nbtCompound = stack.getNbt();
                if (nbtCompound != null && nbtCompound.contains("Effects", 9)) {
                    NbtList nbtList = nbtCompound.getList("Effects", 10);

                    for(int i = 0; i < nbtList.size(); ++i) {
                        int j = 160;
                        NbtCompound nbtCompound2 = nbtList.getCompound(i);
                        if (nbtCompound2.contains("EffectDuration", 3))
                            j = nbtCompound2.getInt("EffectDuration");

                        StatusEffect statusEffect = StatusEffect.byRawId(nbtCompound2.getInt("EffectId"));
                        if (statusEffect != null)
                            this.addStatusEffect(new StatusEffectInstance(statusEffect, j));
                    }
                }
            }

            if(living.hasStatusEffect(WtsisStatusEffectRegistry.FIERY_PASSION))
                this.setFireTicks(SecondsToTicksConverter.secondsToTicks(3, 6));
        }

        if(source.isExplosive() && hasStatusEffect(WtsisStatusEffectRegistry.CREEPER_CURSE)) {
            removeStatusEffect(WtsisStatusEffectRegistry.CREEPER_CURSE);
            addStatusEffect(new StatusEffectInstance(WtsisStatusEffectRegistry.EXPLODE,SecondsToTicksConverter.secondsToTicks(4)));
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;tryUseTotem(Lnet/minecraft/entity/damage/DamageSource;)Z"), method = "damage")
    public void damageInject$afterTryUseTotemCheck(DamageSource source,float amount,CallbackInfoReturnable<Boolean> cir) {
        if(!source.isMagic() && !source.isExplosive() && source.getSource() != null && source.getSource() instanceof LivingEntity living) {
            ItemStack stack = living.getMainHandStack();
            if(stack.isOf(WtsisItemRegistry.SCULK_SWORD) && this.shouldDropXp() && this.getXpToDrop() > 0 && this.isOnGround()) {
                SculkSpreadManager spreadManager = SculkSpreadManager.create();
                int i = this.getXpToDrop();
                for(int j = 0; j < i; j++) {
                    spreadManager.spread(new BlockPos(this.getPos().withBias(Direction.UP,0.5D)),1);
                    spreadManager.tick(world,this.getBlockPos(),world.getRandom(),true);
                }
                this.disableExperienceDropping();
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "tickMovement")
    public void tickMovementInject(CallbackInfo info) {
        LivingEntity living = (LivingEntity) (Entity) this;
        ItemStack[] stack = {
            getEquippedStack(EquipmentSlot.CHEST),
            getEquippedStack(EquipmentSlot.FEET),
            getEquippedStack(EquipmentSlot.HEAD),
            getEquippedStack(EquipmentSlot.LEGS),
            getEquippedStack(EquipmentSlot.MAINHAND),
            getEquippedStack(EquipmentSlot.OFFHAND)
        };
        for(ItemStack itemStack : stack) {
            if(itemStack.getItem() instanceof GunpowderSwordItem gunpowderSword && (!(living instanceof PlayerEntity)))
                gunpowderSword.explode(this,itemStack,this.world,80);
            if(itemStack.getItem() instanceof SlimeSwordItem && (!(living instanceof PlayerEntity))) {
                NbtCompound nbt = itemStack.getOrCreateNbt();
                if(nbt.contains("moltenTime")) {
                    if(nbt.getInt("moltenTime") > 0) nbt.putInt("moltenTime",nbt.getInt("moltenTime") - 1);
                    else nbt.remove("moltenTime");
                }
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "tick")
    public void tickInject(CallbackInfo info) {
        if(this.wasOnFire && hasStatusEffect(WtsisStatusEffectRegistry.CREEPER_CURSE)) {
            removeStatusEffect(WtsisStatusEffectRegistry.CREEPER_CURSE);
            addStatusEffect(new StatusEffectInstance(WtsisStatusEffectRegistry.EXPLODE,SecondsToTicksConverter.secondsToTicks(4)));
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getSoundGroup()Lnet/minecraft/sound/BlockSoundGroup;"), locals = LocalCapture.CAPTURE_FAILEXCEPTION, method = "playBlockFallSound", cancellable = true)
    public void playBlockFallSoundInject(CallbackInfo info,int i,int j,int k,BlockState blockState) {
        if (slimifying()) {
            this.playSound(SoundEvents.ENTITY_SLIME_SQUISH_SMALL,blockState.getSoundGroup().getVolume() * 0.5F,blockState.getSoundGroup().getPitch() * 0.75F);
            info.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "handleFallDamage")
    public void handleFallDamageInject(float fallDistance,float damageMultiplier,DamageSource damageSource,CallbackInfoReturnable<Boolean> cir) {
        if(damageMultiplier == 0.0F && damageSource == DamageSource.FALL)
            if((LivingEntity) (Entity) this instanceof ServerPlayerEntity serverPlayer && EffectPredicates.shouldBehaveLikeSlime(serverPlayer))
                WtsisCriteriaRegistry.YOU_BOUNCE_ME_RIGHT_ROUND.trigger(serverPlayer,new ItemStack(WtsisItemRegistry.SLIME_SWORD));
    }

    @ModifyVariable(at = @At("STORE"), method = "travel", index = 2)
    private double getTheD(double d) {
        if(this.hasStatusEffect(WtsisStatusEffectRegistry.COMICAL_WEIGHT)) d += 0.7D;
        if(this.getStackInHand(Hand.MAIN_HAND).isOf(WtsisItemRegistry.ANVIL_SWORD)) d += 0.7D;
        if(this.getStackInHand(Hand.OFF_HAND).isOf(WtsisItemRegistry.ANVIL_SWORD)) d += 0.3D;

        return d;
    }

    @ModifyVariable(at = @At("STORE"), method = "handleStatus", index = 3)
    private SoundEvent playerOuchie(SoundEvent value) {
        if(slimifying())
            value = SoundEvents.ENTITY_SLIME_HURT;
        return value;
    }

    @ModifyVariable(at = @At("STORE"), method = "playHurtSound", index = 2)
    private SoundEvent entityOuchie(SoundEvent value) {
        if(slimifying())
            value = SoundEvents.ENTITY_SLIME_HURT;
        return value;
    }

    @ModifyVariable(at = @At("STORE"), method = "handleStatus", index = 2)
    private SoundEvent playerRIP(SoundEvent value) {
        if(slimifying())
            value = SoundEvents.ENTITY_SLIME_DEATH;
        return value;
    }

    @ModifyVariable(slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getDeathSound()Lnet/minecraft/sound/SoundEvent;")), at = @At("STORE"), method = "damage", index = 8)
    private SoundEvent entityRIP(SoundEvent value) {
        if(slimifying())
            value = SoundEvents.ENTITY_SLIME_DEATH;
        return value;
    }
}
