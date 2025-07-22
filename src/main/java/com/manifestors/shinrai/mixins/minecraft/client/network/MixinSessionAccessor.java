package com.manifestors.shinrai.mixins.minecraft.client.network;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.session.Session;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MinecraftClient.class)
public interface MixinSessionAccessor {
    @Accessor("session")
    @Mutable
    void setSession(Session session);
}
