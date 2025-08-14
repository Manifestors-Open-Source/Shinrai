package com.manifestors.shinrai.client.ui.alt;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.ui.title.ShinraiTitleScreen;
import com.manifestors.shinrai.client.utils.MinecraftInstance;
import com.manifestors.shinrai.client.utils.rendering.BackgroundDrawer;
import com.manifestors.shinrai.mixins.minecraft.network.MixinSessionAccessor;
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
    private TextFieldWidget accesTokenField;

    public ShinraiAltMenuScreen() {
        super(Text.of("Shinrai Alt Manage Screen"));
    }
    protected void init() {
        final Text copyrightText = Text.translatable("title.screen.copyright");

        this.altNameField = new TextFieldWidget(this.textRenderer, this.width / 2 - 100, this.height / 2 - 50, 200, 20, Text.translatable("altmanager.gui.textfield"));
        this.addDrawableChild(altNameField);
        this.addDrawableChild(
                ButtonWidget.builder(Text.translatable("altmanager.gui.altbutton"), (button) ->

                enterNewSession(new Session(altNameField.getText(), UUID.randomUUID(),"", Optional.empty(),Optional.empty(), Session.AccountType.LEGACY))

                        )

                        .dimensions(this.width / 2 - 100,this.height/2 - 20 , 200, 20)
                        .build()
        );

        this.accesTokenField = new TextFieldWidget(this.textRenderer, this.width / 2 - 100, this.height / 2 + 10, 200, 20, Text.translatable("altmanager.gui.AcesssText"));
        this.addDrawableChild(accesTokenField);
        this.addDrawableChild(
                ButtonWidget.builder(Text.translatable("altmanager.gui.AccessButton"), (button) ->

                                enterNewSession(new Session("MANIFESTORS", UUID.fromString("0a512f4a-2da2-4261-b4c3-e69b20875fa6"),"eyJraWQiOiIwNDkxODEiLCJhbGciOiJSUzI1NiJ9.eyJ4dWlkIjoiMjUzNTQzODA2OTQzMDM1MyIsImFnZyI6IkFkdWx0Iiwic3ViIjoiMDc2ZTc5OWItNmJiMS00MTFmLTg1NWQtM2U0NTZiMzAxOTUyIiwiYXV0aCI6IlhCT1giLCJucyI6ImRlZmF1bHQiLCJyb2xlcyI6W10sImlzcyI6ImF1dGhlbnRpY2F0aW9uIiwiZmxhZ3MiOlsibXVsdGlwbGF5ZXIiLCJ0d29mYWN0b3JhdXRoIiwibXNhbWlncmF0aW9uX3N0YWdlNCIsIm9yZGVyc18yMDIyIl0sInByb2ZpbGVzIjp7Im1jIjoiMGE1MTJmNGEtMmRhMi00MjYxLWI0YzMtZTY5YjIwODc1ZmE2In0sInBsYXRmb3JtIjoiUENfTEFVTkNIRVIiLCJwZmQiOlt7InR5cGUiOiJtYyIsImlkIjoiMGE1MTJmNGEtMmRhMi00MjYxLWI0YzMtZTY5YjIwODc1ZmE2IiwibmFtZSI6Ik1BTklGRVNUT1JTIn1dLCJuYmYiOjE3NTUxMDkyMzYsImV4cCI6MTc1NTE5NTYzNiwiaWF0IjoxNzU1MTA5MjM2fQ.sFw5qFoos76_DqTv-l5zR0gR1c5KWplnsRFT0e71mvYrttoUEeuqJPt546GT9f4Z0FwPhjAtHqQ0zF0HRvFUanmIKuUyZNSafmizR_qLDkr0VsRLmVFuGZS17z_MF_2WC3gxTlLEiGGZ_lbWENbMWBe90Z-PsKG9M7KFz9XFrPpL47KRlfNIFwJxaLQbnZD-L95JnYBwob90pPnR8-c3ukLlqch7rPG4C9TjY2Ib1LnSlmE4uhG98lK77EsI6QfzoFYZPGkqI-KdgrvCW3Fet9DmjvXxP5FBBnObMCEU4rcRnWBNNbTN_QN_Flb2p__KZnoznYhRGcXJMIDHxNuVCA", Optional.empty(),Optional.empty(), Session.AccountType.MSA))

                        )

                        .dimensions(this.width / 2 - 100,this.height/2 + 40 , 200, 20)
                        .build()
        );

        final int copyrightTextWidth = this.textRenderer.getWidth(copyrightText);
        final int copyrightTextX = this.width - copyrightTextWidth - 2;
        final Text altText = Text.translatable("altmanager.gui.text");

        this.addDrawableChild(
                new TextWidget(
                        this.width / 2 - textRenderer.getWidth(altText) / 2,
                        this.height /2 - 60,
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
