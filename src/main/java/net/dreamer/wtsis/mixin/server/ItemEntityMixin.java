package net.dreamer.wtsis.mixin.server;

import net.dreamer.wtsis.item.WtsisItemRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    @Shadow public abstract ItemStack getStack();
    @Shadow public abstract void setStack(ItemStack stack);

    public ItemEntityMixin(EntityType<?> type,World world) {
        super(type,world);
    }

    private void gunpowderSwordExplode(int startingExplosionTime, boolean fromExplosion) {
        NbtCompound nbt = getStack().getOrCreateNbt();
        boolean bl = nbt.contains("explosionTicks");
        boolean bl2 = this.world != null && this.world.isClient;
        boolean bl3 = (this.getFireTicks() > 0 || bl2 && this.getFlag(0)) || fromExplosion;

        float i = getStack().getOrCreateNbt().getInt("explosionTicks") / 20.0F;
        i /= Math.pow(10, (int) Math.log10(i));
        i = ((int) (i * 10)) / 10.0f;
        if(bl && i < 0.1F && !world.isClient) {
            getStack().decrement(1);
            if(!nbt.contains("exploded")) {
                nbt.putBoolean("exploded", true);
                world.createExplosion(null,this.getX(),this.getY(),this.getZ(),3.0F,Explosion.DestructionType.DESTROY);
            }
        }

        if(bl3 && !bl) {
            world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
            nbt.putInt("explosionTicks", startingExplosionTime);
        }
        if(bl) nbt.putInt("explosionTicks", nbt.getInt("explosionTicks") - 1);
    }

    @Inject(at = @At("HEAD"), method = "tick")
    public void tickInject(CallbackInfo info) {
        if(this.getStack().isOf(WtsisItemRegistry.BREAD_SWORD) && this.isWet()) {
            ItemStack stack = new ItemStack(WtsisItemRegistry.WET_BREAD_SWORD);
            if(this.getStack().getNbt() != null) stack.getOrCreateNbt().copyFrom(this.getStack().getNbt());
            this.setStack(stack);
        } else if(this.getStack().isOf(WtsisItemRegistry.WET_BREAD_SWORD) && this.world.getBlockState(this.getBlockPos()).isIn(BlockTags.SAND)) {
            ItemStack stack = new ItemStack(WtsisItemRegistry.BREAD_SWORD);
            if(this.getStack().getNbt() != null)
                stack.getOrCreateNbt().copyFrom(this.getStack().getNbt()).putBoolean("sandy", true);
            this.setStack(stack);
        }

        if(this.getStack().isOf(WtsisItemRegistry.GUNPOWDER_SWORD))
            gunpowderSwordExplode(80,false);

        if(this.getStack().isOf(WtsisItemRegistry.SLIME_SWORD)) {
            NbtCompound nbt = this.getStack().getOrCreateNbt();
            if(nbt.contains("moltenTime")) {
                if(!this.isWet()) {
                    if(nbt.getInt("moltenTime") > 0) nbt.putInt("moltenTime",nbt.getInt("moltenTime") - 1);
                    else nbt.remove("moltenTime");
                } else {
                    world.playSound(null,getX(),getY(),getZ(),SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE,SoundCategory.NEUTRAL,1.0F,new Random().nextFloat(0.5F, 1.0F));
                    ItemStack stack = new ItemStack(WtsisItemRegistry.SLIME_SWORD);
                    if(this.getStack().getNbt() != null)
                        stack.getOrCreateNbt().copyFrom(this.getStack().getNbt()).remove("moltenTime");
                    this.setStack(stack);
                }
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
    public void damageInject(DamageSource source,float amount,CallbackInfoReturnable<Boolean> cir) {
        if(getStack().isOf(WtsisItemRegistry.GUNPOWDER_SWORD)) {
            if(source.isFire()) cir.setReturnValue(false);
            if(source.isExplosive()) {

                gunpowderSwordExplode(40,true);
                cir.setReturnValue(false);
            }
        }
    }
}
