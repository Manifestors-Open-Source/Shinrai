package com.manifestors.shinrai.client.utils.input;

import org.lwjgl.glfw.GLFW;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Modified and part of LWJGL 2 code (LWJGL 2 -> org.lwjgl.input.Keyboard)
 * @author meto1558
 * @since 29.07.2025
 * */
public class GLFWUtil {

    private static final Map<String, Integer> keyMap = new HashMap<>(253);

    static {
        Field[] fields = GLFW.class.getFields();

        try {
            for(var field : fields) {

                if (Modifier.isStatic(field.getModifiers()) && Modifier.isPublic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()) && field.getType().equals(Integer.TYPE) && field.getName().startsWith("GLFW_KEY_") && !field.getName().endsWith("WIN")) {
                    int key = field.getInt(null);
                    String name = field.getName().substring(9);
                    keyMap.put(name, key);
                }
            }
        } catch (Exception ignored) {}
    }

    public static synchronized int getKeyIndex(String keyName) {
        Integer ret = keyMap.get(keyName);
        return ret == null ? -1 : ret;
    }

}
