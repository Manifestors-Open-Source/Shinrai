package com.manifestors.shinrai.client.module;

import com.manifestors.shinrai.client.module.modules.movement.*;

import com.manifestors.shinrai.client.module.modules.visuals.HUD;
import com.manifestors.shinrai.client.module.modules.visuals.NoFOV;
import com.manifestors.shinrai.client.module.modules.visuals.NoHurtCam;
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
    }

    public <T extends Module> boolean isModuleEnabled(Class<T> moduleClass) {
        return modules.stream()
                .filter(module -> moduleClass == module.getClass())
                .findAny().orElseThrow()
                .isEnabled();
    }
}
