package net.dreamer.wtsis.sound;

import net.dreamer.wtsis.WhenTheSwordIsSus;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WtsisSoundRegistry {
    public static final Identifier ENTITY_ENCHANTED_GOLDEN_APPLE_THROW_ID = new Identifier(WhenTheSwordIsSus.MOD_ID, "entity.enchanted_golden_apple.throw");
    public static SoundEvent ENTITY_ENCHANTED_GOLDEN_APPLE_THROW = new SoundEvent(ENTITY_ENCHANTED_GOLDEN_APPLE_THROW_ID);

    public static final Identifier ENTITY_GLASS_EAT_ID = new Identifier(WhenTheSwordIsSus.MOD_ID, "entity.glass.eat");
    public static final SoundEvent ENTITY_GLASS_EAT = new SoundEvent(ENTITY_GLASS_EAT_ID);

    public static final Identifier ENTITY_METAL_EAT_ID = new Identifier(WhenTheSwordIsSus.MOD_ID, "entity.metal.eat");
    public static final SoundEvent ENTITY_METAL_EAT = new SoundEvent(ENTITY_METAL_EAT_ID);

    public static final Identifier ENTITY_GUNPOWDER_EAT_ID = new Identifier(WhenTheSwordIsSus.MOD_ID, "entity.gunpowder.eat");
    public static final SoundEvent ENTITY_GUNPOWDER_EAT = new SoundEvent(ENTITY_GUNPOWDER_EAT_ID);

    public static final Identifier ENTITY_SLIME_EAT_ID = new Identifier(WhenTheSwordIsSus.MOD_ID, "entity.slime.eat");
    public static final SoundEvent ENTITY_SLIME_EAT = new SoundEvent(ENTITY_SLIME_EAT_ID);

    public static final Identifier ENTITY_SCULK_EAT_ID = new Identifier(WhenTheSwordIsSus.MOD_ID, "entity.sculk.eat");
    public static final SoundEvent ENTITY_SCULK_EAT = new SoundEvent(ENTITY_SCULK_EAT_ID);


    public static void register() {
        Registry.register(Registry.SOUND_EVENT, ENTITY_ENCHANTED_GOLDEN_APPLE_THROW_ID, ENTITY_ENCHANTED_GOLDEN_APPLE_THROW);

        Registry.register(Registry.SOUND_EVENT, ENTITY_GLASS_EAT_ID, ENTITY_GLASS_EAT);

        Registry.register(Registry.SOUND_EVENT, ENTITY_METAL_EAT_ID, ENTITY_METAL_EAT);

        Registry.register(Registry.SOUND_EVENT, ENTITY_GUNPOWDER_EAT_ID, ENTITY_GUNPOWDER_EAT);

        Registry.register(Registry.SOUND_EVENT, ENTITY_SLIME_EAT_ID, ENTITY_SLIME_EAT);

        Registry.register(Registry.SOUND_EVENT, ENTITY_SCULK_EAT_ID, ENTITY_SCULK_EAT);
    }
}
