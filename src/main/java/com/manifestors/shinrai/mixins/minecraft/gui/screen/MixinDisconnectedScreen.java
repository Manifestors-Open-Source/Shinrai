package com.manifestors.shinrai.mixins.minecraft.gui.screen;

import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DisconnectedScreen.class)
public class MixinDisconnectedScreen {

    @Shadow
    @Final
    private DirectionalLayoutWidget grid;

    @Shadow
    @Final
    private Screen parent;

    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/DirectionalLayoutWidget;add(Lnet/minecraft/client/gui/widget/Widget;)Lnet/minecraft/client/gui/widget/Widget;", ordinal = 2, shift = At.Shift.AFTER))
    private void addReconnectButton(CallbackInfo ci) {
        var reconnectButton = ButtonWidget.builder(Text.translatable("multiplayer.gui.reconnect"), (button) -> {
            if (parent instanceof MultiplayerScreen mpScreen)
                mpScreen.connect();
        }).width(200).build();
        this.grid.add(reconnectButton);
    }

}
