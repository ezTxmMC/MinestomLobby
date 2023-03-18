package dev.eztxm.minestomlobby.command;

import dev.eztxm.minestomlobby.command.impl.FlyCommand;
import dev.eztxm.minestomlobby.command.impl.StopCommand;

public class Command {

    public static void activateCommands() {
        new FlyCommand();
        new StopCommand();
    }
}
