package com.manifestors.shinrai.client.utils

import net.minecraft.client.MinecraftClient

interface MinecraftInstance {
    val mc: MinecraftClient
        get() = MinecraftClient.getInstance()
}