package com.manifestors.shinrai.client.ui.screen

import com.manifestors.shinrai.client.Shinrai
import com.manifestors.shinrai.client.utils.MinecraftInstance
import com.manifestors.shinrai.client.utils.rendering.BackgroundDrawer
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.LogoDrawer
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen
import net.minecraft.client.gui.screen.option.CreditsAndAttributionScreen
import net.minecraft.client.gui.screen.option.OptionsScreen
import net.minecraft.client.gui.screen.world.SelectWorldScreen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.gui.widget.PressableTextWidget
import net.minecraft.text.Text

class CustomTitleScreen : Screen(Text.of("Custom Title Screen")), MinecraftInstance {

    val logoDrawer = LogoDrawer(false)

    override fun init() {
        val copyrightText: Text = Text.translatable("title.screen.copyright")

        this.addDrawableChild(
            ButtonWidget.builder(Text.translatable("menu.singleplayer")) {
                mc.setScreen(SelectWorldScreen(this))
            }.dimensions(this.width / 2 - 100, this.height / 4 + 38, 200, 20).build()
        )

        this.addDrawableChild(
            ButtonWidget.builder(Text.translatable("menu.multiplayer")) {
                mc.setScreen(MultiplayerScreen(this))
            }.dimensions(this.width / 2 - 100, this.height / 4 + 63, 200, 20).build()
        )

        this.addDrawableChild(
            ButtonWidget.builder(Text.translatable("title.screen.altbutton")) {
                mc.setScreen(null)
            }.dimensions(this.width / 2 - 100, this.height / 4 + 88, 200, 20).build()
        )

        this.addDrawableChild(
            ButtonWidget.builder(Text.translatable("menu.options")) {
                mc.setScreen(OptionsScreen(this, this.client!!.options))
            }.dimensions(this.width / 2 - 100, this.height / 4 + 113, 98, 20).build()
        )

        this.addDrawableChild(
            ButtonWidget.builder(Text.translatable("menu.quit")) {
                mc.close()
            }.dimensions(this.width / 2 + 2, this.height / 4 + 113, 98, 20).build()
        )

        val copyrightTextWidth = this.textRenderer.getWidth(copyrightText)
        val copyrightTextXPosition = this.width - copyrightTextWidth - 2

        this.addDrawableChild(
            PressableTextWidget(
                copyrightTextXPosition,
                this.height - 10,
                copyrightTextWidth,
                10,
                copyrightText,
                { button: ButtonWidget? -> mc.setScreen(CreditsAndAttributionScreen(this)) },
                this.textRenderer
            )
        )

        val clientVersionText: Text = Text.of { Shinrai.getFullVersion() }

        this.addDrawableChild(
            PressableTextWidget(
                2,
                this.height - 10,
                this.textRenderer.getWidth(clientVersionText),
                10,
                clientVersionText,
                { button: ButtonWidget? -> mc.setScreen(CreditsAndAttributionScreen(this)) },
                this.textRenderer
            )
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