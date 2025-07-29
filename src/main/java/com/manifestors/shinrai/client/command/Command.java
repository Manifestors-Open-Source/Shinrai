package com.manifestors.shinrai.client.command;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.utils.LoggerInstance;
import com.manifestors.shinrai.client.utils.MinecraftInstance;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public abstract class Command implements LoggerInstance, MinecraftInstance {

    private final String commandName;
    private final String commandDescription;
    private final String validParameters;
    private final String[] alternatives;

    public Command(String name, String description, String parameters, String ... aliases) {
        this.commandName = name;
        this.commandDescription = description;
        this.validParameters = parameters;
        this.alternatives = aliases;
    }

    public abstract boolean onCommandExecuted(String[] args);

    public void sendUsage() {
        var cmdName = alternatives.length > 0 ? CommandManager.PREFIX + "<" + String.join("/", getAllCommandNames()) + ">" : commandName;
        Shinrai.INSTANCE.addChatMessage("Usage: " + cmdName + " [" + validParameters + "]");
    }

    public List<String> getAllCommandNames() {
        List<String> names = new ArrayList<>();
        names.add(this.commandName);
        if (this.alternatives != null)
            names.addAll(Arrays.stream(this.alternatives).toList());

        return names;
    }
}
