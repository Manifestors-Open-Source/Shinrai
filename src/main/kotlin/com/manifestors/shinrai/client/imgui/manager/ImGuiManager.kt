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

package com.manifestors.shinrai.client.imgui.manager

import com.manifestors.shinrai.client.Shinrai
import com.manifestors.shinrai.client.imgui.fonts.FontManager
import com.manifestors.shinrai.client.utils.MinecraftInstance
import imgui.ImGui
import imgui.flag.ImGuiConfigFlags
import imgui.gl3.ImGuiImplGl3
import imgui.glfw.ImGuiImplGlfw

object ImGuiManager : MinecraftInstance {

    private var initialized = false
    private val lwjgl3 = ImGuiImplGlfw()
    private val gl3 = ImGuiImplGl3()

    fun init(windowHandle: Long) {
        if (initialized) return

        val ctx = ImGui.createContext()
        ImGui.setCurrentContext(ctx)

        val io = ImGui.getIO()
        io.iniFilename = null

        FontManager.initializeFonts()

        lwjgl3.init(windowHandle, true)
        gl3.init("#version 410 core")

        Shinrai.logger.info("ImGui initialized, version ${ImGui.getVersion()}")

        initialized = true
    }

    fun render(block: () -> Unit) {
        val ctx = ImGui.getCurrentContext()
        if (ctx == null) {
            Shinrai.logger.error("ImGui context is null.")
            return
        }

        if (!initialized) {
            Shinrai.logger.error("ImGui context is not initialized.")
            return
        }

        lwjgl3.newFrame()
        gl3.newFrame()
        ImGui.newFrame()

        block()

        ImGui.render()

        val drawData = ImGui.getDrawData()
        gl3.renderDrawData(drawData)
    }
}