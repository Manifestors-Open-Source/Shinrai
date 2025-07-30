package com.manifestors.shinrai.client.setting.settings;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.setting.Setting;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChoiceSetting extends Setting<String> {

    private final List<String> choices = new ArrayList<>();
    @Getter
    private String defaultChoice;

    public ChoiceSetting(String name, String defaultChoice, String ... choices) {
        super(name, defaultChoice);
        this.defaultChoice = defaultChoice;
        if (!this.choices.contains(defaultChoice))
            this.choices.add(defaultChoice);
        this.choices.addAll(Arrays.asList(choices));
    }

    public String getChoice(String choiceName) {
        return choices.stream().filter(choice -> choice.equalsIgnoreCase(choiceName)).findAny().orElse("unknown");
    }

    public void setDefaultChoice(String choiceName) {
        if (this.choices.contains(choiceName))
            this.defaultChoice = choiceName;
        else
            Shinrai.logger.warn("Invalid choice: {}", choiceName);
    }

}