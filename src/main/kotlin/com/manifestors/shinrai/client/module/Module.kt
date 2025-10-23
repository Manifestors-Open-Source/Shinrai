package com.manifestors.shinrai.client.module

import com.google.gson.annotations.Expose
import com.manifestors.shinrai.client.Shinrai.eventManager
import com.manifestors.shinrai.client.setting.Setting
import com.manifestors.shinrai.client.utils.MinecraftInstance
import java.util.concurrent.CopyOnWriteArrayList

open class Module(
    @Expose var name: String,
    var description: String? = null,
    var category: ModuleCategory,
    @Expose var keyCode: Int = 0,
    vararg var alternativeNames: String
) : MinecraftInstance {

    @Expose
    var enabled: Boolean = false
        private set

    @Expose
    val settings = mutableListOf<Setting<*>>()

    fun toggleModule(state: Boolean = !enabled) {
        enabled = state
        if (enabled) {
            eventManager.registerListener(this)
            onEnable()
        } else {
            eventManager.unregisterListener(this)
            onDisable()
        }
    }

    open fun onEnable() {}
    open fun onDisable() {}
}
