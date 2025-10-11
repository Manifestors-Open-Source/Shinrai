package com.manifestors.shinrai.client.setting.settings

import com.manifestors.shinrai.client.setting.Setting

class BooleanSetting(name: String?, var enabled: Boolean) : Setting<Boolean?>(
    name,
    enabled
) {

    fun toggle() { enabled = !enabled }

}
