package com.manifestors.shinrai.mixins.minecraft.block;

import com.manifestors.shinrai.client.module.modules.extras.NoPortalCooldown;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NetherPortalBlock.class)
public class MixinNetherPortalBlock {

    @Inject(method = "getPortalDelay", at = @At(value = "HEAD"), cancellable = true)
    private void removePortalDelay(ServerWorld world, Entity entity, CallbackInfoReturnable<Integer> cir) {
        if (NoPortalCooldown.INSTANCE.getEnabled()) {
            cir.setReturnValue(0);
            cir.cancel();
        }
    }

}
