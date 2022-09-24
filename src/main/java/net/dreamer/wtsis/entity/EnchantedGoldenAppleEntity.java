package net.dreamer.wtsis.entity;

import net.dreamer.wtsis.misc.WtsisDamageSources;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EnchantedGoldenAppleEntity extends ThrownItemEntity {
    private float damage;
    private int knockback;
    private int fireAspect;

    public EnchantedGoldenAppleEntity(EntityType<? extends ThrownItemEntity> entityType,World world) {
        super(entityType,world);
        this.damage = 3.0F;
    }

    public EnchantedGoldenAppleEntity(LivingEntity livingEntity,World world) {
        super(WtsisEntityRegistry.ENCHANTED_GOLDEN_APPLE,livingEntity,world);
    }

    public EnchantedGoldenAppleEntity(World world, double x, double y, double z) {
        super(WtsisEntityRegistry.ENCHANTED_GOLDEN_APPLE, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.ENCHANTED_GOLDEN_APPLE;
    }

    public void setDamage(float damage) {
        this.damage += damage;
    }

    public void setKnockback(int knockback) {
        this.knockback = knockback;
    }

    public void setFireAspect(int fireAspect) {
        this.fireAspect = fireAspect;
    }

    @Override
    public void tick() {
        super.tick();
        if(this.fireAspect > 0) this.setOnFireFor(2);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);

        Entity entity = entityHitResult.getEntity();
        entity.damage(WtsisDamageSources.thrownEnchantedGoldenApple(this, this.getOwner()), 3.0F + this.damage);
        if(this.knockback > 0 && entity instanceof LivingEntity living) {
            double d = Math.max(0.0D,1.0D - living.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
            Vec3d vec3d = this.getVelocity().multiply(1.0D,0.0D,1.0D).normalize().multiply((double) this.knockback * 0.6D * d);
            if(vec3d.lengthSquared() > 0.0D)
                entity.addVelocity(vec3d.x,0.1D,vec3d.z);
        }
        if(entity.getType() != EntityType.ENDERMAN) {
            if(this.fireAspect > 0) entity.setOnFireFor(4 * this.fireAspect);
            else {
                if(this.isOnFire()) entity.setFireTicks(this.getFireTicks());
            }
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putFloat("damage", this.damage);
        nbt.putInt("knockback", this.knockback);
        nbt.putInt("fireAspect", this.fireAspect);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if(nbt.contains("damage", 99)) this.damage = nbt.getFloat("damage");
        if(nbt.contains("knockback", 99)) this.knockback = nbt.getInt("knockback");
        if(nbt.contains("fireAspect", 99)) this.fireAspect = nbt.getInt("fireAspect");
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);

        if (!this.world.isClient) {
            this.world.sendEntityStatus(this, (byte)3);
            this.discard();
        }
    }
}
