package com.manifestors.shinrai.client.ui.title

import com.manifestors.shinrai.client.Shinrai.fullVersion
import com.manifestors.shinrai.client.ui.alt.ShinraiAltMenuScreen
import com.manifestors.shinrai.client.ui.custom.ShinraiCustomizationScreen
import com.manifestors.shinrai.client.utils.MinecraftInstance
import com.manifestors.shinrai.client.utils.rendering.BackgroundDrawer.drawBackground
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

class ShinraiTitleScreen : Screen(Text.of("Shinrai Title Screen")), MinecraftInstance {

    override fun init() {
        val copyrightText: Text = Text.translatable("title.screen.copyright")

        this.addDrawableChild(
            ButtonWidget.builder(
                Text.translatable("menu.singleplayer")
            ) {  mc.setScreen(SelectWorldScreen(this)) }
                .dimensions(this.width / 2 - 100, this.height / 4 + 38, 200, 20)
                .build()
        )

        this.addDrawableChild(
            ButtonWidget.builder(
                Text.translatable("menu.multiplayer")
            ) {  mc.setScreen(MultiplayerScreen(this)) }
                .dimensions(this.width / 2 - 100, this.height / 4 + 63, 200, 20)
                .build()
        )

        this.addDrawableChild(
            ButtonWidget.builder(
                Text.translatable("title.screen.altbutton")
            ) {  mc.setScreen(ShinraiAltMenuScreen()) }
                .dimensions(this.width / 2 - 100, this.height / 4 + 88, 200, 20)
                .build()
        )

        this.addDrawableChild(
            ButtonWidget.builder(
                Text.translatable("title.screen.customization")
            ) {  mc.setScreen(ShinraiCustomizationScreen()) }
                .dimensions(this.width / 2 - 100, this.height / 4 + 113, 200, 20)
                .build()
        )

        this.addDrawableChild(
            ButtonWidget.builder(
                Text.translatable("menu.options")
            ) {  mc.setScreen(OptionsScreen(this, mc.options)) }
                .dimensions(this.width / 2 - 100, this.height / 4 + 140, 98, 20)
                .build()
        )

        this.addDrawableChild(
            ButtonWidget.builder(
                Text.translatable("menu.quit")
            ) {  mc.scheduleStop() }
                .dimensions(this.width / 2 + 2, this.height / 4 + 140, 98, 20)
                .build()
        )

        val copyrightTextWidth = this.textRenderer.getWidth(copyrightText)
        val copyrightTextX = this.width - copyrightTextWidth - 2

        this.addDrawableChild(
            PressableTextWidget(
                copyrightTextX,
                this.height - 10,
                copyrightTextWidth,
                10,
                copyrightText,
                {  mc.setScreen(CreditsAndAttributionScreen(this)) },
                this.textRenderer
            )
        )

        val clientVersionText = Text.of(fullVersion)

        this.addDrawableChild(
            PressableTextWidget(
                2,
                this.height - 10,
                this.textRenderer.getWidth(clientVersionText),
                10,
                clientVersionText,
                {  mc.setScreen(CreditsAndAttributionScreen(this)) },
                this.textRenderer
            )
        )
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, deltaTicks: Float) {
        val drawer = LogoDrawer(false)
        drawBackground(context)
        drawer.draw(context, mc.window.scaledWidth, 0f)
        super.render(context, mouseX, mouseY, deltaTicks)
    }

    override fun shouldCloseOnEsc(): Boolean {
        return false
    }

    override fun shouldPause(): Boolean {
        return false
    }
}
