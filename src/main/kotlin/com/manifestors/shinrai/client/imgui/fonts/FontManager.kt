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
        this.javaClass.getResourceAsStream(path)?.let { stream ->
            val bytes = stream.readBytes()

            val fontAtlas = io.fonts

            val font = fontAtlas.addFontFromMemoryTTF(bytes, 26.0f)

            io.fontDefault = font

            fontAtlas.build()
        } ?: Shinrai.logger.error("Cannot find font: $path. Using ImGui's default font.")
    }

}