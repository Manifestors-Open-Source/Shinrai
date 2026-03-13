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

package com.manifestors.shinrai.client.utils.input

import org.lwjgl.glfw.GLFW
import java.lang.reflect.Modifier

/**
 * Modified and part of LWJGL 2 code (LWJGL 2 -> org.lwjgl.input.Keyboard)
 * @author meto1558
 * @since 29.07.2025
 */
object GLFWUtil {
    private val keyMap: MutableMap<String?, Int?> = HashMap(253)

    init {
        val fields = GLFW::class.java.fields

        try {
            for (field in fields) {
                if (Modifier.isStatic(field.modifiers) && Modifier.isPublic(field.modifiers) && Modifier.isFinal(
                        field.modifiers
                    ) && field.type == Integer.TYPE && field.name.startsWith("GLFW_KEY_") && !field.name
                        .endsWith("WIN")
                ) {
                    val key = field.getInt(null)
                    val name = field.name.substring(9)
                    keyMap[name] = key
                }
            }
        } catch (ignored: Exception) {
        }
    }

    @JvmStatic
    @Synchronized
    fun getKeyIndex(keyName: String?): Int {
        val ret = keyMap[keyName]
        return ret ?: -1
    }
}
