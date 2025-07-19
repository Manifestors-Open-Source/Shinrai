package com.manifestors.shinrai.mixins.minecraft.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.client.resource.metadata.TextureResourceMetadata;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.TextureContents;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.IOException;
import java.io.InputStream;

@Mixin(SplashOverlay.class)
public class MixinSplashOverlay {
    @Unique
    private static final Identifier manifestorsLogo = Identifier.of("shinrai","srclogo2.png");



    @Redirect(method = "<clinit>",at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/ColorHelper;getArgb(IIII)I",ordinal = 0))
    private static int manifestorsBlack(int alpha, int red, int green, int blue){

        return ColorHelper.getArgb(0,0,0);
    }


    @Redirect(method = "init",at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/SplashOverlay;LOGO:Lnet/minecraft/util/Identifier;",ordinal = 0))
    private static Identifier init(){
        return manifestorsLogo;
    }
    @Redirect(method = "<clinit>",at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Identifier;ofVanilla(Ljava/lang/String;)Lnet/minecraft/util/Identifier;",ordinal = 0))
    private static Identifier manifestorsLogoFun(String path){
        return Identifier.of("shinrai","textures/mddf.png");
    }

    @Unique

    private static TextureContents loadContents(ResourceManager resourceManager) throws IOException {
        ResourceFactory resourceFactory = MinecraftClient.getInstance().getDefaultResourcePack().getFactory();

        try (InputStream inputStream = resourceFactory.open(SplashOverlay.LOGO)) {
            return new TextureContents(NativeImage.read(inputStream), new TextureResourceMetadata(true, true));
        }
    }
}
