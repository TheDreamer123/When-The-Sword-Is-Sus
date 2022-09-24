package net.dreamer.wtsis.item;

import net.dreamer.wtsis.WhenTheSwordIsSus;
import net.dreamer.wtsis.material.WtsisToolMaterials;
import net.dreamer.wtsis.misc.WtsisFoodComponents;
import net.dreamer.wtsis.sound.WtsisSoundRegistry;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WtsisItemRegistry {
    public static final Item BREAD_SWORD = new BreadSwordItem(WtsisToolMaterials.BREAD_SWORD,3,-2.4F, WtsisFoodComponents.BREAD_SWORD, SoundEvents.ENTITY_GENERIC_EAT);
    public static final Item WET_BREAD_SWORD = new WetBreadSwordItem(WtsisToolMaterials.WET_BREAD_SWORD, 1,-3.0F, WtsisFoodComponents.IMAGINE, SoundEvents.ENTITY_GENERIC_EAT);

    public static final Item APPLE_SWORD = new AppleSwordItem(WtsisToolMaterials.APPLE_SWORD,4,-2.6F, WtsisFoodComponents.APPLE_SWORD, SoundEvents.ENTITY_GENERIC_EAT);
    public static final Item GOLDEN_APPLE_SWORD = new GoldenAppleSwordItem(WtsisToolMaterials.GOLDEN_APPLE_SWORD,5,-2.6F, WtsisFoodComponents.GOLDEN_APPLE_SWORD, SoundEvents.ENTITY_GENERIC_EAT);

    public static final Item GLASS_SWORD = new GlassSwordItem(WtsisToolMaterials.GLASS_SWORD,5,-2.4F, WtsisFoodComponents.IMAGINE, WtsisSoundRegistry.ENTITY_GLASS_EAT);

    public static final Item ANVIL_SWORD = new AnvilSwordItem(WtsisToolMaterials.ANVIL_SWORD,7,-3.5F, WtsisFoodComponents.ANVIL_SWORD, WtsisSoundRegistry.ENTITY_METAL_EAT);

    public static final Item TOTEM_SWORD = new TotemSwordItem(WtsisToolMaterials.TOTEM_SWORD, 4, -2.4F, WtsisFoodComponents.TOTEM_SWORD, WtsisSoundRegistry.ENTITY_METAL_EAT);

    public static final Item GUNPOWDER_SWORD = new GunpowderSwordItem(WtsisToolMaterials.GUNPOWDER_SWORD, 3,-2.4F, WtsisFoodComponents.GUNPOWDER_SWORD, WtsisSoundRegistry.ENTITY_GUNPOWDER_EAT);

    public static final Item SLIME_SWORD = new SlimeSwordItem(WtsisToolMaterials.SLIME_SWORD,4, -2.4F, WtsisFoodComponents.SLIME_SWORD, WtsisSoundRegistry.ENTITY_SLIME_EAT);

    public static final Item FLINT_AND_STEEL_SWORD = new FlintAndSteelSwordItem(WtsisToolMaterials.FLINT_AND_STEEL_SWORD, 4, -2.4F, WtsisFoodComponents.FLINT_AND_STEEL_SWORD, WtsisSoundRegistry.ENTITY_METAL_EAT);

    public static final Item SUSPICIOUS_STEW_SWORD = new SuspiciousStewSwordItem(WtsisToolMaterials.SUSPICIOUS_STEW_SWORD, 4, -2.2F, WtsisFoodComponents.SUSPICIOUS_STEW_SWORD, SoundEvents.ENTITY_GENERIC_EAT);

    public static final Item SCULK_SWORD = new SculkSwordItem(WtsisToolMaterials.SCULK_SWORD, 3, -2.2F, WtsisFoodComponents.SCULK_SWORD, WtsisSoundRegistry.ENTITY_SCULK_EAT);


    public static void register() {
        Registry.register(Registry.ITEM, new Identifier(WhenTheSwordIsSus.MOD_ID, "bread_sword"),BREAD_SWORD);
        Registry.register(Registry.ITEM, new Identifier(WhenTheSwordIsSus.MOD_ID, "wet_bread_sword"), WET_BREAD_SWORD);

        Registry.register(Registry.ITEM, new Identifier(WhenTheSwordIsSus.MOD_ID, "apple_sword"), APPLE_SWORD);
        Registry.register(Registry.ITEM, new Identifier(WhenTheSwordIsSus.MOD_ID, "golden_apple_sword"), GOLDEN_APPLE_SWORD);

        Registry.register(Registry.ITEM, new Identifier(WhenTheSwordIsSus.MOD_ID, "glass_sword"), GLASS_SWORD);

        Registry.register(Registry.ITEM, new Identifier(WhenTheSwordIsSus.MOD_ID, "anvil_sword"), ANVIL_SWORD);

        Registry.register(Registry.ITEM, new Identifier(WhenTheSwordIsSus.MOD_ID, "totem_sword"), TOTEM_SWORD);

        Registry.register(Registry.ITEM, new Identifier(WhenTheSwordIsSus.MOD_ID, "gunpowder_sword"), GUNPOWDER_SWORD);

        Registry.register(Registry.ITEM, new Identifier(WhenTheSwordIsSus.MOD_ID, "slime_sword"), SLIME_SWORD);

        Registry.register(Registry.ITEM, new Identifier(WhenTheSwordIsSus.MOD_ID, "flint_and_steel_sword"), FLINT_AND_STEEL_SWORD);

        Registry.register(Registry.ITEM, new Identifier(WhenTheSwordIsSus.MOD_ID, "suspicious_stew_sword"), SUSPICIOUS_STEW_SWORD);

        Registry.register(Registry.ITEM, new Identifier(WhenTheSwordIsSus.MOD_ID, "sculk_sword"), SCULK_SWORD);
    }
}
