package com.manifestors.shinrai.client.module.modules.visuals;

import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;

@ModuleData(
        name = "NoHurtCam",
        description = "Disables camera shake effect when taking damage.",
        category = ModuleCategory.VISUALS
)
public class NoHurtCam extends Module {

}
