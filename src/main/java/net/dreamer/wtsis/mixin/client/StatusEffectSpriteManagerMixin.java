package net.dreamer.wtsis.mixin.client;

import net.dreamer.wtsis.effect.WtsisStatusEffectRegistry;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasHolder;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatusEffectSpriteManager.class)
public abstract class StatusEffectSpriteManagerMixin extends SpriteAtlasHolder {
    public StatusEffectSpriteManagerMixin(TextureManager textureManager,Identifier atlasId,String pathPrefix) {
        super(textureManager,atlasId,pathPrefix);
    }

    @Inject(at = @At("RETURN"), method = "getSprite", cancellable = true)
    public void getSpriteInject(StatusEffect effect,CallbackInfoReturnable<Sprite> cir) {
        if(effect == WtsisStatusEffectRegistry.RIMURU_BOOTLEG_DAMAGE || effect == WtsisStatusEffectRegistry.RIMURU_BOOTLEG_DAMAGEN_T)
            cir.setReturnValue(this.getSprite(Registry.STATUS_EFFECT.getId(WtsisStatusEffectRegistry.RIMURU_BOOTLEG_DAMAGE)));
    }
}
