package com.manifestors.shinrai.client.module.modules.movement;


import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;
import org.lwjgl.glfw.GLFW;

@ModuleData(
        name = "NoJumpDelay",
        description = "Block submerge in Water",
        category = ModuleCategory.MOVEMENT,
        keyCode = GLFW.GLFW_KEY_I
)
public class NoJumpDelay extends Module {
}
