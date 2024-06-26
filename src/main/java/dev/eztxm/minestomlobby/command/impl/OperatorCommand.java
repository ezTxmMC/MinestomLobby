package dev.eztxm.minestomlobby.command.impl;

import dev.eztxm.minestomlobby.operator.Operator;
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
            if (sender instanceof Player) return;
            Player player = MinecraftServer.getConnectionManager().getPlayer(playerName.toString());
            assert player != null;
            if (!Operator.isOperator(player.getUuid())) {
                Operator.setOperator(player.getUuid(), true);
                sender.sendMessage(player.getName() + " are now an operator");
                player.sendMessage("You are now an operator");
            } else {
                sender.sendMessage(player.getName() + " are an operator");
            }
        });
    }
}
