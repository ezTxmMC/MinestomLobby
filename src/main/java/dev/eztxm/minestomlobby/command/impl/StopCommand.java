package dev.eztxm.minestomlobby.command.impl;

import dev.eztxm.minestomlobby.terminal.MineTerminal;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StopCommand extends Command {

    public StopCommand() {
        super("stop");
        setDefaultExecutor((sender, context) -> {
            if (MinecraftServer.isStarted()) {
                MineTerminal.stop();
                MinecraftServer.stopCleanly();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                LocalDateTime localDateTime = LocalDateTime.now();
                System.out.println("[info] [" + dateTimeFormatter.format(localDateTime)+ "]  Server is stopping...");
            }
        });
        MinecraftServer.getCommandManager().register(this);
    }
}
