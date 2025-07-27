package com.manifestors.shinrai.file.settings;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public interface ISettings {

    void saveToJson(@NotNull File file);
    void readFromJson(@NotNull File file);

}
