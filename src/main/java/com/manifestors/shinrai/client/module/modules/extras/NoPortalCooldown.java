package com.manifestors.shinrai.client.module.modules.extras;

import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;
import org.lwjgl.glfw.GLFW;

@ModuleData(
        name = "NoPortalCooldown",
        description = "You are not gonna get effected from portal's cooldown.",
        category = ModuleCategory.EXTRAS,
        keyCode = GLFW.GLFW_KEY_C
)
public class NoPortalCooldown extends Module {
}
