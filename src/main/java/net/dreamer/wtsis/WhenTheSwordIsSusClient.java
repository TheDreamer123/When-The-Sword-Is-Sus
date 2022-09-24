package net.dreamer.wtsis;

import net.dreamer.wtsis.entity.WtsisEntityRegistry;
import net.dreamer.wtsis.item.WtsisItemRegistry;
import net.dreamer.wtsis.util.IntColorCalculator;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.impl.client.rendering.ColorProviderRegistryImpl;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.nbt.NbtCompound;

public class WhenTheSwordIsSusClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(WtsisEntityRegistry.ENCHANTED_GOLDEN_APPLE, FlyingItemEntityRenderer::new);

        ColorProviderRegistryImpl.ITEM.register((stack,tintIndex) -> {
            NbtCompound nbt = stack.getOrCreateSubNbt("display");
            if (nbt.contains("color")) return nbt.getInt("color");
            return IntColorCalculator.rgbToInt(255,255,255);
        }, WtsisItemRegistry.GLASS_SWORD);
    }
}
