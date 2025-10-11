package com.manifestors.shinrai.client.ui.title;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.ui.alt.ShinraiAltMenuScreen;
import com.manifestors.shinrai.client.ui.custom.ShinraiCustomizationScreen;
import com.manifestors.shinrai.client.utils.MinecraftInstance;
import com.manifestors.shinrai.client.utils.rendering.BackgroundDrawer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LogoDrawer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.CreditsAndAttributionScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.PressableTextWidget;
import net.minecraft.text.Text;

public class ShinraiTitleScreen extends Screen implements MinecraftInstance {

    public ShinraiTitleScreen() {
        super(Text.of("Shinrai Title Screen"));
    }

    @Override
    protected void init() {
        final Text copyrightText = Text.translatable("title.screen.copyright");

        this.addDrawableChild(
                ButtonWidget.builder(Text.translatable("menu.singleplayer"), (button) ->
                                mc.setScreen(new SelectWorldScreen(this)))
                        .dimensions(this.width / 2 - 100, this.height / 4 + 38, 200, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(Text.translatable("menu.multiplayer"), (button) ->
                                mc.setScreen(new MultiplayerScreen(this)))
                        .dimensions(this.width / 2 - 100, this.height / 4 + 63, 200, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(Text.translatable("title.screen.altbutton"), (button) ->
                                mc.setScreen(new ShinraiAltMenuScreen()))
                        .dimensions(this.width / 2 - 100, this.height / 4 + 88, 200, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(Text.translatable("title.screen.customization"), (button) ->
                                mc.setScreen(new ShinraiCustomizationScreen()))
                        .dimensions(this.width / 2 - 100, this.height / 4 + 113, 200, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(Text.translatable("menu.options"), (button) ->
                                mc.setScreen(new OptionsScreen(this, mc.options)))
                        .dimensions(this.width / 2 - 100, this.height / 4 + 140, 98, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(Text.translatable("menu.quit"), (button) ->
                                mc.scheduleStop()
                        )
                        .dimensions(this.width / 2 + 2, this.height / 4 + 140, 98, 20)
                        .build()
        );

        final int copyrightTextWidth = this.textRenderer.getWidth(copyrightText);
        final int copyrightTextX = this.width - copyrightTextWidth - 2;

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
        return false;
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
