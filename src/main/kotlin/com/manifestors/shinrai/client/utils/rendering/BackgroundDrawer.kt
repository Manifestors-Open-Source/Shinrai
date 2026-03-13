/*
 *
 *  * Copyright © 2026 Manifestors Open Source
 *  * License: GPL-3.0
 *  *
 *  * All code in this project is the property of the Manifestors Open Source team
 *  * and its contributors. If you use this code in any project, please provide proper attribution
 *  * and release your project under the GPL-3.0 license as well.
 *  *
 *  * For more details, see: https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 */

package com.manifestors.shinrai.client.utils.rendering

import com.manifestors.shinrai.client.utils.MinecraftInstance
import com.manifestors.shinrai.client.file.ShinraiAssets
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