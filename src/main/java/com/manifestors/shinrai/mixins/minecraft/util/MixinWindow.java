/*
 *
 * Copyright © 2026 Manifestors Open Source
 * License: GPL-3.0
 * All code in this project is the property of the Manifestors Open Source team
 * and its contributors. If you use this code in any project, please provide proper attribution
 * and release your project under the GPL-3.0 license as well.
 * For more details, see: https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 */

package com.manifestors.shinrai.mixins.minecraft.util;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.event.EventManager;
import com.manifestors.shinrai.client.event.events.rendering.ImGuiDrawEvent;
import com.manifestors.shinrai.client.imgui.manager.ImGuiManager;
import net.minecraft.client.util.Icons;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.tracy.TracyFrameCapturer;
import net.minecraft.resource.InputSupplier;
import net.minecraft.resource.ResourcePack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Mixin(Window.class)
public class MixinWindow {

    @Shadow
    @Final
    private long handle;

    @Redirect(method = "setIcon", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Icons;getIcons(Lnet/minecraft/resource/ResourcePack;)Ljava/util/List;"))
    private List<InputSupplier<InputStream>> changeIconPath(Icons instance, ResourcePack resources) throws IOException {
        var icon16 = Shinrai.class.getResourceAsStream("/assets/shinrai/textures/icon_16x16.png");
        var icon32 = Shinrai.class.getResourceAsStream("/assets/shinrai/textures/icon_32x32.png");

        if (icon16 == null || icon32 == null)
            return instance.getIcons(resources);

        return List.of(() -> icon16, () -> icon32);
    }

    @Redirect(method = "setIcon", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Icons;getMacIcon(Lnet/minecraft/resource/ResourcePack;)Lnet/minecraft/resource/InputSupplier;"))
    private InputSupplier<InputStream> sa(Icons instance, ResourcePack resourcePack) throws IOException {
        var icon32 = Shinrai.class.getResourceAsStream("/assets/shinrai/textures/icon_32x32.png");

        return icon32 == null ?
                instance.getMacIcon(resourcePack) : () -> icon32;
    }

    @Inject(method = "swapBuffers", at = @At("HEAD"))
    private void sa(TracyFrameCapturer capturer, CallbackInfo ci) {
        ImGuiDrawEvent event = new ImGuiDrawEvent();
        EventManager.INSTANCE.listenEvent(event);
        if (event.isReadyForDraw()) {
            ImGuiManager.INSTANCE.render(event.draw);
        }
    }

}
