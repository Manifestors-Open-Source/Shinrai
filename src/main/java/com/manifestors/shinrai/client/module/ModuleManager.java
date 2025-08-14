package com.manifestors.shinrai.client.module;

import com.google.common.reflect.TypeToken;
import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.module.modules.combat.*;
import com.manifestors.shinrai.client.module.modules.extras.*;
import com.manifestors.shinrai.client.module.modules.fun.*;
import com.manifestors.shinrai.client.module.modules.movement.*;
import com.manifestors.shinrai.client.module.modules.player.BlockFly;
import com.manifestors.shinrai.client.module.modules.player.ChestStealer;
import com.manifestors.shinrai.client.module.modules.visuals.*;
import com.manifestors.shinrai.client.utils.file.FileManager;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class ModuleManager {

    private final CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<>();

    public void registerModules() {
        Shinrai.logger.info("Loading modules...");
            addModules(
                    // Combat
                    new BackToOldPVP(),
                    new Velocity(),
                    // Movement
                    new Jesus(),
                    new NoJumpDelay(),
                    new NoSlow(),
                    new Speed(),
                    new Sprint(),
                    // Player
                    new BlockFly(),
                    new ChestStealer(),
                    // Visuals
                    new HUD(),
                    new NoFOV(),
                    new NoHurtCam(),
                    new NoTorchAnymore(),
                    // Extras
                    new NoPortalCooldown(),
                    // Fun
                    new NoGravity()
            );
        Shinrai.logger.info("Loaded {} modules.", modules.size());
    }

    private void addModules(Module ... modules) {
        try {
            this.modules.addAll(Arrays.asList(modules));
        } catch (Exception e) {
            Shinrai.logger.error("Can't load modules", e);
        }
    }

    public <T extends Module> boolean isModuleEnabled(Class<T> moduleClass) {
        return modules.stream()
                .filter(module -> moduleClass == module.getClass())
                .findFirst().orElseThrow()
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
                .findFirst().orElse(null);
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
