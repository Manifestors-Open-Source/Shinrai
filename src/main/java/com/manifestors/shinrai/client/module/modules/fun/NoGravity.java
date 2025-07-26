package com.manifestors.shinrai.client.module.modules.fun;

import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;
import org.lwjgl.glfw.GLFW;

@ModuleData(
        name = "NoGravity",
        description = "Lets Change World Upside down",
        category = ModuleCategory.FUN,
        keyCode = GLFW.GLFW_KEY_G
)
public class NoGravity extends Module {

    @Override
    public void onEnable() {
        mc.player.setNoGravity(true);
    }

    @Override
    public void onDisable() {
        mc.player.setNoGravity(false);
    }

}
