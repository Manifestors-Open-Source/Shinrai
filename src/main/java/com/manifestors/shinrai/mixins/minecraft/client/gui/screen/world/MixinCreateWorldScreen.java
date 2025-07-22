package com.manifestors.shinrai.mixins.minecraft.client.gui.screen.world;

import com.manifestors.shinrai.client.ui.title.ShinraiTitleScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CreateWorldScreen.class)
public class MixinCreateWorldScreen {

    @Redirect(method = "onCloseScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;setScreen(Lnet/minecraft/client/gui/screen/Screen;)V"))
    private void sa(MinecraftClient instance, Screen screen) {
        instance.setScreen(new ShinraiTitleScreen());
    }

}
