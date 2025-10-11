package com.manifestors.shinrai.client.event

import com.manifestors.shinrai.client.Shinrai
import com.manifestors.shinrai.client.event.annotations.ListenEvent

object EventManager {

    private val listeners = mutableMapOf<Any, Class<*>>()

    fun registerListener(listener: Any) {
        listeners[listener] = listener.javaClass
    }

    fun unregisterListener(listener: Any) {
        listeners.remove(listener)
    }

    fun listenEvent(event: ConstantEvent) {
        try {
            for ((listenerObject, clazz) in listeners) {
                clazz.declaredMethods.forEach { method ->
                    val annotation = method.getAnnotation(ListenEvent::class.java)
                    if (annotation != null &&
                        method.parameterCount > 0 &&
                        method.parameters[0].type.isAssignableFrom(event.javaClass)
                    ) {
                        method.isAccessible = true
                        method.invoke(listenerObject, event)
                    }
                }
            }
        } catch (e: Exception) {
            Shinrai.logger.error("An error occurred while handling event: ", e)
        }
    }
}
