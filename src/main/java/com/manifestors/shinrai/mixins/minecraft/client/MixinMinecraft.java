package com.manifestors.shinrai.mixins.minecraft.client;

import com.manifestors.shinrai.client.Shinrai;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.server.integrated.IntegratedServer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraft {

    @Shadow
    public abstract ClientPlayNetworkHandler getNetworkHandler();
    @Shadow
    public abstract ServerInfo getCurrentServerEntry();
    @Shadow
    public abstract IntegratedServer getServer();

    /**
     * @author meto1558
     * @reason Change title
    */
    @Overwrite
    private @NotNull String getWindowTitle() {
        StringBuilder stringBuilder = new StringBuilder(Shinrai.INSTANCE.getFullVersion());

        stringBuilder.append(" ");
        stringBuilder.append(SharedConstants.getGameVersion().name());
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

}
