package com.manifestors.shinrai.client.module.modules.visuals

import com.manifestors.shinrai.client.module.Category
import com.manifestors.shinrai.client.module.Module
import org.lwjgl.glfw.GLFW

class NoHurtCam : Module(
    name = "NoHurtCam",
    description = "Disables hurt cam effect when you are taking damage.",
    category = Category.VISUALS,
    key = GLFW.GLFW_KEY_B
)