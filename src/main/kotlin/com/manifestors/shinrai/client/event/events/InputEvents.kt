package com.manifestors.shinrai.client.event.events

import com.manifestors.shinrai.client.Shinrai
import com.manifestors.shinrai.client.event.CancellableEvent

class KeyPressEvent(val key: Int) : CancellableEvent() {
    fun toggleModulesByKey() = Shinrai.moduleManager.modules.forEach {
        if (it.key == key)
            it.toggleModule()
    }
}