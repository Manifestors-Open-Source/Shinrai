package com.manifestors.shinrai.client.ui.custom;

import com.google.common.reflect.TypeToken;
import com.manifestors.shinrai.client.ui.title.ShinraiTitleScreen;
import com.manifestors.shinrai.client.utils.LoggerInstance;
import com.manifestors.shinrai.client.utils.MinecraftInstance;
import com.manifestors.shinrai.client.utils.file.FileManager;
import com.manifestors.shinrai.client.utils.file.ShinraiAssets;
import com.manifestors.shinrai.client.utils.rendering.BackgroundDrawer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ShinraiCustomizationScreen extends Screen implements MinecraftInstance, LoggerInstance {

    private final String[] metadataProperties = {"custom_backgrounds", "metadata.json"};

    public ShinraiCustomizationScreen() {
        super(Text.of("Customization Screen"));
    }

    @Override
    protected void init() {
        this.addDrawableChild(
                ButtonWidget.builder(Text.translatable("customization.gui.changebutton"), (button) ->
                                this.selectAndChangeBackground())
                        .dimensions(this.width / 2 - 100, this.height / 4 + 30, 200, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(Text.translatable("customization.gui.resetbutton"), (button) ->
                                this.resetBackground())
                        .dimensions(this.width / 2 - 100, this.height / 4 + 60, 200, 20)
                        .build()
        );

        this.addDrawableChild(
                ButtonWidget.builder(Text.of("Back"), (button) ->
                                mc.setScreen(new ShinraiTitleScreen()))
                        .dimensions(this.width / 2 - 100, this.height / 4 + 90, 200, 20)
                        .build()
        );
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);
    }

    private void selectAndChangeBackground() {
        try (MemoryStack stack = MemoryStack.stackPush()) {

            PointerBuffer filters = stack.mallocPointer(2);

            filters.put(stack.UTF8("*.png"));
            filters.flip();

            var imageFilePath = TinyFileDialogs.tinyfd_openFileDialog(
                    "Select Background Image",
                    "",
                    filters,
                    "PNG files",
                    false
            );

            if (imageFilePath != null) {
                ShinraiAssets.loadDynamicImage("custom_bg", imageFilePath);
                BackgroundDrawer.customBgId = ShinraiAssets.getDynamicTextureId("custom_bg");
                BackgroundDrawer.isCustomBgActivated = true;
                this.saveMetadata(true, imageFilePath);
                logger.info("Changed background and saved metadata.");
            } else {
                logger.info("Cancelled background image selection.");
            }

        }
    }

    private void resetBackground() {
        BackgroundDrawer.isCustomBgActivated = false;
        this.saveMetadata(false, "null");
        logger.info("Custom background reset and updated metadata.");

    }

    public void loadBackgroundFromJson() {
        String json = FileManager.getJsonFromFile(metadataProperties[0], metadataProperties[1]);
        if (json.isEmpty()) {
            logger.warn("Custom background metadata is empty, loading default background.");
            return;
        }

        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> mapData = FileManager.getObjectFromJson(json, type);

        if ((boolean) mapData.get("customBgActive")) {
            var imagePath = new File((String) mapData.get("imagePath"));
            if (imagePath.exists()) {
                ShinraiAssets.loadDynamicImage("custom_bg", imagePath.getAbsolutePath());
                BackgroundDrawer.isCustomBgActivated = true;
                BackgroundDrawer.customBgId = ShinraiAssets.getDynamicTextureId("custom_bg");
                logger.info("Custom background loaded successfully.");
            } else {
                logger.warn("Custom background path is invalid. Loading default background.");
            }
        } else {
            logger.info("Custom background is disabled, loading default background.");
        }

    }

    private void saveMetadata(boolean customBgActive, String imagePath) {
        var dataMap = new HashMap<String, Object>();
        dataMap.put("customBgActive", customBgActive);
        dataMap.put("imagePath", imagePath);
        var data = FileManager.toJson(dataMap);
        FileManager.writeJsonToFile(data, metadataProperties[0], metadataProperties[1]);
    }

}
