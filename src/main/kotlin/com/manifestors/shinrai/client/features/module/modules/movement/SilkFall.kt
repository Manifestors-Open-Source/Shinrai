package com.manifestors.shinrai.client.features.module.modules.movement


import com.manifestors.shinrai.client.features.module.Module
import com.manifestors.shinrai.client.features.module.ModuleCategory

object SilkFall : Module(
    name = "SilkFall",
    description = "Block fall damage",
    category = ModuleCategory.MOVEMENT,
    alternativeNames = arrayOf("NoFall")
)
