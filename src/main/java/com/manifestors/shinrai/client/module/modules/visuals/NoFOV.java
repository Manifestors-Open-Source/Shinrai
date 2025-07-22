package com.manifestors.shinrai.client.module.modules.visuals;

import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;
import org.lwjgl.glfw.GLFW;

@ModuleData(
        name = "NoFOV",
        description = "Disables FOV camera effect.",
        category = ModuleCategory.VISUALS,
        keyCode = GLFW.GLFW_KEY_V
)
public class NoFOV extends Module {
}
