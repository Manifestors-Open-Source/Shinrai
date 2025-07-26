package com.manifestors.shinrai.client.module.modules.visuals;

import com.manifestors.shinrai.client.event.annotations.ListenEvent;
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent;
import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;
import net.minecraft.client.option.Perspective;
import org.lwjgl.glfw.GLFW;

@ModuleData(
        name = "NoTorchAnymore",
        description = "Disables FOV camera effect.",
        category = ModuleCategory.VISUALS,
        keyCode = GLFW.GLFW_KEY_N
)

public class NoTorchAnymore extends Module {
    private double previousGamma;
    @ListenEvent
    public void onTickMovement(TickMovementEvent event) {
        previousGamma = mc.options.getGamma().getValue();
        mc.options.setPerspective(Perspective.THIRD_PERSON_BACK);

    }
}
