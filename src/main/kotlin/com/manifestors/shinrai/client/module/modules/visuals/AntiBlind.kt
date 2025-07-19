package com.manifestors.shinrai.client.module.modules.visuals

import com.manifestors.shinrai.client.module.Category
import com.manifestors.shinrai.client.module.Module
import org.lwjgl.glfw.GLFW

object AntiBlind : Module(
    name = "AntiBlind",
    description = "Removes some bad visual effects for you.",
    category = Category.VISUALS,
    key = GLFW.GLFW_KEY_I
)