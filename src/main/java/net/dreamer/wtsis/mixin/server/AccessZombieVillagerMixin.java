package net.dreamer.wtsis.mixin.server;

import net.minecraft.entity.mob.ZombieVillagerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.UUID;

@Mixin(ZombieVillagerEntity.class)
public interface AccessZombieVillagerMixin {
    @Invoker("setConverting")
    void runSetConverting(@Nullable UUID uuid,int delay);
}
