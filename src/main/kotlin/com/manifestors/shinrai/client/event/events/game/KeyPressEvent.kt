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

package com.manifestors.shinrai.client.event.events.game

import com.manifestors.shinrai.client.event.CancellableEvent
import com.manifestors.shinrai.client.features.module.ModuleManager

class KeyPressEvent(
    val key: Int
) : CancellableEvent() {

    fun toggleModulesByKey() {
        ModuleManager.modules
            .filter { it.keyCode == key }
            .forEach { it.toggleModule() }
    }

}
