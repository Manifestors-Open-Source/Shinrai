package com.manifestors.shinrai.client.utils

import net.minecraft.client.Minecraft

interface MinecraftInstance {
    val mc: Minecraft
        get() = Minecraft.getInstance()
}