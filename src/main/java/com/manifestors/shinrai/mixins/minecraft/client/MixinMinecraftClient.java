package com.manifestors.shinrai.mixins.minecraft.client;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.ui.title.ShinraiTitleScreen;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.server.integrated.IntegratedServer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {

    @Shadow
    public abstract ClientPlayNetworkHandler getNetworkHandler();

    @Shadow
    public abstract ServerInfo getCurrentServerEntry();

    @Shadow
    public abstract IntegratedServer getServer();

    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
    private void test(Screen screen, CallbackInfo ci) {
        if (screen instanceof TitleScreen) {
            MinecraftClient.getInstance().setScreen(new ShinraiTitleScreen());
            ci.cancel();
        }
    }

    /**
     * @author meto1558
     * @reason Change title
     */
    @Overwrite
    private @NotNull String getWindowTitle() {
        StringBuilder stringBuilder = new StringBuilder(Shinrai.INSTANCE.getFullVersion());

        stringBuilder.append(" ");
        var clientPlayNetworkHandler = this.getNetworkHandler();
        if (clientPlayNetworkHandler != null && clientPlayNetworkHandler.getConnection().isOpen()) {
            stringBuilder.append(" - ");
            var serverInfo = this.getCurrentServerEntry();
            if (this.getServer() != null && !this.getServer().isRemote()) {
                stringBuilder.append(I18n.translate("title.singleplayer"));
            } else if (this.getServer() == null && (serverInfo == null || !serverInfo.isLocal())) {
                stringBuilder.append(I18n.translate("title.multiplayer.other"));
            } else {
                stringBuilder.append(I18n.translate("title.multiplayer.lan"));
            }
            stringBuilder.append(" ");
        }
        stringBuilder.append(String.format("[%s]", SharedConstants.getGameVersion().name()));

        return stringBuilder.toString();
    }

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void initializeShinrai(CallbackInfo ci) {
        Shinrai.INSTANCE.initializeShinrai();
    }

}
