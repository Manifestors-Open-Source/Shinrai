package com.manifestors.shinrai.client.ui.custom.splash

import com.manifestors.shinrai.client.utils.MinecraftInstance
import com.manifestors.shinrai.client.utils.file.ShinraiAssets
import net.minecraft.client.gl.RenderPipelines
import net.minecraft.client.gui.DrawContext
import net.minecraft.util.Identifier

/**
 * User can change splash screen logo.
 * @author meto1558
 * @since 14.10.2025
 * */
class CustomSplashLogo(val style: SplashScreenStyle = SplashScreenStyle.DEFAULT) : MinecraftInstance {

    fun getStyleId(): Identifier {
        return when (style) {
            SplashScreenStyle.DEFAULT -> getId()
            SplashScreenStyle.SAMSUNG -> getId("samsung")
            SplashScreenStyle.APPLE -> getId("apple")
            SplashScreenStyle.LINUX -> getId("tux")
            else -> getId()
        }
    }

    fun getImagePositions(): List<Int> {
        val defaultPosition = listOf(64, 64, 128, 128) // x offset, y offset, width, height
        return when(style) {
            SplashScreenStyle.DEFAULT -> defaultPosition
            SplashScreenStyle.SAMSUNG -> listOf(64, 35, 128, 70)
            SplashScreenStyle.APPLE -> listOf(32, 32, 64, 64)
            SplashScreenStyle.LINUX -> listOf(40, 40, 80, 80)
            else -> defaultPosition
        }
    }

    fun drawSplash(context: DrawContext) {
        val x = mc.window.scaledWidth / 2 - getImagePositions()[0]
        val y = mc.window.scaledHeight / 2 - getImagePositions()[1]
        val width = getImagePositions()[2]
        val height = getImagePositions()[3]

        context.drawTexture(
            RenderPipelines.GUI_TEXTURED,
            getStyleId(),
            x,
            y,
            0f,
            0f,
            width,
            height,
            width,
            height
        )
    }

    private fun getId(name: String = "mopensource"): Identifier = ShinraiAssets.getTextureId("splashes/$name")

}

enum class SplashScreenStyle(val displayName: String) {
    DEFAULT("Manifestors"),
    SAMSUNG("Samsung"),
    APPLE("Apple"),
    LINUX("Linux"),
    CUSTOM_IMAGE("Custom (not ready yet)")
}