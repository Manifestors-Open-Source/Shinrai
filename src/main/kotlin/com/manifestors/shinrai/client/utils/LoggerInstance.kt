package com.manifestors.shinrai.client.utils

import com.manifestors.shinrai.client.Shinrai
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

interface LoggerInstance {
    val logger: Logger
        get() = LogManager.getLogger(Shinrai.NAME)
}
