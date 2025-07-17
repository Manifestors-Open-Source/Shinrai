package com.manifestors.shinrai.client.module

import com.manifestors.shinrai.client.module.modules.movement.*
import com.manifestors.shinrai.client.module.modules.visuals.*

class ModuleManager {

    val modules = mutableListOf<Module>()

    fun registerModules() {
        modules.add(Sprint())
        modules.add(NoFOV())
        modules.add(NoHurtCam())
    }

    fun getModuleFromName(moduleName: String) = modules.find { it.name.equals(moduleName, true) }
    fun <T : Module> getModuleFromClass(moduleClass: Class<T>) = modules.find { it::class.java == moduleClass }

    fun getModulesFromCategory(category: Category) = modules.filter { it.category == category }
}