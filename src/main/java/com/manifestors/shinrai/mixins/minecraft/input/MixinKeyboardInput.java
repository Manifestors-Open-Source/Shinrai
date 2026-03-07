package com.manifestors.shinrai.mixins.minecraft.input;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.event.events.player.MovementInputEvent;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.util.math.Vec2f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public class MixinKeyboardInput extends Input {

    @Inject(method = "tick", at = @At("TAIL"))
    private void hookMoveInput(CallbackInfo ci) {
        Vec2f rawMoveVec = this.movementVector;
        MovementInputEvent event = new MovementInputEvent(rawMoveVec.y, rawMoveVec.x);

        Shinrai.eventManager.listenEvent(event);

        this.movementVector = event.toVec();
    }

}
