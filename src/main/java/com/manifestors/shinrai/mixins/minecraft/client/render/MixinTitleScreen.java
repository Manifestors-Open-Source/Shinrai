package com.manifestors.shinrai.mixins.minecraft.client.render;


import com.manifestors.shinrai.files.LogoDrawer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;

import java.util.Objects;

@Mixin(TitleScreen.class)
public class MixinTitleScreen {

    @Unique
    private final MinecraftClient client = MinecraftClient.getInstance();

    @Unique
    private final LogoDrawer logoDrawer = new LogoDrawer(false);
    @Unique
    private final boolean ignoreAlpha = false;

    @Overwrite
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks){
        Identifier BG = Identifier.of("shinrai","background.png");


        context.drawTexture(RenderPipelines.GUI_TEXTURED, BG, 0, 0, 0F, 0F, client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight(), client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight());
        this.logoDrawer.draw(context, this.client.getWindow().getScaledWidth()/2, this.logoDrawer.shouldIgnoreAlpha() ? 1.0F : 1F);
    }

    public boolean shouldIgnoreAlpha() {
        return this.ignoreAlpha;
    }
}
