package com.manifestors.shinrai.client.ui.alt

import com.manifestors.shinrai.client.Shinrai.fullVersion
import com.manifestors.shinrai.client.ui.title.ShinraiTitleScreen
import com.manifestors.shinrai.client.utils.MinecraftInstance
import com.manifestors.shinrai.client.utils.rendering.BackgroundDrawer.drawBackground
import com.manifestors.shinrai.mixins.minecraft.network.MixinSessionAccessor
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.LogoDrawer
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.screen.option.CreditsAndAttributionScreen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.gui.widget.PressableTextWidget
import net.minecraft.client.gui.widget.TextFieldWidget
import net.minecraft.client.gui.widget.TextWidget
import net.minecraft.client.session.Session
import net.minecraft.text.Text
import java.util.*

class ShinraiAltMenuScreen : Screen(Text.of("Shinrai Alt Manage Screen")), MinecraftInstance {

    private var altNameField: TextFieldWidget? = null
    private var accessTokenField: TextFieldWidget? = null

    override fun init() {
        val copyrightText: Text = Text.translatable("title.screen.copyright")

        this.altNameField = TextFieldWidget(
            this.textRenderer,
            this.width / 2 - 100,
            this.height / 2 - 50,
            200,
            20,
            Text.translatable("altmanager.gui.textfield")
        )
        this.addDrawableChild<TextFieldWidget?>(altNameField)
        this.addDrawableChild(
            ButtonWidget.builder(Text.translatable("altmanager.gui.altbutton")

            ) {
                enterNewSession(
                    Session(
                        altNameField!!.text,
                        UUID.randomUUID(),
                        "",
                        Optional.empty(),
                        Optional.empty(),
                        Session.AccountType.LEGACY
                    )
                )
            }

                .dimensions(this.width / 2 - 100, this.height / 2 - 20, 200, 20)
                .build()
        )

        this.accessTokenField = TextFieldWidget(
            this.textRenderer,
            this.width / 2 - 100,
            this.height / 2 + 10,
            200,
            20,
            Text.translatable("altmanager.gui.AcesssText")
        )
        this.addDrawableChild<TextFieldWidget?>(accessTokenField)
        this.addDrawableChild(
            ButtonWidget.builder(
                Text.translatable("altmanager.gui.AccessButton")

            ) {
                enterNewSession(
                    Session(
                        "",
                        UUID.fromString(""),
                        "",
                        Optional.empty(),
                        Optional.empty(),
                        Session.AccountType.MSA
                    )
                )
            }

                .dimensions(this.width / 2 - 100, this.height / 2 + 40, 200, 20)
                .build()
        )

        val copyrightTextWidth = this.textRenderer.getWidth(copyrightText)
        val copyrightTextX = this.width - copyrightTextWidth - 2
        val altText: Text = Text.translatable("altmanager.gui.text")

        this.addDrawableChild(
            TextWidget(
                this.width / 2 - textRenderer.getWidth(altText) / 2,
                this.height / 2 - 60,
                copyrightTextWidth,
                10,
                altText,
                this.textRenderer
            )
        )


        this.addDrawableChild(
            PressableTextWidget(
                copyrightTextX,
                this.height - 10,
                copyrightTextWidth,
                10,
                copyrightText,
                { mc.setScreen(CreditsAndAttributionScreen(this)) },
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
                { mc.setScreen(CreditsAndAttributionScreen(this)) },
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
        return true
    }

    override fun close() {
        mc.setScreen(ShinraiTitleScreen())
    }

    override fun shouldPause(): Boolean {
        return false
    }

    fun enterNewSession(alterSession: Session?) {
        (mc as MixinSessionAccessor).setSession(alterSession)
    }
}
