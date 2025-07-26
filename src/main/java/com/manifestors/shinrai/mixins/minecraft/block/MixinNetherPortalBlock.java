package com.manifestors.shinrai.mixins.minecraft.block;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.module.modules.extras.NoPortalCoolDown;
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
        if (Shinrai.INSTANCE.getModuleManager().isModuleEnabled(NoPortalCoolDown.class)) {
            cir.setReturnValue(0);
            cir.cancel();
        }
    }

}
