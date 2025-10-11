package com.manifestors.shinrai.client.event;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.event.annotations.ListenEvent;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class EventManager {
    private final Map<Object, Class<?>> listenableObjects = new HashMap<>();

    public void registerListener(Object listenable) {
        listenableObjects.put(listenable, listenable.getClass());
    }

    public void unregisterListener(Object listenable) {
        listenableObjects.remove(listenable);
    }

    public void listenEvent(ConstantEvent event) {
        try {
            for (Map.Entry<Object, Class<?>> entry : listenableObjects.entrySet()) {
                Object callableObject = entry.getKey();
                Class<?> listenableClass = entry.getValue();

                for (Method method : listenableClass.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(ListenEvent.class)
                            && method.getParameterCount() > 0
                            && method.getParameters()[0].getType().equals(event.getClass())) {
                        method.invoke(callableObject, event);
                    }
                }
            }
        } catch (Exception e) {
            Shinrai.logger.error("An error occurred while listening an event: ", e);
        }
    }
}
