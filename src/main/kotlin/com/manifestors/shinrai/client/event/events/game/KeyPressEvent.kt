package com.manifestors.shinrai.client.event.events.game

import com.manifestors.shinrai.client.Shinrai.moduleManager
import com.manifestors.shinrai.client.event.CancellableEvent

class KeyPressEvent(
    val key: Int
) : CancellableEvent() {

    fun toggleModulesByKey() {
        moduleManager.modules
            .filter { it.keyCode == key }
            .forEach { it.toggleModule() }
    }

}
