package com.manifestors.shinrai.client.event

import com.manifestors.shinrai.client.Shinrai
import com.manifestors.shinrai.client.event.listener.ListenEvent

class EventManager {

    private val listenableObjects = hashMapOf<Any, Class<*>>()

    fun registerListener(listenable: Any) {
        listenableObjects[listenable] = listenable::class.java
    }

    fun unregisterListener(listenable: Any) {
        listenableObjects.remove(listenable)
    }

    fun listenEvent(event: ConstantEvent) {
        try {
            for (listenableEntries in listenableObjects) {

                val callableObject = listenableEntries.key
                val listenableClass = listenableEntries.value

                listenableClass.declaredMethods.forEach { method ->
                    if (method.isAnnotationPresent(ListenEvent::class.java) && method.parameterCount > 0 &&
                        method.parameters[0].type == event::class.java) {
                        method.invoke(callableObject, event)
                    }
                }
            }
        } catch (e: Exception) {
            Shinrai.logger.error("An error occurred while listening an event: ", e)
        }

    }

}