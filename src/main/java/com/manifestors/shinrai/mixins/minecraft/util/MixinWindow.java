package com.manifestors.shinrai.mixins.minecraft.util;

import com.manifestors.shinrai.client.Shinrai;
import net.minecraft.client.util.Icons;
import net.minecraft.client.util.Window;
import net.minecraft.resource.InputSupplier;
import net.minecraft.resource.ResourcePack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Mixin(Window.class)
public class MixinWindow {

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

}
