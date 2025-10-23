package com.manifestors.shinrai.client.setting

import com.google.gson.annotations.Expose

open class Setting<T : Any>(
    @Expose val settingName: String? = null,
    @Expose var current: T
)
