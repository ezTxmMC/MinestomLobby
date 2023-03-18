package dev.eztxm.minestomlobby.command.impl;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;

public class OperatorCommand extends Command {

    public OperatorCommand() {
        super("operator", "op");
        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("You must been add an username");
        });
        var playerName = ArgumentType.String("player");
        addSyntax((sender, context) -> {
            Player player = MinecraftServer.getConnectionManager().getPlayer(playerName.toString());

        });
    }
}
