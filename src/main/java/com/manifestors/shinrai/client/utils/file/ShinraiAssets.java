package com.manifestors.shinrai.client.utils.file;

import net.minecraft.util.Identifier;

public class ShinraiAssets {

    public static Identifier getBackgroundId(String bgName) {
        return Identifier.of("shinrai", "backgrounds/" + bgName + ".png");
    }

    public static Identifier getTextureId(String textureName) {
        return Identifier.of("shinrai", "textures/" + textureName + ".png");
    }

}
