package com.manifestors.shinrai.client.module;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.module.annotations.ModuleData;
import com.manifestors.shinrai.client.utils.MinecraftInstance;
import lombok.Getter;

@Getter
public class Module implements MinecraftInstance {

    private String name;
    private String description;
    private ModuleCategory category;
    private int keyCode;

    private boolean enabled = false;

    protected Module() {
        if (getClass().isAnnotationPresent(ModuleData.class)) {
            var moduleData = getClass().getAnnotation(ModuleData.class);
            this.name = moduleData.name();
            this.description = moduleData.description().isEmpty() ? "module." + name+ ".desc" : moduleData.description();
            this.category = moduleData.category();
            this.keyCode = moduleData.keyCode();
        } else {
            Shinrai.logger.error("A module have not ModuleData annotation: {}", getClass().getSimpleName());
        }

    }

    public void toggleModule() {
        enabled = !enabled;
        if (enabled) {
            Shinrai.INSTANCE.getEventManager().registerListener(this);
            onEnable();
        } else {
            Shinrai.INSTANCE.getEventManager().unregisterListener(this);
            onDisable();
        }
    }

    public void onEnable() {}
    public void onDisable() {}

}
