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

package com.manifestors.shinrai.client.event

import com.manifestors.shinrai.client.Shinrai
import com.manifestors.shinrai.client.event.annotations.InvokeEvent
import java.lang.reflect.Method
import java.util.concurrent.CopyOnWriteArrayList

object EventManager {

    private val listeners = CopyOnWriteArrayList<Any>()
    private val invokers = mutableMapOf<Any, Array<out Method>>()

    fun registerListener(listener: Any) {
        listeners.add(listener)
        invokers[listener] = listener.javaClass.declaredMethods
    }

    fun unregisterListener(listener: Any) {
        listeners.remove(listener)
        invokers.remove(listener)
    }

    fun listenEvent(event: ConstantEvent) {
        for (listenerObject in listeners) {
            val methods = invokers[listenerObject] ?: continue
            methods.forEach { method ->
                if (method.isAnnotationPresent(InvokeEvent::class.java) &&
                    method.parameterTypes[0].isAssignableFrom(event.javaClass)) {
                    try {
                        method.isAccessible = true
                        method.invoke(listenerObject, event)
                    } catch (e: Exception) {
                        Shinrai.logger.error("Event error: ", e)
                    }
                }
            }
        }
    }
}
