package com.manifestors.shinrai.client.command.commands;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.command.Command;

public class ToggleCommand extends Command {

    public ToggleCommand() {
        super("toggle", "Toggles a module by name.", "module", "t");
    }

    @Override
    public boolean onCommandExecuted(String[] args) {
        if (args.length < 2)
            return false;
        var moduleName = args[1];
        var module = Shinrai.INSTANCE.getModuleManager().getModuleByName(moduleName);

        if (module != null) {
            module.toggleModule();
            Shinrai.INSTANCE.addChatMessage(module.getName() + " has been " + (module.isEnabled() ? "enabled" : "disabled") + ".");
            return true;
        } else
            Shinrai.INSTANCE.addChatMessage("Module not found.");

        return false;
    }
}
