package com.manifestors.shinrai.client.setting.settings;

import com.manifestors.shinrai.client.setting.Setting;
import lombok.Getter;

public class DoubleSetting extends Setting<Double> {

    @Getter
    private double current;
    @Getter
    private final double max;
    @Getter
    private final double min;
    private final double step;

    public DoubleSetting(String name, double current, double max, double min, double step) {
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
