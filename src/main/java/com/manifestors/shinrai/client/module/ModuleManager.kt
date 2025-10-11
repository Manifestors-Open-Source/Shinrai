package com.manifestors.shinrai.client.module

import com.google.common.reflect.TypeToken
import com.manifestors.shinrai.client.Shinrai
import com.manifestors.shinrai.client.module.modules.combat.*
import com.manifestors.shinrai.client.module.modules.extras.*
import com.manifestors.shinrai.client.module.modules.movement.*
import com.manifestors.shinrai.client.module.modules.player.*
import com.manifestors.shinrai.client.module.modules.visuals.*
import com.manifestors.shinrai.client.module.modules.`fun`.*
import com.manifestors.shinrai.client.utils.file.FileManager
import com.manifestors.shinrai.client.utils.file.FileManager.getJsonFromFile
import com.manifestors.shinrai.client.utils.file.FileManager.getObjectFromJson
import com.manifestors.shinrai.client.utils.file.FileManager.toJsonOnlyExposedFields
import java.util.concurrent.CopyOnWriteArrayList

object ModuleManager {

    val modules = CopyOnWriteArrayList<Module>()

    fun registerModules() {
        Shinrai.logger.info("Loading modules...")

        modules += listOf(
            // Combat
            AutoDodge,
            AutoWeapon,
            BackToOldPVP,
            KillAura(),
            Velocity(),

            // Movement
            TensionedFluid(),
            NoJumpDelay,
            NoSlow,
            Speed(),
            Sprint(),
            SilkFall,

            // Player
            AutoEat(),
            AutoTotem(),
            BlockFly(),
            ChestStealer(),

            // Visuals
            HUD(),
            NoFOV,
            NoHurtCam,
            NoTorchAnymore(),

            // Extras
            NoPortalCooldown,

            // Fun
            NoGravity()
        )

        Shinrai.logger.info("Loaded ${modules.size} modules.")
    }

    val enabledModules: List<Module>
        get() = modules.filter { it.enabled }

    fun getModuleByName(moduleName: String): Module? {
        return modules.firstOrNull { module ->
            module.name.equals(moduleName, ignoreCase = true) ||
                    module.alternativeNames.any { it.equals(moduleName, ignoreCase = true) }
        }
    }

    fun saveModulesJson() {
        val json = toJsonOnlyExposedFields(modules)
        if (json != null) {
            FileManager.writeJsonToFile(json, "settings", "modules.shinrai")
        } else {
            Shinrai.logger.warn("Failed to serialize modules to JSON.")
        }
    }

    fun loadModulesFromJson() {
        val type = object : TypeToken<MutableList<MutableMap<String, Any>>>() {}.type
        val json = getJsonFromFile("settings", "modules.shinrai")
        if (json.isEmpty()) return

        val list = getObjectFromJson<MutableList<MutableMap<String, Any>>>(json, type) ?: return

        list.forEach { moduleMap ->
            val name = moduleMap["name"] as? String ?: return@forEach
            val module = getModuleByName(name) ?: return@forEach

            (moduleMap["keyCode"] as? Double)?.toInt()?.let { module.keyCode = it }
            (moduleMap["enabled"] as? Boolean)?.let { module.toggleModule(it) }
        }
    }
}
