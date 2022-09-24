package net.dreamer.wtsis.mixin.server;

import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DamageSource.class)
public interface AccessDamageSource {
    @Invoker("<init>")
    static DamageSource newDamageSource(String name) {
        throw new RuntimeException("Problems found with " + name + ", please look forward into fixing it.");
    }

    @Invoker("setUnblockable")
    DamageSource unblockableSource();

    @Invoker("setBypassesArmor")
    DamageSource bypassesArmorSource();

    @Invoker("setBypassesProtection")
    DamageSource bypassesProtectionSource();
}
