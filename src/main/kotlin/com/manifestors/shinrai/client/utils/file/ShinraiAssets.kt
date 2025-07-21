package com.manifestors.shinrai.client.utils.file

import com.manifestors.shinrai.client.Shinrai
import net.minecraft.util.Identifier

object ShinraiAssets {

    @JvmStatic
    fun getIdFromTexturesFolder(fileName: String): Identifier {
        return Identifier.of(Shinrai.NAME.lowercase(), "textures/$fileName")
    }

    @JvmStatic
    fun getIdFromBackgroundsFolder(fileName: String): Identifier {
        return Identifier.of(Shinrai.NAME.lowercase(), "backgrounds/$fileName")
    }

}