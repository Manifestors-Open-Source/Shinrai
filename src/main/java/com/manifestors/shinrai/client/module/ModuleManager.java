package com.manifestors.shinrai.client.module;

import com.google.common.reflect.TypeToken;
import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.module.modules.combat.*;
import com.manifestors.shinrai.client.module.modules.extras.*;
import com.manifestors.shinrai.client.module.modules.fun.*;
import com.manifestors.shinrai.client.module.modules.movement.*;
import com.manifestors.shinrai.client.module.modules.visuals.*;
import com.manifestors.shinrai.client.utils.file.FileManager;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class ModuleManager {

    private final CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<>();

    public void registerModules() {
        try {
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
            modules.add(new Speed());
            modules.add(new Velocity());
            Shinrai.logger.info("Loaded {} modules.", modules.size());
        } catch (Exception e) {
            Shinrai.logger.error("Can't load modules", e);
        }
    }

    public <T extends Module> boolean isModuleEnabled(Class<T> moduleClass) {
        return modules.stream()
                .filter(module -> moduleClass == module.getClass())
                .findAny().orElseThrow()
                .isEnabled();
    }

    @Nullable
    public Module getModuleByName(String moduleName) {
        return modules.stream()
                .filter(module -> {
                    if (module.getName().equalsIgnoreCase(moduleName)) return true;

                    for (String altName : module.getAlternativeNames())
                        if (altName.equalsIgnoreCase(moduleName)) return true;
                    return false;
                })
                .findAny().orElse(null);
    }

    public void saveModulesJson() {
        var json = FileManager.toJsonOnlyExposedFields(modules);
        FileManager.writeJsonToFile(json, "settings", "modules.shinrai");
    }

    public void loadModulesFromJson() {
        Type type = new TypeToken<List<Map<String, Object>>>(){}.getType();
        var json = FileManager.getJsonFromFile("settings", "modules.shinrai");
        if (json.isEmpty())
            return;

        List<Map<String, Object>> list = FileManager.getObjectFromJson(json, type);
        for (Map<String, Object> modulesMap : list) {
            var module = getModuleByName((String) modulesMap.get("name"));
            if (module != null) {
                module.setKeyCode(((Double) modulesMap.get("keyCode")).intValue());
                module.toggleModule((boolean) modulesMap.get("enabled"));
            }
        }
    }

}
