/*
 *
 *  * Copyright © 2026 Manifestors Open Source
 *  * License: GPL-3.0
 *  *
 *  * All code in this project is the property of the Manifestors Open Source team
 *  * and its contributors. If you use this code in any project, please provide proper attribution
 *  * and release your project under the GPL-3.0 license as well.
 *  *
 *  * For more details, see: https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 */

package com.manifestors.shinrai.client.features.module

import com.google.gson.annotations.Expose
import com.manifestors.shinrai.client.Shinrai.eventManager
import com.manifestors.shinrai.client.setting.Setting
import com.manifestors.shinrai.client.utils.MinecraftInstance

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
