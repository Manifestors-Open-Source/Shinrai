package com.manifestors.shinrai.client.module

import com.google.common.reflect.TypeToken
import com.manifestors.shinrai.client.Shinrai.logger
import com.manifestors.shinrai.client.module.modules.combat.*
import com.manifestors.shinrai.client.module.modules.extras.*
import com.manifestors.shinrai.client.module.modules.movement.*
import com.manifestors.shinrai.client.module.modules.player.*
import com.manifestors.shinrai.client.module.modules.visuals.*
import com.manifestors.shinrai.client.module.modules.`fun`.*
import com.manifestors.shinrai.client.setting.Setting
import com.manifestors.shinrai.client.setting.settings.BooleanSetting
import com.manifestors.shinrai.client.setting.settings.ChoiceSetting
import com.manifestors.shinrai.client.setting.settings.DoubleSetting
import com.manifestors.shinrai.client.setting.settings.IntegerSetting
import com.manifestors.shinrai.client.utils.file.FileManager
import com.manifestors.shinrai.client.utils.file.FileManager.getJsonFromFile
import com.manifestors.shinrai.client.utils.file.FileManager.getObjectFromJson
import com.manifestors.shinrai.client.utils.file.FileManager.toJsonOnlyExposedFields
import com.manifestors.shinrai.client.utils.file.FileManager.toJson
import java.util.ArrayList
import java.util.concurrent.CopyOnWriteArrayList

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
            HUD(),
            NoFOV,
            NoHurtCam,
            NoTorchAnymore(),

            // Extras
            NoPortalCooldown,

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
                        is DoubleSetting -> this.current = (current as Number).toDouble()
                        is IntegerSetting -> this.current = (current as Number).toInt()
                        is ChoiceSetting -> this.current = current as String
                    }
                }
            }
        }
    }
}
