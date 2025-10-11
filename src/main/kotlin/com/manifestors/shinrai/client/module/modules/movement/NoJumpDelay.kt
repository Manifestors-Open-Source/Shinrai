package com.manifestors.shinrai.client.module.modules.movement


import com.manifestors.shinrai.client.module.Module
import com.manifestors.shinrai.client.module.ModuleCategory

object NoJumpDelay : Module(
    name = "NoJumpDelay",
    description = "Removes jump delay when jumping between blocks.",
    category = ModuleCategory.MOVEMENT
)
