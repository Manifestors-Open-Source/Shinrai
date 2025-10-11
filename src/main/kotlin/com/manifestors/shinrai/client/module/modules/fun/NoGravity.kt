package com.manifestors.shinrai.client.module.modules.`fun`

import com.manifestors.shinrai.client.module.Module
import com.manifestors.shinrai.client.module.ModuleCategory

class NoGravity : Module(
    name = "NoGravity",
    description = "Lets Change World Upside down",
    category = ModuleCategory.FUN
) {
    override fun onEnable() {
        val player = mc.player ?: return
        player.setNoGravity(true)
    }

    override fun onDisable() {
        val player = mc.player ?: return
        player.setNoGravity(false)
    }

}
