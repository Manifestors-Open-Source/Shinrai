package com.manifestors.shinrai.mixins.minecraft.client;

import com.manifestors.shinrai.client.Shinrai;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.server.IntegratedServer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

    @Shadow
    public abstract ClientPacketListener getConnection();
    @Shadow
    public abstract ServerData getCurrentServer();
    @Shadow
    public abstract IntegratedServer getSingleplayerServer();

    /**
     * @author meto1558
     * @reason Change title
     */
    @Overwrite
    private @NotNull String createTitle() {
        StringBuilder stringBuilder = new StringBuilder(Shinrai.INSTANCE.getFullVersion());
        stringBuilder.append(" ");
        ClientPacketListener clientPacketListener = this.getConnection();
        if (clientPacketListener != null && clientPacketListener.getConnection().isConnected()) {
            stringBuilder.append(" - ");
            ServerData serverData = this.getCurrentServer();
            if (this.getSingleplayerServer() != null && !this.getSingleplayerServer().isPublished()) {
                stringBuilder.append(I18n.get("title.singleplayer"));
            } else if (this.getSingleplayerServer() == null && (serverData == null || !serverData.isLan())) {
                stringBuilder.append(I18n.get("title.multiplayer.other"));
            } else {
                stringBuilder.append(I18n.get("title.multiplayer.lan"));
            }
            stringBuilder.append(" ");
        }
        stringBuilder.append(String.format("[%s]", SharedConstants.getCurrentVersion().name()));

        return stringBuilder.toString();
    }

}
