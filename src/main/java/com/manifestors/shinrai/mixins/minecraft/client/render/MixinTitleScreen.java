package com.manifestors.shinrai.mixins.minecraft.client.render;

import com.manifestors.shinrai.client.utils.rendering.BackgroundDrawer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LogoDrawer;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class MixinTitleScreen {

    @Final
    @Shadow
    private LogoDrawer logoDrawer;

    @Unique
    private final MinecraftClient client = MinecraftClient.getInstance();

    /**
     * @author Manifestor, meto1558
     * @reason Change title screen
     */
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;renderPanoramaBackground(Lnet/minecraft/client/gui/DrawContext;F)V", shift = At.Shift.AFTER), cancellable = true)
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci) {
        BackgroundDrawer.drawBackgroundTexture(context);
        logoDrawer.draw(context, client.getWindow().getScaledWidth(), 0);
    }

}
