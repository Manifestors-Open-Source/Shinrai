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
                if (method.parameterTypes[0].isAssignableFrom(event.javaClass)) {
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
