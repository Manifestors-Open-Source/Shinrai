package com.manifestors.shinrai.client.module.modules.visuals;

import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;
import org.lwjgl.glfw.GLFW;

@ModuleData(
        name = "NoHurtCam",
        description = "Disables camera shake effect when taking damage.",
        category = ModuleCategory.VISUALS,
        keyCode = GLFW.GLFW_KEY_B
)
public class NoHurtCam extends Module {

}
