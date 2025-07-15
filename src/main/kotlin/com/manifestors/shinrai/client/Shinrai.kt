package com.manifestors.shinrai.client

import com.manifestors.shinrai.client.utils.logging.LoggerInstance

object Shinrai : LoggerInstance {

    const val NAME = "Shinrai"
    const val VERSION = "1.0.0"
    const val AUTHORS = "Manifestors"
    const val IN_DEV = true

    fun getFullVersion(): String {
        val nameAndVersion = "$NAME $VERSION"
        return if (IN_DEV) "$nameAndVersion (Development)" else nameAndVersion
    }

}