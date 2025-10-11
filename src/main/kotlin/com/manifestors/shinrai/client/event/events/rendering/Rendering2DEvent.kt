package com.manifestors.shinrai.client.event.events.rendering

import com.manifestors.shinrai.client.event.ConstantEvent
import net.minecraft.client.gui.DrawContext

class Rendering2DEvent(
    val context: DrawContext,
    val width: Int,
    val height: Int
) : ConstantEvent()
