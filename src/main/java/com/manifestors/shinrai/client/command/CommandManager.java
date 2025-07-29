package com.manifestors.shinrai.client.command;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.command.commands.BindCommand;
import com.manifestors.shinrai.client.command.commands.ToggleCommand;
import com.manifestors.shinrai.client.event.events.player.ChatMessageSendEvent;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CommandManager {

    private final CopyOnWriteArrayList<Command> commands = new CopyOnWriteArrayList<>();

    public static final String PREFIX = ".";

    public void registerCommands() {
        commands.add(new BindCommand());
        commands.add(new ToggleCommand());
    }

    public void processCommands(ChatMessageSendEvent event) {
        if (event.getMessage().startsWith(PREFIX)) {
            var found = false;
            for (Command command : commands) {
                var args = event.getMessage().split("\\s+");
                event.setCancelled(true);

                if (isValidCommand(args[0], command.getAllCommandNames())) {
                    found = true;
                    if (!command.onCommandExecuted(args))
                        command.sendUsage();
                    break;
                }
            }

            if (!found)
                Shinrai.INSTANCE.addChatMessage("Unknown command. If you forget any command, type .help for help.");
        }
    }

    private boolean isValidCommand(String context, List<String> commandNames) {
        return commandNames.stream().anyMatch(name -> context.startsWith(PREFIX + name));
    }

}
