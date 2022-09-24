package net.dreamer.wtsis.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.dreamer.wtsis.WhenTheSwordIsSus;
import net.dreamer.wtsis.effect.WtsisStatusEffectRegistry;
import net.dreamer.wtsis.util.EffectPredicates;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin extends DrawableHelper {
    @Shadow @Final private MinecraftClient client;

    private static final Identifier SLIMIFIED_HEARTS = new Identifier(WhenTheSwordIsSus.MOD_ID,"textures/gui/slimified_hearts.png");

    @Inject(at = @At(value = "HEAD"), method = "drawHeart", cancellable = true)
    public void renderHealthBarInject(MatrixStack matrices,InGameHud.HeartType type,int x,int y,int v,boolean blinking,boolean halfHeart,CallbackInfo info) {
        boolean bl = type != InGameHud.HeartType.CONTAINER && type != InGameHud.HeartType.ABSORBING;
        if(client.player != null && EffectPredicates.hasEffects(client.player,WtsisStatusEffectRegistry.RIMURU_BOOTLEG_DAMAGE,WtsisStatusEffectRegistry.RIMURU_BOOTLEG_DAMAGEN_T) && bl) {
            if(!blinking) {
                RenderSystem.setShaderTexture(0,SLIMIFIED_HEARTS);
                drawTexture(matrices,x,y,halfHeart ? 16 + 45 : 16 + 36,27,9,9);
                RenderSystem.setShaderTexture(0,GUI_ICONS_TEXTURE);

                info.cancel();
            }
        }
    }
}
