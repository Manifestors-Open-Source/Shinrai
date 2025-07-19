package com.manifestors.shinrai.client.module.modules.movement

import com.manifestors.shinrai.client.event.events.TickEvent
import com.manifestors.shinrai.client.event.listener.ListenEvent
import com.manifestors.shinrai.client.module.Category
import com.manifestors.shinrai.client.module.Module
import org.lwjgl.glfw.GLFW

object Sprint : Module(
    name = "Sprint",
    description = "Automatically sprints for you.",
    category = Category.MOVEMENT,
    key = GLFW.GLFW_KEY_Z
) {

    @ListenEvent
    fun onTick(event: TickEvent) {
        val player = mc.player ?: return
        player.isSprinting = true
    }

}