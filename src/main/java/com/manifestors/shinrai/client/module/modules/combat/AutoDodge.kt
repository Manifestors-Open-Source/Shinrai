package com.manifestors.shinrai.client.module.modules.combat;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.event.annotations.ListenEvent;
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent;
import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.client.module.ModuleCategory;
import com.manifestors.shinrai.client.module.annotations.ModuleData;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

@ModuleData(
        name = "AutoDodge",
        description = "Module will dodge arrow, fireballs and snowballs automatically.",
        category = ModuleCategory.COMBAT
)
public class AutoDodge extends Module {

    private boolean dodging = false;
    private int dodgeTicks = 0;
    private BlockPos lastDodgeTarget = null;
    private ProjectileEntity lastProjectile = null;
    private Vec3d preDodgePos = null;

    private Module blockFlyModule = null;
    private boolean blockFlyWasToggled = false;
    private int originalSlot = -1;

    @ListenEvent
    public void tick(TickMovementEvent event) {
        if (mc.player == null || mc.world == null) return;
        if (dodging) {
            dodgeTicks++;
            if (dodgeTicks > 5) {
                if (lastProjectile != null && lastProjectile.isAlive()) {
                    buildWallInDirection(mc.player, mc.world, lastProjectile);
                }
                dodging = false;
                dodgeTicks = 0;
                lastProjectile = null;
                preDodgePos = null;
            }
            return;
        }
        checkBlockFlySafeLanding();
        checkIncomingProjectiles(mc.player, mc.world);
    }

    private void checkBlockFlySafeLanding() {
        if (blockFlyModule != null && blockFlyWasToggled && lastDodgeTarget != null) {
            if (mc.player.getBlockPos().equals(lastDodgeTarget)) {
                blockFlyModule.toggleModule(false);
                blockFlyWasToggled = false;
                lastDodgeTarget = null;
            }
        }
    }

    public void checkIncomingProjectiles(PlayerEntity player, World world) {
        if (player == null || world == null) return;
        List<ProjectileEntity> projectiles = world.getEntitiesByClass(
                ProjectileEntity.class,
                player.getBoundingBox().expand(10),
                p -> p instanceof ArrowEntity || p instanceof FireballEntity || p instanceof net.minecraft.entity.projectile.thrown.SnowballEntity
        );
        for (ProjectileEntity proj : projectiles) {
            Vec3d projVel = proj.getVelocity();
            if (projVel.lengthSquared() == 0) continue;
            projVel = projVel.normalize();
            Vec3d toPlayer = player.getPos().subtract(proj.getPos()).normalize();
            double dot = projVel.dotProduct(toPlayer);
            if (dot > 0.9) {
                preDodgePos = player.getPos();
                lastProjectile = proj;
                Vec3d right = player.getRotationVec(1.0F).crossProduct(new Vec3d(0, 1, 0)).normalize();
                Vec3d dodgeDirection = Math.random() > 0.5 ? right : right.multiply(-1);
                double dodgeSpeed = 0.7;
                BlockPos targetPos = new BlockPos(
                        (int) Math.floor(player.getX() + dodgeDirection.x),
                        (int) Math.floor(player.getY()),
                        (int) Math.floor(player.getZ() + dodgeDirection.z)
                );
                lastDodgeTarget = targetPos;
                if (!world.getBlockState(targetPos.down()).isAir()) {
                    player.setVelocity(dodgeDirection.multiply(dodgeSpeed).add(0, 0.2, 0));
                } else {
                    if (hasAtLeastAirBelow(targetPos, 6)) {
                        blockFlyModule = Shinrai.INSTANCE.getModuleManager().getModuleByName("BlockFly");
                        if (blockFlyModule != null && !blockFlyModule.isEnabled()) {
                            blockFlyModule.toggleModule(true);
                            blockFlyWasToggled = true;
                        }
                    }
                    player.setVelocity(dodgeDirection.multiply(dodgeSpeed).add(0, 0.2, 0));
                }
                dodging = true;
                dodgeTicks = 0;
                break;
            }
        }
    }

    public void buildWallInDirection(PlayerEntity player, World world, ProjectileEntity proj) {
        if (player == null || world == null || proj == null) return;
        Entity owner = proj.getOwner();
        if (owner == null) return;

        int blockSlot = findBlockInHotbar();
        if (blockSlot == -1) return;
        originalSlot = mc.player.getInventory().getSelectedSlot();
        mc.player.getInventory().setSelectedSlot(blockSlot);

        Vec3d toOwner = owner.getPos().subtract(player.getPos()).normalize();
        Vec3d wallDirection = toOwner.crossProduct(new Vec3d(0, 1, 0)).normalize();

        BlockPos playerPos = player.getBlockPos();
        BlockPos basePos = playerPos.add((int)Math.round(toOwner.x), 0, (int)Math.round(toOwner.z));

        for (int y = 0; y < 2; y++) {
            BlockPos yPos = basePos.up(y);

            for (int i = -1; i <= 1; i++) {
                BlockPos wallPos = yPos.add((int) Math.round(wallDirection.x * i), 0, (int) Math.round(wallDirection.z * i));
                placeBlock(wallPos);
            }
        }
        mc.player.getInventory().setSelectedSlot(originalSlot);
    }

    private void placeBlock(BlockPos pos) {
        if (!mc.world.getBlockState(pos).isReplaceable()) return;
        for (Direction side : Direction.values()) {
            BlockPos neighbor = pos.offset(side);
            Direction sideOpposite = side.getOpposite();
            if (!mc.world.getBlockState(neighbor).isAir()) {
                BlockHitResult result = new BlockHitResult(
                        Vec3d.ofCenter(neighbor),
                        sideOpposite,
                        neighbor,
                        false
                );
                if (mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, result).isAccepted()) {
                    mc.player.swingHand(Hand.MAIN_HAND);
                    return;
                }
            }
        }
    }

    private int findBlockInHotbar() {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (stack.getItem() instanceof BlockItem) {
                return i;
            }
        }
        return -1;
    }

    private boolean hasAtLeastAirBelow(BlockPos pos, int minAir) {
        for (int i = 1; i <= minAir; i++) {
            BlockPos check = pos.down(i);
            if (!mc.world.getBlockState(check).isAir()) {
                return false;
            }
        }
        return true;
    }
}