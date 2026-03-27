/*
 *
 * Copyright © 2026 Manifestors Open Source
 * License: GPL-3.0
 * All code in this project is the property of the Manifestors Open Source team
 * and its contributors. If you use this code in any project, please provide proper attribution
 * and release your project under the GPL-3.0 license as well.
 * For more details, see: https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 */

package com.manifestors.shinrai.client.imgui.fonts

import com.manifestors.shinrai.client.Shinrai
import com.manifestors.shinrai.client.imgui.ImGuiAccess

object FontManager : ImGuiAccess {

    fun initializeFonts() {
        val path = "/assets/shinrai/font/SF-Pro-Display-Regular.ttf"
        val fontAtlas = io.fonts

        val stream = this.javaClass.getResourceAsStream(path)

        if (stream == null) {
            io.fontDefault = fontAtlas.addFontDefault()
            Shinrai.logger.error("Cannot find font: $path. Using ImGui's default font.")
        } else {
            Shinrai.logger.info("Loading custom ImGui font: $path")

            val bytes = stream.readBytes()

            val font = fontAtlas.addFontFromMemoryTTF(bytes, 26.0f)

            io.fontDefault = font

            Shinrai.logger.info("Custom font(s) loaded successfully.")
        }

        fontAtlas.build()
    }

}