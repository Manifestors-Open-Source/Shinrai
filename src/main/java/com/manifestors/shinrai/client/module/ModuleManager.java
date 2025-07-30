package com.manifestors.shinrai.client.module;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.utils.file.FileManager;
import com.manifestors.shinrai.client.module.modules.combat.BackToOldPVP;
import com.manifestors.shinrai.client.module.modules.extras.NoPortalCooldown;
import com.manifestors.shinrai.client.module.modules.fun.NoGravity;
import com.manifestors.shinrai.client.module.modules.movement.Jesus;
import com.manifestors.shinrai.client.module.modules.movement.NoJumpDelay;
import com.manifestors.shinrai.client.module.modules.movement.Sprint;
import com.manifestors.shinrai.client.module.modules.visuals.HUD;
import com.manifestors.shinrai.client.module.modules.visuals.NoFOV;
import com.manifestors.shinrai.client.module.modules.visuals.NoHurtCam;
import com.manifestors.shinrai.client.module.modules.visuals.NoTorchAnymore;
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
        Gson gson = new Gson();
        Type type = new TypeToken<List<Map<String, Object>>>(){}.getType();
        var json = FileManager.getJsonFromFile("settings", "modules.shinrai");
        if (json.isEmpty())
            return;

        List<Map<String, Object>> list = gson.fromJson(json, type);
        for (Map<String, Object> modulesMap : list) {
            var module = getModuleByName((String) modulesMap.get("name"));
            if (module != null) {
                module.setKeyCode(((Double) modulesMap.get("keyCode")).intValue());
                module.toggleModule((boolean) modulesMap.get("enabled"));
            } else {
                Shinrai.logger.error("module is null");
            }
        }
    }

}
