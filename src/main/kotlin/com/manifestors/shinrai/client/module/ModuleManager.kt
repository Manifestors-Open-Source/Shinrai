package com.manifestors.shinrai.client.module

import com.manifestors.shinrai.client.module.modules.movement.Sprint

class ModuleManager {

    val modules = mutableListOf<Module>()

    fun registerModules() {
        modules.add(Sprint())
    }

    fun getModuleFromName(moduleName: String) = modules.find { it.name.equals(moduleName, true) }
    fun getModuleFromClass(moduleClass: Class<Module>) = modules.find { it::class.java == moduleClass }

    fun getModulesFromCategory(category: Category) = modules.filter { it.category == category }
}