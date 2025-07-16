package com.manifestors.shinrai.client.module

import com.manifestors.shinrai.client.Shinrai
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
        if (enabled) {
            Shinrai.eventManager.registerListener(this)
            onEnable()
        } else {
            Shinrai.eventManager.unregisterListener(this)
            onDisable()
        }
    }

    open fun onEnable() {}
    open fun onDisable() {}

}