package com.manifestors.shinrai.client.module.modules.combat

import com.manifestors.shinrai.client.Shinrai.moduleManager
import com.manifestors.shinrai.client.event.annotations.ListenEvent
import com.manifestors.shinrai.client.event.events.player.TickMovementEvent
import com.manifestors.shinrai.client.module.Module
import com.manifestors.shinrai.client.module.ModuleCategory
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.*
import net.minecraft.entity.projectile.thrown.SnowballEntity
import net.minecraft.item.BlockItem
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import kotlin.math.floor
import kotlin.math.round
import kotlin.random.Random

object AutoDodge : Module(
    name = "AutoDodge",
    description = "Automatically dodges arrows, fireballs, and snowballs.",
    category = ModuleCategory.COMBAT
) {
    private var dodging = false
    private var dodgeTicks = 0
    private var lastDodgeTarget: BlockPos? = null
    private var lastProjectile: ProjectileEntity? = null
    private var preDodgePos: Vec3d? = null

    private var blockFlyModule: Module? = null
    private var blockFlyWasToggled = false
    private var originalSlot = -1

    @ListenEvent
    fun onTick(event: TickMovementEvent) {
        val player = mc.player ?: return
        val world = mc.world ?: return

        if (dodging) {
            if (++dodgeTicks > 5) {
                lastProjectile?.takeIf { it.isAlive }?.let {
                    buildWallInDirection(player, world, it)
                }
                resetDodge()
            }
            return
        }

        checkBlockFlySafeLanding(player)
        checkIncomingProjectiles(player, world)
    }

    private fun resetDodge() {
        dodging = false
        dodgeTicks = 0
        lastProjectile = null
        preDodgePos = null
    }

    private fun checkBlockFlySafeLanding(player: PlayerEntity) {
        if (blockFlyModule != null && blockFlyWasToggled && lastDodgeTarget != null) {
            if (player.blockPos == lastDodgeTarget) {
                blockFlyModule?.toggleModule(false)
                blockFlyWasToggled = false
                lastDodgeTarget = null
            }
        }
    }

    private fun checkIncomingProjectiles(player: PlayerEntity, world: World) {
        val projectiles = world.getEntitiesByClass(
            ProjectileEntity::class.java,
            player.boundingBox.expand(10.0)
        ) { it is ArrowEntity || it is FireballEntity || it is SnowballEntity }

        for (proj in projectiles) {
            val projVel = proj.velocity.takeIf { it.lengthSquared() > 0 }?.normalize() ?: continue
            val toPlayer = player.pos.subtract(proj.pos).normalize()
            val dot = projVel.dotProduct(toPlayer)

            if (dot > 0.9) {
                preDodgePos = player.pos
                lastProjectile = proj

                val right = player.getRotationVec(1.0f).crossProduct(Vec3d(0.0, 1.0, 0.0)).normalize()
                val dodgeDir = if (Random.nextBoolean()) right else right.multiply(-1.0)
                val dodgeSpeed = 0.7
                val targetPos = BlockPos(
                    floor(player.x + dodgeDir.x).toInt(),
                    floor(player.y).toInt(),
                    floor(player.z + dodgeDir.z).toInt()
                )

                lastDodgeTarget = targetPos

                if (!world.getBlockState(targetPos.down()).isAir) {
                    player.velocity = dodgeDir.multiply(dodgeSpeed).add(0.0, 0.2, 0.0)
                } else {
                    if (hasAtLeastAirBelow(world, targetPos)) {
                        blockFlyModule = moduleManager.getModuleByName("BlockFly")
                        if (blockFlyModule?.enabled == false) {
                            blockFlyModule?.toggleModule(true)
                            blockFlyWasToggled = true
                        }
                    }
                    player.velocity = dodgeDir.multiply(dodgeSpeed).add(0.0, 0.2, 0.0)
                }

                dodging = true
                dodgeTicks = 0
                break
            }
        }
    }

    private fun buildWallInDirection(player: PlayerEntity, world: World, proj: ProjectileEntity) {
        val owner = proj.owner ?: return

        val blockSlot = findBlockInHotbar() ?: return
        val inv = player.inventory

        originalSlot = inv.selectedSlot
        inv.selectedSlot = blockSlot

        val toOwner = owner.pos.subtract(player.pos).normalize()
        val wallDir = toOwner.crossProduct(Vec3d(0.0, 1.0, 0.0)).normalize()
        val basePos = player.blockPos.add(round(toOwner.x).toInt(), 0, round(toOwner.z).toInt())

        for (y in 0..1) {
            val yPos = basePos.up(y)
            for (i in -1..1) {
                val wallPos = yPos.add(
                    round(wallDir.x * i).toInt(),
                    0,
                    round(wallDir.z * i).toInt()
                )
                placeBlock(world, wallPos)
            }
        }

        inv.selectedSlot = originalSlot
    }

    private fun placeBlock(world: World, pos: BlockPos) {
        if (!world.getBlockState(pos).isReplaceable) return

        Direction.entries.forEach { side ->
            val neighbor = pos.offset(side)
            val sideOpposite = side.opposite
            if (!world.getBlockState(neighbor).isAir) {
                val result = BlockHitResult(Vec3d.ofCenter(neighbor), sideOpposite, neighbor, false)
                if (mc.interactionManager?.interactBlock(mc.player, Hand.MAIN_HAND, result)?.isAccepted == true) {
                    mc.player?.swingHand(Hand.MAIN_HAND)
                    return
                }
            }
        }
    }

    private fun findBlockInHotbar(): Int? {
        val inv = mc.player?.inventory ?: return null
        return (0..8).firstOrNull { inv.getStack(it).item is BlockItem }
    }

    private fun hasAtLeastAirBelow(world: World, pos: BlockPos): Boolean {
        return (1..6).none { !world.getBlockState(pos.down(it)).isAir }
    }
}
