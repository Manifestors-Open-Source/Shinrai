package com.manifestors.shinrai.client.module.modules.movement;


import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;

@ModuleData(
        name = "NoJumpDelay",
        description = "Removes jump delay when jumping between blocks.",
        category = ModuleCategory.MOVEMENT
)
public class NoJumpDelay extends Module {
}
