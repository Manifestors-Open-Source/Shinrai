package com.manifestors.shinrai.client.utils.rendering

import com.manifestors.shinrai.client.utils.MinecraftInstance
import com.manifestors.shinrai.client.utils.file.ShinraiAssets
import net.minecraft.client.gl.RenderPipelines
import net.minecraft.client.gui.DrawContext
import net.minecraft.util.Identifier

object BackgroundDrawer : MinecraftInstance {

    var isCustomBgActivated = false
    var customBgId: Identifier? = null

    fun drawBackground(context: DrawContext, backgroundName: String?) {
        val backgroundImage = if (isCustomBgActivated) customBgId else ShinraiAssets.getBackgroundId(backgroundName)
        context.drawTexture(
            RenderPipelines.GUI_TEXTURED,
            backgroundImage,
            0,
            0,
            0f,
            0f,
            mc.window.scaledWidth,
            mc.window.scaledHeight,
            mc.window.scaledWidth,
            mc.window.scaledHeight
        )
    }

    @JvmStatic
    fun drawBackground(context: DrawContext) {
        val backgroundImage = if (isCustomBgActivated) customBgId else ShinraiAssets.getBackgroundId("background")

        context.drawTexture(
            RenderPipelines.GUI_TEXTURED,
            backgroundImage,
            0,
            0,
            0f,
            0f,
            mc.window.scaledWidth,
            mc.window.scaledHeight,
            mc.window.scaledWidth,
            mc.window.scaledHeight
        )
    }
}