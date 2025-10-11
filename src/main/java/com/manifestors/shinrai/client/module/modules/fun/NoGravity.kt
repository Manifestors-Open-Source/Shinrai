package com.manifestors.shinrai.client.module.modules.fun;

import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;

@ModuleData(
        name = "NoGravity",
        description = "Lets Change World Upside down",
        category = ModuleCategory.FUN
)
public class NoGravity extends Module {

    @Override
    public void onEnable() {
        if (mc.player != null)
            mc.player.setNoGravity(true);
    }

    @Override
    public void onDisable() {
        if (mc.player != null)
            mc.player.setNoGravity(false);
    }

}
