package com.manifestors.shinrai.client.module;

import com.manifestors.shinrai.client.module.modules.combat.BackToOldPVP;
import com.manifestors.shinrai.client.module.modules.extras.NoPortalCooldown;
import com.manifestors.shinrai.client.module.modules.fun.NoGravity;
import com.manifestors.shinrai.client.module.modules.movement.*;

import com.manifestors.shinrai.client.module.modules.visuals.HUD;
import com.manifestors.shinrai.client.module.modules.visuals.NoFOV;
import com.manifestors.shinrai.client.module.modules.visuals.NoHurtCam;
import com.manifestors.shinrai.client.module.modules.visuals.NoTorchAnymore;
import lombok.Getter;

import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class ModuleManager {

    private final CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<>();

    public void registerModules() {
        modules.add(new Sprint());
        modules.add(new NoFOV());
        modules.add(new NoHurtCam());
        modules.add(new HUD());
        modules.add(new Jesus());
        modules.add(new NoPortalCooldown());
        modules.add(new NoGravity());
        modules.add(new NoJumpDelay());
        modules.add(new NoTorchAnymore());
        modules.add(new BackToOldPVP());
    }

    public <T extends Module> boolean isModuleEnabled(Class<T> moduleClass) {
        return modules.stream()
                .filter(module -> moduleClass == module.getClass())
                .findAny().orElseThrow()
                .isEnabled();
    }
}
