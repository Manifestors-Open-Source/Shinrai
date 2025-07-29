package com.manifestors.shinrai.client.module.modules.movement;

import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;

@ModuleData(
        name = "NoSlow",
        description = "Removes slowness effect when consuming or using item.",
        category = ModuleCategory.MOVEMENT
)
public class NoSlow extends Module {
}
