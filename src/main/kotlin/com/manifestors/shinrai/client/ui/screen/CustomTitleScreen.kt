package com.manifestors.shinrai.client.ui.screen

import com.manifestors.shinrai.client.Shinrai
import com.manifestors.shinrai.client.utils.MinecraftInstance
import com.manifestors.shinrai.client.utils.rendering.BackgroundDrawer
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.LogoDrawer
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.screen.TitleScreen
import net.minecraft.client.gui.screen.option.CreditsAndAttributionScreen
import net.minecraft.client.gui.screen.option.OptionsScreen
import net.minecraft.client.gui.screen.world.SelectWorldScreen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.gui.widget.ButtonWidget.PressAction
import net.minecraft.client.gui.widget.PressableTextWidget
import net.minecraft.text.Text

class CustomTitleScreen : Screen(Text.of("Custom Title Screen")), MinecraftInstance {

    val COPYRIGHT: Text = Text.translatable("title.screen.copyright")
    val logoDrawer = LogoDrawer(false)
    override fun init() {
        this.addDrawableChild(
            ButtonWidget.builder(Text.translatable("menu.singleplayer")) {
                mc.setScreen(SelectWorldScreen(this))
            }.dimensions(this.width / 2 - 100, this.height / 4 + 38, 200, 20).build()

        )

        this.addDrawableChild(
            ButtonWidget.builder(Text.translatable("menu.multiplayer")) {
                mc.setScreen(SelectWorldScreen(this))
            }.dimensions(this.width / 2 - 100, this.height / 4 + 63, 200, 20).build()

        )
        this.addDrawableChild(
            ButtonWidget.builder(Text.translatable("title.screen.altbutton")) {
                mc.setScreen(SelectWorldScreen(this))
            }.dimensions(this.width / 2 - 100, this.height / 4 + 88, 200, 20).build()

        )

        this.addDrawableChild(
            ButtonWidget.builder(Text.translatable("menu.options")) {
                mc.setScreen(OptionsScreen(this, this.client!!.options))
            }.dimensions(this.width / 2 - 100, this.height / 4 + 113, 98, 20).build()

        )

        this.addDrawableChild(
            ButtonWidget.builder(Text.translatable("menu.quit")) {
                mc.setScreen(SelectWorldScreen(this))
            }.dimensions(this.width / 2 + 2, this.height / 4 + 113, 98, 20).build()

        )
        val i = this.textRenderer.getWidth(COPYRIGHT)
        val j = this.width - i - 2
        this.addDrawableChild<PressableTextWidget?>(
            PressableTextWidget(
                j,
                this.height - 10,
                i,
                10,
                COPYRIGHT,
                PressAction { button: ButtonWidget? -> this.client!!.setScreen(CreditsAndAttributionScreen(this)) },
                this.textRenderer
            )
        )
        val k: Text = Text.of {Shinrai.NAME + " Client " + Shinrai.VERSION}
        this.addDrawableChild<PressableTextWidget?>(
            PressableTextWidget(
                2,
                this.height - 10,
                i,
                10,
                k,
                PressAction { button: ButtonWidget? -> this.client!!.setScreen(CreditsAndAttributionScreen(this)) },
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