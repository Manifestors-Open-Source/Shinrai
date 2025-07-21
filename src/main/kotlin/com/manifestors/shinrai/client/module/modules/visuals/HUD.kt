package com.manifestors.shinrai.client.module.modules.visuals

import com.manifestors.shinrai.client.Shinrai
import com.manifestors.shinrai.client.event.events.RenderHudEvent
import com.manifestors.shinrai.client.event.listener.ListenEvent
import com.manifestors.shinrai.client.module.Category
import com.manifestors.shinrai.client.module.Module
import net.minecraft.util.math.ColorHelper
import org.lwjgl.glfw.GLFW

object HUD : Module(
    name = "HUD",
    description = "Displays activated modules and watermark.",
    category = Category.VISUALS,
    key = GLFW.GLFW_KEY_M
) {

    @ListenEvent
    fun onRenderHud(event: RenderHudEvent) {
        val context = event.context ?: return

        context.drawText(mc.textRenderer, Shinrai.NAME, 2, 3, ColorHelper.getArgb(196, 24, 41), true)

        var yOffset = 0

        for (module in Shinrai.moduleManager.modules) {
            val xOffset = event.width - mc.textRenderer.getWidth(module.name) - 3
            if (!module.enabled) continue

            context.drawText(mc.textRenderer, module.name, xOffset.toInt(), 4 + yOffset, ColorHelper.getArgb(196, 24, 41), true)
            yOffset += 11
        }
    }

}