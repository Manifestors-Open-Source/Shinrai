package com.manifestors.shinrai.client.utils.movement;

import com.manifestors.shinrai.client.utils.MinecraftInstance;
import net.minecraft.util.math.MathHelper;

public class MovementUtils implements MinecraftInstance {

    public static boolean isMoving() {
        if (mc.player == null) return false;
        return mc.player.input.getMovementInput().x != 0.0F || mc.player.input.getMovementInput().y != 0.0F;
    }

    public static float getSpeed() {
        if (mc.player == null) return 0.0F;

        var x = mc.player.getVelocity().x;
        var z = mc.player.getVelocity().z;

        return (float) Math.sqrt(x * x + z * z);
    }

    public static void applyStrafe() {
        applyStrafe(getSpeed());
    }

    public static void applyStrafe(float speed) {
        if (mc.player != null) {
            float forward = mc.player.input.getMovementInput().y;
            float strafe = mc.player.input.getMovementInput().x;

            if (forward == 0.0 && strafe == 0.0) {
                mc.player.setVelocity(0.0, mc.player.getVelocity().y, 0.0);
                return;
            }

            // Normalize input
            if (forward != 0.0) forward = forward > 0.0 ? 1.0f : -1.0f;
            if (strafe != 0.0) strafe = strafe > 0.0 ? 1.0f : -1.0f;

            // Diagonal movement normalization
            if (forward != 0.0 && strafe != 0.0) {
                forward *= (float) Math.sin(45);
                strafe *= (float) Math.sin(45);
            }

            var direction = Math.toRadians(mc.player.getBodyYaw());

            var x = (forward * -Math.sin(direction) + strafe * Math.cos(direction)) * speed;
            var z = (forward * Math.cos(direction) + strafe * Math.sin(direction)) * speed;

            mc.player.setVelocity(x, mc.player.getVelocity().y, z);
        }
    }

    public static boolean isLookingDiagonally() {
        var direction = mc.player.bodyYaw;

        var yaw = Math.round(Math.abs(MathHelper.wrapDegrees(direction)) / 45f) * 45f;

        var isYawDiagonal = yaw % 90f != 0f;
        var isMovingDiagonal = mc.player.getMovement().y != 0f && mc.player.getMovement().x == 0f;

        return isYawDiagonal || isMovingDiagonal;
    }

}
