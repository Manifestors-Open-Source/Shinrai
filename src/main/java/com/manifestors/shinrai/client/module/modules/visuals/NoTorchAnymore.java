package com.manifestors.shinrai.client.module.modules.visuals;

import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

@ModuleData(
        name = "NoTorchAnymore",
        description = "Disables FOV camera effect.",
        category = ModuleCategory.VISUALS,
        alternatives = "FullBright"
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
        if (mc.player != null)
            mc.player.addStatusEffect(effect);
    }

    @Override
    public void onDisable() {
        if (mc.player != null) {
            mc.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
        }
    }


}
