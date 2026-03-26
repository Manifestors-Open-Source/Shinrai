/*
 *
 * Copyright © 2026 Manifestors Open Source
 * License: GPL-3.0
 * All code in this project is the property of the Manifestors Open Source team
 * and its contributors. If you use this code in any project, please provide proper attribution
 * and release your project under the GPL-3.0 license as well.
 * For more details, see: https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 */

package com.manifestors.shinrai.client.features.module

import com.google.common.reflect.TypeToken
import com.manifestors.shinrai.client.Shinrai.logger
import com.manifestors.shinrai.client.features.module.modules.combat.AutoDodge
import com.manifestors.shinrai.client.features.module.modules.combat.AutoWeapon
import com.manifestors.shinrai.client.features.module.modules.combat.BackToOldPVP
import com.manifestors.shinrai.client.features.module.modules.combat.KillAura
import com.manifestors.shinrai.client.features.module.modules.combat.Velocity
import com.manifestors.shinrai.client.features.module.modules.extras.NoPortalCooldown
import com.manifestors.shinrai.client.features.module.modules.`fun`.NoGravity
import com.manifestors.shinrai.client.features.module.modules.movement.NoJumpDelay
import com.manifestors.shinrai.client.features.module.modules.movement.NoSlow
import com.manifestors.shinrai.client.features.module.modules.movement.SilkFall
import com.manifestors.shinrai.client.features.module.modules.movement.Speed
import com.manifestors.shinrai.client.features.module.modules.movement.Sprint
import com.manifestors.shinrai.client.features.module.modules.movement.TensionedFluid
import com.manifestors.shinrai.client.features.module.modules.player.AutoEat
import com.manifestors.shinrai.client.features.module.modules.player.AutoTotem
import com.manifestors.shinrai.client.features.module.modules.player.BlockFly
import com.manifestors.shinrai.client.features.module.modules.player.ChestStealer
import com.manifestors.shinrai.client.features.module.modules.visuals.ESP
import com.manifestors.shinrai.client.features.module.modules.visuals.HUD
import com.manifestors.shinrai.client.features.module.modules.visuals.NoFOV
import com.manifestors.shinrai.client.features.module.modules.visuals.NoHurtCam
import com.manifestors.shinrai.client.features.module.modules.visuals.NoTorchAnymore
import com.manifestors.shinrai.client.features.module.modules.visuals.ClickGUI
import com.manifestors.shinrai.client.setting.Setting
import com.manifestors.shinrai.client.setting.settings.BooleanSetting
import com.manifestors.shinrai.client.setting.settings.ChoiceSetting
import com.manifestors.shinrai.client.setting.settings.DoubleSetting
import com.manifestors.shinrai.client.setting.settings.IntegerSetting
import com.manifestors.shinrai.client.file.FileManager
import com.manifestors.shinrai.client.file.FileManager.getJsonFromFile
import com.manifestors.shinrai.client.file.FileManager.getObjectFromJson
import com.manifestors.shinrai.client.file.FileManager.toJsonOnlyExposedFields
import com.manifestors.shinrai.client.file.FileManager.toJson
import java.util.ArrayList
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.collections.plusAssign

object ModuleManager {

    val modules = CopyOnWriteArrayList<Module>()

    fun registerModules() {
        logger.info("Loading modules...")

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
            ESP,
            HUD(),
            NoFOV,
            NoHurtCam,
            NoTorchAnymore(),

            // Extras
            NoPortalCooldown,
            ClickGUI(),

            // Fun
            NoGravity()
        )

        registerModuleSettings()
        logger.info("Loaded ${modules.size} modules.")
    }

    private fun registerModuleSettings() {
        logger.info("Registering module settings...")
        modules.forEach { module ->
            module::class.java.declaredFields
                .filter { Setting::class.java.isAssignableFrom(it.type) }
                .forEach { field ->
                    field.isAccessible = true
                    module.settings.add(field.get(module) as Setting<*>)
                }
        }
        logger.info("Registered module settings.")
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
            FileManager.writeJsonToFile(json, "settings", "modules.json")
        } else {
            logger.warn("Failed to serialize modules to JSON.")
        }
    }

    fun loadModulesFromJson() {
        val type = object : TypeToken<MutableList<MutableMap<String, Any>>>() {}.type
        val json = getJsonFromFile("settings", "modules.json")
        if (json.isEmpty()) return

        val list = getObjectFromJson<MutableList<MutableMap<String, Any>>>(json, type) ?: return

        list.forEach { moduleMap ->
            val name = moduleMap["name"] as? String ?: return@forEach
            val module = getModuleByName(name) ?: return@forEach

            (moduleMap["keyCode"] as? Double)?.toInt()?.let { module.keyCode = it }
            (moduleMap["enabled"] as? Boolean)?.let { module.toggleModule(it) }

            val settingsFromMap = moduleMap["settings"] as? ArrayList<*>? ?: return@forEach
            val settingsJson = toJson(settingsFromMap) ?: return@forEach
            val settings = getObjectFromJson<MutableList<MutableMap<String, Any>>>(settingsJson, type) ?: return@forEach

            settings.forEach { settingsMap ->
                val settingName = settingsMap["settingName"] as? String? ?: return@forEach
                val current = settingsMap["current"] ?: return@forEach

                module.settings.find { it.settingName == settingName }?.apply {
                    when (this) {
                        is BooleanSetting -> this.current = current as Boolean
                        is DoubleSetting -> this.currentValue = (current as Number).toDouble()
                        is IntegerSetting -> this.currentValue = (current as Number).toInt()
                        is ChoiceSetting -> this.currentChoice = current as String
                    }
                }
            }
        }
    }
}
