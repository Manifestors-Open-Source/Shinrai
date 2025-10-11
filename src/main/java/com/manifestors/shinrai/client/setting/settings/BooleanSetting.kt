package com.manifestors.shinrai.client.setting.settings;

import com.manifestors.shinrai.client.setting.Setting;
import lombok.Getter;

@Getter
public class BooleanSetting extends Setting<Boolean> {

    private boolean enabled;

    public BooleanSetting(String name, boolean enabled) {
        super(name, enabled);
        this.enabled = enabled;
    }

    public void toggle() {
        toggle(!enabled);
    }

    public void toggle(boolean state) {
        enabled = state;
    }

}
