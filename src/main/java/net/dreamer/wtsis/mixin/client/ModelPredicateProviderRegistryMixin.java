package net.dreamer.wtsis.mixin.client;

import net.dreamer.wtsis.WhenTheSwordIsSus;
import net.dreamer.wtsis.misc.BooleanModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(ModelPredicateProviderRegistry.class)
public class ModelPredicateProviderRegistryMixin {
    @Shadow @Final private static Map<Identifier, ModelPredicateProvider> GLOBAL;

    private static final Identifier MOLTEN_TIME_ID = new Identifier(WhenTheSwordIsSus.MOD_ID, "molten_time");
    private static final Identifier EXPLOSION_TICKS_ID = new Identifier(WhenTheSwordIsSus.MOD_ID, "explosion_ticks");

    private static void registerMoltenTime(BooleanModelPredicateProvider provider) {
        GLOBAL.put(MOLTEN_TIME_ID, provider);
    }

    private static void registerExplosionTicks(BooleanModelPredicateProvider provider) {
        GLOBAL.put(EXPLOSION_TICKS_ID, provider);
    }

    static {
        registerMoltenTime((stack, world, entity, seed) -> {
            if(stack.hasNbt()) return stack.getNbt().contains("moltenTime");
            return false;
        });

        registerExplosionTicks((stack, world, entity, seed) -> {
            if(stack.hasNbt()) return stack.getNbt().contains("explosionTicks");
            return false;
        });
    }
}
