package com.manifestors.shinrai.client.ui.alt;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.ui.title.ShinraiTitleScreen;
import com.manifestors.shinrai.client.utils.MinecraftInstance;
import com.manifestors.shinrai.client.utils.rendering.BackgroundDrawer;
import com.manifestors.shinrai.mixins.minecraft.client.network.MixinSessionAccessor;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LogoDrawer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.CreditsAndAttributionScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.PressableTextWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.session.Session;
import net.minecraft.text.Text;

import java.util.Optional;
import java.util.UUID;

public class ShinraiAltMenuScreen extends Screen implements MinecraftInstance {

    private TextFieldWidget altNameField;

    public ShinraiAltMenuScreen() {
        super(Text.of("Shinrai Alt Manage Screen"));
    }
    protected void init() {
        final Text copyrightText = Text.translatable("title.screen.copyright");

        this.altNameField = new TextFieldWidget(this.textRenderer, this.width / 2 - 100, this.height / 2 - 10, 200, 20, Text.translatable("altmanager.gui.textfield"));
        this.addDrawableChild(altNameField);
        this.addDrawableChild(
                ButtonWidget.builder(Text.translatable("altmanager.gui.altbutton"), (button) ->

                enterNewSession(new Session(altNameField.getText(), UUID.randomUUID(),"", Optional.empty(),Optional.empty(), Session.AccountType.LEGACY))

                        )

                        .dimensions(this.width / 2 - 100,this.height/2 + 20 , 200, 20)
                        .build()
        );

        final int copyrightTextWidth = this.textRenderer.getWidth(copyrightText);
        final int copyrightTextX = this.width - copyrightTextWidth - 2;
        final Text altText = Text.translatable("altmanager.gui.text");

        this.addDrawableChild(
                new TextWidget(
                        this.width / 2 - textRenderer.getWidth(altText) / 2,
                        this.height /2 - 25,
                        copyrightTextWidth,
                        10,
                        altText,
                        this.textRenderer
                )
        );
        this.addDrawableChild(
                new PressableTextWidget(
                        copyrightTextX,
                        this.height - 10,
                        copyrightTextWidth,
                        10,
                        copyrightText,
                        (button) -> mc.setScreen(new CreditsAndAttributionScreen(this)),
                        this.textRenderer
                )
        );

        final Text clientVersionText = Text.of(Shinrai.INSTANCE.getFullVersion());

        this.addDrawableChild(
                new PressableTextWidget(
                        2,
                        this.height - 10,
                        this.textRenderer.getWidth(clientVersionText),
                        10,
                        clientVersionText,
                        (button) -> mc.setScreen(new CreditsAndAttributionScreen(this)),
                        this.textRenderer
                )
        );
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        final LogoDrawer drawer = new LogoDrawer(false);
        BackgroundDrawer.drawBackground(context);
        drawer.draw(context, mc.getWindow().getScaledWidth(), 0);
        super.render(context, mouseX, mouseY, deltaTicks);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public void close() {
        mc.setScreen(new ShinraiTitleScreen());
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    void enterNewSession(Session alterSession){
        ((MixinSessionAccessor) mc).setSession(alterSession);
    }

}
