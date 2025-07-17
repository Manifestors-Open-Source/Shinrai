package com.manifestors.shinrai.client.module.modules.visuals

import com.manifestors.shinrai.client.module.Category
import com.manifestors.shinrai.client.module.Module
import org.lwjgl.glfw.GLFW

class NoFOV : Module(
    name = "NoFOV",
    description = "Disables dynamic FOV effect.",
    category = Category.VISUALS,
    key = GLFW.GLFW_KEY_V
)