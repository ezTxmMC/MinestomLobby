package dev.eztxm.minestomlobby.command.impl;

import dev.eztxm.minestomlobby.operator.Operator;
import dev.eztxm.minestomlobby.terminal.MineTerminal;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StopCommand extends Command {

    public StopCommand() {
        super("stop");
        setDefaultExecutor((sender, context) -> {
            if (sender instanceof Player player) {
                if (Operator.isOperator(player.getUuid())) {
                    stopping();
                } else {
                    player.sendMessage("You don't have permissions to do this");
                }
            } else {
                if (MinecraftServer.isStarted()) {
                    stopping();
                }
            }
        });
        MinecraftServer.getCommandManager().register(this);
    }

    private void stopping() {
        MineTerminal.stop();
        MinecraftServer.getConnectionManager().getOnlinePlayers().forEach(player -> player.kick("Server was stopped"));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("[info] [" + dateTimeFormatter.format(localDateTime)+ "]  Server is stopping...");
        Executors.newScheduledThreadPool(2).schedule(MinecraftServer::stopCleanly, 100, TimeUnit.MILLISECONDS);
    }
}
