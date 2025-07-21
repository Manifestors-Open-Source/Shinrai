package com.manifestors.shinrai.client

import com.manifestors.shinrai.client.event.EventManager
import com.manifestors.shinrai.client.module.ModuleManager
import com.manifestors.shinrai.client.utils.logging.LoggerInstance

object Shinrai : LoggerInstance {

    const val NAME = "Shinrai"
    const val VERSION = "1.0.0"
    const val AUTHORS = "Manifestors"
    const val IN_DEV = true

    lateinit var moduleManager: ModuleManager
    lateinit var eventManager: EventManager

    fun initializeShinrai() {
        moduleManager = ModuleManager()
        moduleManager.registerModules()
        eventManager = EventManager()
    }


    fun getFullVersion(): String {
        val nameAndVersion = "$NAME $VERSION"
        return if (IN_DEV) "$nameAndVersion (Development)" else nameAndVersion
    }

}