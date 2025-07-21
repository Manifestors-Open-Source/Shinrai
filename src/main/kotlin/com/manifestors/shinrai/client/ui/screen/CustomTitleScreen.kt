package com.manifestors.shinrai.client.ui.screen

import com.manifestors.shinrai.client.utils.MinecraftInstance
import com.manifestors.shinrai.client.utils.rendering.BackgroundDrawer
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.LogoDrawer
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.screen.world.SelectWorldScreen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.text.Text

class CustomTitleScreen : Screen(Text.of("Custom Title Screen")), MinecraftInstance {

    val logoDrawer = LogoDrawer(false)

    override fun init() {
        this.addDrawableChild(
            ButtonWidget.builder(Text.translatable("menu.singleplayer")) {
                mc.setScreen(SelectWorldScreen(this))
            }.dimensions(this.width / 2 - 100, this.height / 4 + 48, 200, 20).build()
        )
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, deltaTicks: Float) {
        BackgroundDrawer.drawBackgroundTexture(context)
        logoDrawer.draw(context, mc.window.scaledWidth, 0F)
        super.render(context, mouseX, mouseY, deltaTicks)
    }

    override fun shouldPause() = false
    override fun shouldCloseOnEsc() = false

}