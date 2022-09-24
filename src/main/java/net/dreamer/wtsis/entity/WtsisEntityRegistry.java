package net.dreamer.wtsis.entity;

import net.dreamer.wtsis.WhenTheSwordIsSus;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WtsisEntityRegistry {
    public static EntityType<EnchantedGoldenAppleEntity> ENCHANTED_GOLDEN_APPLE;

    public static void register() {
        ENCHANTED_GOLDEN_APPLE = Registry.register(Registry.ENTITY_TYPE,new Identifier(WhenTheSwordIsSus.MOD_ID, "enchanted_golden_apple"),FabricEntityTypeBuilder.<EnchantedGoldenAppleEntity>create(SpawnGroup.MISC,EnchantedGoldenAppleEntity::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build());
    }
}
