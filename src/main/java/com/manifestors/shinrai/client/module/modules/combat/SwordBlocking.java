package com.manifestors.shinrai.client.module.modules.combat;

import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;
import com.manifestors.shinrai.client.module.modules.combat.config.SwordBlockingConfig;

@ModuleData(
        name = "SwordBlocking",
        description = "Use sword for blocking while you hold shield",
        category = ModuleCategory.COMBAT
)
public class SwordBlocking extends Module {
    public void onEnable() {

        SwordBlockingConfig.enabled = true;
    }
    public void onDisable() {
        SwordBlockingConfig.enabled = false;

    }
}
