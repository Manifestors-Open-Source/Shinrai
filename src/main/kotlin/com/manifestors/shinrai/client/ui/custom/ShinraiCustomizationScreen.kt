package com.manifestors.shinrai.client.ui.custom

import com.google.common.reflect.TypeToken
import com.manifestors.shinrai.client.ui.custom.splash.SplashScreenStyle
import com.manifestors.shinrai.client.ui.title.ShinraiTitleScreen
import com.manifestors.shinrai.client.utils.LoggerInstance
import com.manifestors.shinrai.client.utils.MinecraftInstance
import com.manifestors.shinrai.client.utils.file.FileManager
import com.manifestors.shinrai.client.utils.file.FileManager.getObjectFromJson
import com.manifestors.shinrai.client.utils.file.FileManager.toJson
import com.manifestors.shinrai.client.utils.file.ShinraiAssets.getDynamicTextureId
import com.manifestors.shinrai.client.utils.file.ShinraiAssets.loadDynamicImage
import com.manifestors.shinrai.client.utils.rendering.BackgroundDrawer
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.gui.widget.CyclingButtonWidget
import net.minecraft.text.Text
import org.lwjgl.system.MemoryStack
import org.lwjgl.util.tinyfd.TinyFileDialogs
import java.io.File

object ShinraiCustomizationScreen : Screen(Text.of("Customization Screen")), MinecraftInstance, LoggerInstance {

    private val metadataProperties = arrayOf<String?>("customizations", "metadata.json")
    private const val SPLASH_LOGO_JSON = "selected_splash.json"
    private var selectedSplashMode = SplashScreenStyle.DEFAULT

    override fun init() {
        this.addDrawableChild(
            ButtonWidget.builder(
                Text.translatable("customization.gui.changebutton")
            ) {  this.selectAndChangeBackground() }
                .dimensions(this.width / 2 - 100, this.height / 4 + 30, 200, 20)
                .build()
        )

        this.addDrawableChild(
            ButtonWidget.builder(
                Text.translatable("customization.gui.resetbutton")
            ) {  this.resetBackground() }
                .dimensions(this.width / 2 - 100, this.height / 4 + 60, 200, 20)
                .build()
        )

        this.addDrawableChild(
            CyclingButtonWidget.builder<SplashScreenStyle> { Text.literal(it.displayName) }
                .values(SplashScreenStyle.entries)
                .initially(selectedSplashMode)
                .build(
                    this.width / 2 - 100,
                    this.height / 4 + 90,
                    200,
                    20,
                    Text.translatable("customization.gui.splashbutton")
                ) {
                        _, value ->
                    selectedSplashMode = value
                    val json = """
                       {
                         "selectedSplash": "$selectedSplashMode"
                       }
                   """.trimIndent()

                    FileManager.writeJsonToFile(json, metadataProperties[0]!!, SPLASH_LOGO_JSON)
                }
        )

        this.addDrawableChild(
            ButtonWidget.builder(
                Text.of("Back")
            ) {  mc.setScreen(ShinraiTitleScreen()) }
                .dimensions(this.width / 2 - 100, this.height / 4 + 120, 200, 20)
                .build()
        )

    }

    override fun render(context: DrawContext?, mouseX: Int, mouseY: Int, deltaTicks: Float) {
        super.render(context, mouseX, mouseY, deltaTicks)
    }

    private fun selectAndChangeBackground() {
        MemoryStack.stackPush().use { stack ->
            val filters = stack.mallocPointer(2)
            filters.put(stack.UTF8("*.png"))
            filters.flip()

            val imageFilePath = TinyFileDialogs.tinyfd_openFileDialog(
                "Select Background Image",
                "",
                filters,
                "PNG files",
                false
            )
            if (imageFilePath != null) {
                loadDynamicImage("custom_bg", imageFilePath)
                BackgroundDrawer.customBgId = getDynamicTextureId("custom_bg")
                BackgroundDrawer.isCustomBgActivated = true
                this.saveMetadata(true, imageFilePath)
                logger.info("Changed background and saved metadata.")
            } else {
                logger.info("Cancelled background image selection.")
            }
        }
    }

    private fun resetBackground() {
        BackgroundDrawer.isCustomBgActivated = false
        this.saveMetadata(false, "null")
        logger.info("Custom background reset and updated metadata.")
    }

    fun getSplashLogoFromJson(): SplashScreenStyle {
        val json = FileManager.getJsonFromFile(metadataProperties[0]!!, SPLASH_LOGO_JSON)
        if (json.isEmpty()) {
            logger.warn("Custom splash data not found, loading default splash logo.")
            return SplashScreenStyle.DEFAULT
        }

        val type = object : TypeToken<MutableMap<String?, Any?>?>() {}.type
        val mapData = getObjectFromJson<MutableMap<String?, String?>?>(json, type)

        runCatching {
            selectedSplashMode = SplashScreenStyle.valueOf(mapData!!["selectedSplash"].toString())
            logger.info("Custom splash logo loaded.")
            return selectedSplashMode
        }.onFailure {
            logger.error("Custom splash logo could not load, resetting to default.")
            return@onFailure
        }

        return SplashScreenStyle.DEFAULT
    }

    fun loadBackgroundFromJson() {
        val json = FileManager.getJsonFromFile(metadataProperties[0]!!, metadataProperties[1]!!)
        if (json.isEmpty()) {
            logger.warn("Custom background metadata is empty, loading default background.")
            return
        }

        val type = object : TypeToken<MutableMap<String?, Any?>?>() {}.type
        val mapData = getObjectFromJson<MutableMap<String?, Any?>?>(json, type)

        if (mapData!!["customBgActive"] as Boolean) {
            val imagePath = File(mapData["imagePath"].toString())
            if (imagePath.exists()) {
                loadDynamicImage("custom_bg", imagePath.absolutePath)
                BackgroundDrawer.isCustomBgActivated = true
                BackgroundDrawer.customBgId = getDynamicTextureId("custom_bg")
                logger.info("Custom background loaded successfully.")
            } else {
                logger.warn("Custom background path is invalid. Loading default background.")
            }
        } else {
            logger.info("Custom background is disabled, loading default background.")
        }
    }

    private fun saveMetadata(customBgActive: Boolean, imagePath: String?) {
        val dataMap = HashMap<String?, Any?>()
        dataMap["customBgActive"] = customBgActive
        dataMap["imagePath"] = imagePath
        val data = toJson(dataMap)
        FileManager.writeJsonToFile(data!!, metadataProperties[0]!!, metadataProperties[1]!!)
    }

    override fun close() {
        mc.setScreen(ShinraiTitleScreen())
    }
}
