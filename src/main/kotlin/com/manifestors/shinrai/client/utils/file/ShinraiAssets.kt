package com.manifestors.shinrai.client.utils.file

import com.manifestors.shinrai.client.utils.LoggerInstance
import com.manifestors.shinrai.client.utils.MinecraftInstance
import net.minecraft.client.texture.NativeImage
import net.minecraft.client.texture.NativeImageBackedTexture
import net.minecraft.util.Identifier
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.function.Supplier

object ShinraiAssets : MinecraftInstance, LoggerInstance {
    fun getBackgroundId(bgName: String?): Identifier {
        return Identifier.of("shinrai", "backgrounds/$bgName.png")
    }

    fun getTextureId(textureName: String?): Identifier {
        return Identifier.of("shinrai", "textures/$textureName.png")
    }

    fun getDynamicTextureId(textureName: String?): Identifier {
        return Identifier.of("shinrai", "dynamic/$textureName.png")
    }

    fun loadDynamicImage(textureName: String?, imagePath: String) {
        try {
            val path = Paths.get(imagePath)
            val nativeImage = NativeImage.read(Files.readAllBytes(path))
            val texture = NativeImageBackedTexture(Supplier { textureName }, nativeImage)
            mc.textureManager.registerTexture(getDynamicTextureId(textureName), texture)
            logger.info("Registered dynamic image: {}", imagePath)
        } catch (e: IOException) {
            logger.warn("Can't register dynamic image: ", e)
        }
    }
}
