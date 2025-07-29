package com.manifestors.shinrai.client.module;

import com.google.gson.annotations.Expose;
import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.module.annotations.ModuleData;
import com.manifestors.shinrai.client.utils.MinecraftInstance;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Module implements MinecraftInstance {

    @Expose
    private String name;
    private String description;
    private ModuleCategory category;
    @Setter
    @Expose
    private int keyCode;
    private String[] alternativeNames;

    @Expose
    private boolean enabled;

    protected Module() {
        if (getClass().isAnnotationPresent(ModuleData.class)) {
            var moduleData = getClass().getAnnotation(ModuleData.class);
            this.name = moduleData.name();
            this.description = moduleData.description().isEmpty() ? "module." + name+ ".desc" : moduleData.description();
            this.category = moduleData.category();
            this.keyCode = moduleData.keyCode();
            this.alternativeNames = moduleData.alternatives();
        } else {
            Shinrai.logger.error("A module have not ModuleData annotation: {}", getClass().getSimpleName());
        }

    }

    public void toggleModule() {
        toggleModule(!enabled);
    }

    public void toggleModule(boolean state) {
        enabled = state;
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
