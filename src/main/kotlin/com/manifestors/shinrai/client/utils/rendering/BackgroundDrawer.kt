package com.manifestors.shinrai.client.utils.rendering

import com.manifestors.shinrai.client.utils.MinecraftInstance
import com.manifestors.shinrai.client.utils.file.ShinraiAssets
import net.minecraft.client.gl.RenderPipelines
import net.minecraft.client.gui.DrawContext
import net.minecraft.util.Identifier

object BackgroundDrawer : MinecraftInstance {

    @JvmStatic
    fun drawBackgroundTexture(context: DrawContext) {
        val background = ShinraiAssets.getIdFromBackgroundsFolder("background.png")
        context.drawTexture(
            RenderPipelines.GUI_TEXTURED,
            background,
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