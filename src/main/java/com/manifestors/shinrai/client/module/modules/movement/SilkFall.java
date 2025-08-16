package com.manifestors.shinrai.client.module.modules.movement;


import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;

@ModuleData(
        name = "SilkFall",
        description = "Block fall damage",
        category = ModuleCategory.MOVEMENT,
        alternatives = "NoFall"

)
public class SilkFall extends Module {
}
