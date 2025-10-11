package com.manifestors.shinrai.client.command.commands;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.command.Command;
import com.manifestors.shinrai.client.utils.input.GLFWUtil;

public class BindCommand extends Command {

    public BindCommand() {
        super("bind", "Change our reset modules keybind.", "moduleName, key", "b");
    }

    @Override
    public boolean onCommandExecuted(String[] args) {
        if (args.length < 3)
            return false;

        var moduleName = args[1];
        var keyInput = args[2];

        var module = Shinrai.INSTANCE.getModuleManager().getModuleByName(moduleName);

        if (module != null) {
            if (keyInput.equalsIgnoreCase("none")) {
                module.setKeyCode(0);
                Shinrai.INSTANCE.addChatMessage("Unbound module " + module.getName() + ".");
            } else {
                var keyName = keyInput.toUpperCase();
                var key = GLFWUtil.getKeyIndex(keyName);
                if (key != -1) {
                    module.setKeyCode(key);
                    Shinrai.INSTANCE.addChatMessage(module.getName() + " bound to " + keyName + ".");
                } else
                    Shinrai.INSTANCE.addChatMessage("Invalid key " + keyInput + ".");
            }
        } else {
            sendNotFound("Module '" + moduleName + "'");
        }

        return true;
    }
}
