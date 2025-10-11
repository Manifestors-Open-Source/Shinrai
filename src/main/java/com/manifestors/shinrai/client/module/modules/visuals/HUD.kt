package com.manifestors.shinrai.client.module.modules.visuals

import com.manifestors.shinrai.client.Shinrai
import com.manifestors.shinrai.client.Shinrai.moduleManager
import com.manifestors.shinrai.client.event.annotations.ListenEvent
import com.manifestors.shinrai.client.event.events.rendering.Rendering2DEvent
import com.manifestors.shinrai.client.module.Module
import com.manifestors.shinrai.client.module.ModuleCategory
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffectUtil
import net.minecraft.screen.ScreenTexts
import net.minecraft.text.Text
import java.awt.Color

class HUD : Module(
    name = "HUD",
    description = "Shows active modules and watermark.",
    category = ModuleCategory.VISUALS
) {

    private val colors = arrayOf(
        Color.RED,
        Color.BLUE,
        Color.ORANGE,
        Color.PINK,
        Color.YELLOW,
        Color.MAGENTA,
        Color.CYAN,
        Color(30, 190, 120)
    )

    @ListenEvent
    fun onRendering2D(event: Rendering2DEvent) {
        renderWatermark(event)
        renderArrayList(event)
        renderPotionEffects(event)
    }

    private fun renderWatermark(event: Rendering2DEvent) {
        event.context.drawText(mc.textRenderer, Shinrai.NAME, 2, 3, Color.RED.rgb, true)
    }

    private fun renderArrayList(event: Rendering2DEvent) {
        var yOffset = 0

        val activeModules = moduleManager.modules
            .filter { it.enabled }
            .sortedByDescending { mc.textRenderer.getWidth(it.name) }

        for (module in activeModules) {
            val name = module.name
            val x = event.width - mc.textRenderer.getWidth(name) - 3
            event.context.drawText(mc.textRenderer, name, x, yOffset + 5, Color.RED.rgb, true)
            yOffset += 11
        }
    }

    private fun renderPotionEffects(event: Rendering2DEvent) {
        val player = mc.player ?: return
        val world = mc.world ?: return

        val statusEffects = player.statusEffects
            .sortedByDescending { mc.textRenderer.getWidth(it.effectType.value().name.string) }

        if (statusEffects.isEmpty() || mc.currentScreen?.showsStatusEffects() == true) return

        var yOffset = 0
        statusEffects.forEachIndexed { index, effect ->
            val effectName = getStatusEffectDescription(effect).string + " : " +
                    StatusEffectUtil.getDurationText(effect, 1.0f, world.tickManager.tickRate).literalString
            val x = event.width - mc.textRenderer.getWidth(effectName) - 3
            val y = event.height - 15 - yOffset
            event.context.drawText(mc.textRenderer, effectName, x, y, getRandomColor(index), true)
            yOffset += 11
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

    private fun getRandomColor(index: Int): Int {
        return colors[index % colors.size].rgb
    }
}
