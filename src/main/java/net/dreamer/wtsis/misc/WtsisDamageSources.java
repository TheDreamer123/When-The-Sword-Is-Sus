package net.dreamer.wtsis.misc;

import net.dreamer.wtsis.mixin.server.AccessDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import org.jetbrains.annotations.Nullable;

public class WtsisDamageSources {
    public static final DamageSource EAT_WET_BREAD = (((AccessDamageSource)((AccessDamageSource)((AccessDamageSource) AccessDamageSource.newDamageSource("eatWetBread")).unblockableSource()).bypassesArmorSource()).bypassesProtectionSource());
    public static final DamageSource GLASS_SHARDS = AccessDamageSource.newDamageSource("glassShards");
    public static final DamageSource EAT_GLASS = (((AccessDamageSource)((AccessDamageSource)((AccessDamageSource) AccessDamageSource.newDamageSource("eatGlass")).unblockableSource()).bypassesArmorSource()).bypassesProtectionSource());
    public static final DamageSource SLIMIFY = (((AccessDamageSource)((AccessDamageSource)((AccessDamageSource) AccessDamageSource.newDamageSource("slimify")).unblockableSource()).bypassesArmorSource()).bypassesProtectionSource());
    public static final DamageSource SCULKIFY = (((AccessDamageSource)((AccessDamageSource)((AccessDamageSource) AccessDamageSource.newDamageSource("sculkify")).unblockableSource()).bypassesArmorSource()).bypassesProtectionSource());
    public static DamageSource thrownEnchantedGoldenApple(Entity projectile,@Nullable Entity attacker) {
        return new ProjectileDamageSource("thrownEnchantedGoldenApple", projectile, attacker).setProjectile();
    }
}
