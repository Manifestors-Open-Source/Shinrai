package com.manifestors.shinrai.client.module.modules.combat;

import com.manifestors.shinrai.client.event.annotations.ListenEvent;
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent;
import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;

@ModuleData(
        name = "AutoDodge",
        description = "",
        category = ModuleCategory.COMBAT
)
public class AutoDodge extends Module {

    @ListenEvent
    public void tick(TickMovementEvent a){
        checkIncomingArrows(mc.player,mc.world);
        checkIncomingFireBalls(mc.player,mc.world);
        checkIncomingSnowballs(mc.player,mc.world);
    }
    public static void checkIncomingArrows(PlayerEntity player, World world) {
        List<ArrowEntity> arrows = world.getEntitiesByClass(
                ArrowEntity.class,
                player.getBoundingBox().expand(10),
                arrow -> true
        );
        for (ArrowEntity arrow : arrows) {
            Vec3d arrowVel = arrow.getVelocity().normalize();
            Vec3d toPlayer = player.getPos().subtract(arrow.getPos()).normalize();
            double dot = arrowVel.dotProduct(toPlayer);
            if (dot > 0.9) {
                Vec3d right = player.getRotationVec(1.0F).crossProduct(new Vec3d(0,1,0)).normalize();
                Vec3d left = right.multiply(-1); // sol yön
                Vec3d dodgeDirection = Math.random() > 0.5 ? right : left;
                double dodgeSpeed = 0.7;
                player.setVelocity(dodgeDirection.multiply(dodgeSpeed).add(0, player.getVelocity().y, 0));
            }
        }
    }
    public static void checkIncomingFireBalls(PlayerEntity player, World world) {
        List<FireballEntity> Fireballs = world.getEntitiesByClass(
                FireballEntity.class,
                player.getBoundingBox().expand(10),
                arrow -> true
        );
        for (FireballEntity fireball : Fireballs) {
            Vec3d arrowVel = fireball.getVelocity().normalize();
            Vec3d toPlayer = player.getPos().subtract(fireball.getPos()).normalize();
            double dot = arrowVel.dotProduct(toPlayer);
            if (dot > 0.9) {
                Vec3d right = player.getRotationVec(1.0F).crossProduct(new Vec3d(0,1,0)).normalize();
                Vec3d left = right.multiply(-1);
                Vec3d dodgeDirection = Math.random() > 0.5 ? right : left;
                double dodgeSpeed = 0.7;
                player.setVelocity(dodgeDirection.multiply(dodgeSpeed).add(0, player.getVelocity().y, 0));
            }
        }
    }
    public static void checkIncomingSnowballs(PlayerEntity player, World world) {
        List<SnowballEntity> Snowballs = world.getEntitiesByClass(
                SnowballEntity.class,
                player.getBoundingBox().expand(10),
                arrow -> true
        );
        for (SnowballEntity snowball : Snowballs) {
            Vec3d arrowVel = snowball.getVelocity().normalize();
            Vec3d toPlayer = player.getPos().subtract(snowball.getPos()).normalize();
            double dot = arrowVel.dotProduct(toPlayer);
            if (dot > 0.9) {
                Vec3d right = player.getRotationVec(1.0F).crossProduct(new Vec3d(0,1,0)).normalize();
                Vec3d left = right.multiply(-1);
                Vec3d dodgeDirection = Math.random() > 0.5 ? right : left;
                double dodgeSpeed = 0.7;
                player.setVelocity(dodgeDirection.multiply(dodgeSpeed).add(0, player.getVelocity().y, 0));
            }
        }
    }

}
