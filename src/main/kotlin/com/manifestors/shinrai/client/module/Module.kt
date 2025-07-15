package com.manifestors.shinrai.client.module

import com.manifestors.shinrai.client.utils.MinecraftInstance
import com.manifestors.shinrai.client.utils.logging.LoggerInstance

open class Module(
    val name: String,
    val description: String = "",
    val category: Category,
    val key: Int = 0
) : MinecraftInstance, LoggerInstance {

    var enabled = false

    fun toggleModule() {
        enabled = !enabled
        if (enabled)
            onEnable()
        else
            onDisable()
    }

    open fun onEnable() {}
    open fun onDisable() {}

}