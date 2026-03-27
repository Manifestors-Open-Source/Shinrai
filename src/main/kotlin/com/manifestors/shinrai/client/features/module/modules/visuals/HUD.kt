/*
 *
 * Copyright © 2026 Manifestors Open Source
 * License: GPL-3.0
 * All code in this project is the property of the Manifestors Open Source team
 * and its contributors. If you use this code in any project, please provide proper attribution
 * and release your project under the GPL-3.0 license as well.
 * For more details, see: https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 */

package com.manifestors.shinrai.client.features.module.modules.visuals

import com.manifestors.shinrai.client.Shinrai
import com.manifestors.shinrai.client.event.annotations.InvokeEvent
import com.manifestors.shinrai.client.event.events.rendering.ImGuiDrawEvent
import com.manifestors.shinrai.client.features.module.Module
import com.manifestors.shinrai.client.features.module.ModuleCategory
import com.manifestors.shinrai.client.features.module.ModuleManager
import com.manifestors.shinrai.client.imgui.ImGuiAccess
import imgui.ImColor
import imgui.ImGui
import imgui.ImVec2
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffectUtil
import net.minecraft.screen.ScreenTexts
import net.minecraft.text.Text

class HUD : Module(
    name = "HUD",
    description = "Shows active modules and watermark.",
    category = ModuleCategory.VISUALS
), ImGuiAccess {

    @InvokeEvent
    fun onDraw(event: ImGuiDrawEvent) {
        event.isReadyForDraw = mc.world != null
        event.draw = {
            renderWatermark()
            renderArrayList()
            renderPotionEffects()
        }
    }

    private fun renderWatermark() {
        drawList.addText(ImVec2(2f, 3f), ImColor.rgb(255, 0, 0), Shinrai.NAME)
    }

    private fun renderArrayList() {
        var yOffset = 0f

        val activeModules = ModuleManager.modules
            .filter { it.enabled }
            .sortedByDescending { ImGui.calcTextSize(it.name).x }

        for (module in activeModules) {
            val name = module.name
            val x = io.displaySizeX - ImGui.calcTextSize(name).x - 3

            drawList.addText(ImVec2(x, yOffset), ImColor.rgb(255, 0, 0), name)
            yOffset += io.fontDefault.fontSize
        }
    }

    private fun renderPotionEffects() {
        val player = mc.player ?: return
        val world = mc.world ?: return

        val statusEffects = player.statusEffects
            .sortedByDescending { ImGui.calcTextSize(it.effectType.value().name.string).x }

        if (statusEffects.isEmpty() || mc.currentScreen?.showsStatusEffects() == true) return

        var yOffset = 0f
        val fontSize = io.fontDefault.fontSize
        statusEffects.forEachIndexed { index, effect ->
            val effectName = getStatusEffectDescription(effect).string + " : " +
                    StatusEffectUtil.getDurationText(effect, 1.0f, world.tickManager.tickRate).literalString
            val x = io.displaySizeX - ImGui.calcTextSize(effectName).x - 3
            val y = io.displaySizeY - fontSize - yOffset
            drawList.addText(ImVec2(x, y), ImColor.rgb(255, 0, 0), effectName)
            yOffset += fontSize
        }
    }

    private fun getStatusEffectDescription(effect: StatusEffectInstance): Text {
        val text = effect.effectType.value().name.copy()
        val amplifier = effect.amplifier
        if (amplifier in 1..9) {
            text.append(ScreenTexts.SPACE)
                .append(Text.translatable("enchantment.level.${amplifier + 1}"))
        }
        return text
    }

}
