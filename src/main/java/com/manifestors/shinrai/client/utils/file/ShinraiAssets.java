package com.manifestors.shinrai.client.utils.file;

import com.manifestors.shinrai.client.utils.LoggerInstance;
import com.manifestors.shinrai.client.utils.MinecraftInstance;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ShinraiAssets implements MinecraftInstance, LoggerInstance {

    public static Identifier getBackgroundId(String bgName) {
        return Identifier.of("shinrai", "backgrounds/" + bgName + ".png");
    }

    public static Identifier getTextureId(String textureName) {
        return Identifier.of("shinrai", "textures/" + textureName + ".png");
    }

    public static Identifier getDynamicTextureId(String textureName) {
        return Identifier.of("shinrai", "dynamic/" + textureName + ".png");
    }

    public static void loadDynamicImage(String textureName, String imagePath) {
        try {
            Path path = Paths.get(imagePath);
            NativeImage nativeImage = NativeImage.read(Files.readAllBytes(path));
            var texture = new NativeImageBackedTexture(() -> textureName, nativeImage);
            mc.getTextureManager().registerTexture(getDynamicTextureId(textureName), texture);
            logger.info("Registered dynamic image: {}", imagePath);
        } catch (IOException e) {
            logger.warn("Can't register dynamic image: ", e);
        }
    }

}
