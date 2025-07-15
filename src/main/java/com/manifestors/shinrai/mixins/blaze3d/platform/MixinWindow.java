package com.manifestors.shinrai.mixins.blaze3d.platform;

import com.manifestors.shinrai.client.Shinrai;
import com.mojang.blaze3d.platform.IconSet;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.resources.IoSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Mixin(Window.class)
public class MixinWindow {

    @Redirect(method = "setIcon", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/IconSet;getStandardIcons(Lnet/minecraft/server/packs/PackResources;)Ljava/util/List;"))
    private List<IoSupplier<InputStream>> changeIconPath(IconSet instance, PackResources resources) throws IOException {
        InputStream icon16 = Shinrai.class.getResourceAsStream("/assets/shinrai/icon_16x16.png");
        InputStream icon32 = Shinrai.class.getResourceAsStream("/assets/shinrai/icon_32x32.png");

        if (icon16 == null || icon32 == null)
            return instance.getStandardIcons(resources);

        return List.of(() -> icon16, () -> icon32);
    }

}
