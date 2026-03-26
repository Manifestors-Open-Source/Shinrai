/*
 *
 * Copyright © 2026 Manifestors Open Source
 * License: GPL-3.0
 * All code in this project is the property of the Manifestors Open Source team
 * and its contributors. If you use this code in any project, please provide proper attribution
 * and release your project under the GPL-3.0 license as well.
 * For more details, see: https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 */

package com.manifestors.shinrai.client.utils

import com.manifestors.shinrai.client.Shinrai
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

interface LoggerInstance {
    val logger: Logger
        get() = LogManager.getLogger(Shinrai.NAME)
}
