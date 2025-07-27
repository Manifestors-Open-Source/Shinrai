package com.manifestors.shinrai.client.module.modules.visuals;

import com.manifestors.shinrai.client.event.annotations.ListenEvent;
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent;
import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;
import com.manifestors.shinrai.mixins.minecraft.entity.MixinGameOptionsAccessor;
import net.minecraft.block.Portal;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.lwjgl.glfw.GLFW;

import java.lang.reflect.Field;

@ModuleData(
        name = "NoTorchAnymore",
        description = "Disables FOV camera effect.",
        category = ModuleCategory.VISUALS,
        keyCode = GLFW.GLFW_KEY_N
)

public class NoTorchAnymore extends Module {
    @Override
    public void onEnable() {
        StatusEffectInstance effect = new StatusEffectInstance(
                StatusEffects.NIGHT_VISION,
                2000000,
                0,
                true, // ambient
                false, // show particles
                false  // show icon
        );
            mc.player.addStatusEffect(effect);
    }

    @Override
    public void onDisable() {
        if (mc.player != null) {
            mc.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
        }
    }


}
