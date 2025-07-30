package com.manifestors.shinrai.client.setting.settings;

import com.manifestors.shinrai.client.setting.Setting;
import lombok.Getter;

public class IntegerSetting extends Setting<Integer> {

    @Getter
    private int current;
    @Getter
    private final int max;
    @Getter
    private final int min;
    private final int step;

    public IntegerSetting(String name, int current, int max, int min, int step) {
        super(name, current);
        this.current = current;
        this.max = max;
        this.min = min;
        this.step = step;
    }

    public void incrementCurrent() {
        current += step;

        if (current >= max) {
            current = max;
        }
    }

    public void decrementCurrent() {
        current -= step;

        if (current <= min) {
            current = min;
        }
    }

}
