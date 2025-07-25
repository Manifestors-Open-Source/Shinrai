package com.manifestors.shinrai.client.module.modules.combat;

import com.manifestors.shinrai.client.event.annotations.ListenEvent;
import com.manifestors.shinrai.client.event.events.network.PacketReceivingEvent;
import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import org.lwjgl.glfw.GLFW;

@ModuleData(
        name = "Velocity",
        description = "Reduces velocity when you are taking damage. (a.k.a AntiKnockBack)",
        category = ModuleCategory.COMBAT,
        keyCode = GLFW.GLFW_KEY_B,
        alternatives = {"AntiKnockBack", "AntiVelocity"}
)
public class Velocity extends Module {

    @ListenEvent
    public void onPacketReading(PacketReceivingEvent event) {
        var p = event.getPacket();
        if (p instanceof EntityVelocityUpdateS2CPacket)
            event.setCancelled(true);
    }

}
