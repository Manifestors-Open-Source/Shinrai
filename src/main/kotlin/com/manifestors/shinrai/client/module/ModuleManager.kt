package com.manifestors.shinrai.client.module

import com.manifestors.shinrai.client.module.modules.movement.*
import com.manifestors.shinrai.client.module.modules.visuals.*

class ModuleManager {

    val modules = mutableListOf<Module>()

    fun registerModules() {
        modules.add(Sprint)
        modules.add(NoFOV)
        modules.add(NoHurtCam)
        modules.add(AntiBlind)
        modules.add(HUD)
    }

    fun getModulesFromCategory(category: Category) = modules.filter { it.category == category }
}