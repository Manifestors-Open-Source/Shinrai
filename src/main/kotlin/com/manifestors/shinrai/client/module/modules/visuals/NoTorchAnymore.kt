package com.manifestors.shinrai.client.module.modules.visuals

import com.manifestors.shinrai.client.module.Module
import com.manifestors.shinrai.client.module.ModuleCategory
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects

class NoTorchAnymore : Module(
    name = "NoTorchAnymore",
    description = "Disables FOV camera effect.",
    category = ModuleCategory.VISUALS,
    alternativeNames = arrayOf("FullBright")
) {
    override fun onEnable() {
        val player = mc.player ?: return
        val effect = StatusEffectInstance(
            StatusEffects.NIGHT_VISION,
            2000000,
            0,
            true,  // ambient
            false,  // show particles
            false // show icon
        )
        player.addStatusEffect(effect)
    }

    override fun onDisable() {
        val player = mc.player ?: return
        player.removeStatusEffect(StatusEffects.NIGHT_VISION)
    }
}
