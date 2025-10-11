package com.manifestors.shinrai.client.module.modules.movement


import com.manifestors.shinrai.client.module.Module
import com.manifestors.shinrai.client.module.ModuleCategory

object SilkFall : Module(
    name = "SilkFall",
    description = "Block fall damage",
    category = ModuleCategory.MOVEMENT,
    alternativeNames = arrayOf("NoFall")
)
