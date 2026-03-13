/*
 *
 *  * Copyright © 2026 Manifestors Open Source
 *  * License: GPL-3.0
 *  *
 *  * All code in this project is the property of the Manifestors Open Source team
 *  * and its contributors. If you use this code in any project, please provide proper attribution
 *  * and release your project under the GPL-3.0 license as well.
 *  *
 *  * For more details, see: https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 */

package com.manifestors.shinrai.mixins.minecraft.gui;

import com.manifestors.shinrai.client.file.ShinraiAssets;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LogoDrawer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(LogoDrawer.class)
public class MixinLogoDrawer {

    /**
     * @author Manifestor, meto1558
     * @reason Draw Shinrai's logo
     */
    @Overwrite
    public void draw(DrawContext context, int screenWidth, float alpha, int y) {
        Identifier logo = ShinraiAssets.INSTANCE.getTextureId("smooth_logo");
        int logoWidth = 256;
        int logoHeight = 64;
        int logoX = screenWidth / 2 - logoWidth / 2;
        context.drawTexture(RenderPipelines.GUI_TEXTURED, logo, logoX, 10, 0F, 0F, logoWidth, logoHeight, logoWidth, logoHeight);
    }

}
