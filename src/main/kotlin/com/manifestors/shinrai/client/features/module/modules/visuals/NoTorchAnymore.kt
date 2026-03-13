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

package com.manifestors.shinrai.client.features.module.modules.visuals

import com.manifestors.shinrai.client.features.module.Module
import com.manifestors.shinrai.client.features.module.ModuleCategory
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
