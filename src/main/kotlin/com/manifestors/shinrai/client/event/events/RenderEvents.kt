package com.manifestors.shinrai.client.event.events

import com.manifestors.shinrai.client.event.ConstantEvent
import net.minecraft.client.gui.DrawContext

class RenderHudEvent(val context: DrawContext? = null, val width: Double, val height: Double) : ConstantEvent()