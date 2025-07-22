package com.manifestors.shinrai.client.module.modules.visuals;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.event.annotations.ListenEvent;
import com.manifestors.shinrai.client.event.events.rendering.Rendering2DEvent;
import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

@ModuleData(
        name = "HUD",
        description = "Shows active modules and watermark.",
        category = ModuleCategory.VISUALS,
        keyCode = GLFW.GLFW_KEY_H
)
public class HUD extends Module {

    @ListenEvent
    public void onRendering2D(Rendering2DEvent event) {
        var context = event.getContext();

        context.drawText(mc.textRenderer, Shinrai.NAME, 2, 3, Color.RED.getRGB(), true);

        int yOffset = 0;

        var activeModules = Shinrai.INSTANCE.getModuleManager().getModules().stream()
                .filter(Module::isEnabled)
                .sorted((m1, m2) -> mc.textRenderer.getWidth(m2.getName()) - mc.textRenderer.getWidth(m1.getName()))
                .toList();

        for (Module module : activeModules) {
            int x = event.getWidth() - mc.textRenderer.getWidth(module.getName()) - 3;
            context.drawText(mc.textRenderer, module.getName(), x, yOffset + 5, Color.RED.getRGB(), true);

            yOffset += 11;
        }
    }

}
