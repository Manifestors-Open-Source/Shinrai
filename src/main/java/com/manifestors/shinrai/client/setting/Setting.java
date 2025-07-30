package com.manifestors.shinrai.client.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Setting<T> {

    private final String settingName;
    private final T defaultValue;

}
