package dev.eztxm.minestomlobby.command.impl;

import dev.eztxm.minestomlobby.operator.Operator;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;

public class UnOperatorCommand extends Command {

    public UnOperatorCommand() {
        super("unoperator", "unop");
        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("You must been add an username");
        });
        var playerName = ArgumentType.String("player");
        addSyntax((sender, context) -> {
            if (sender instanceof Player) return;
            Player player = MinecraftServer.getConnectionManager().getPlayer(playerName.toString());
            assert player != null;
            if (Operator.isOperator(player.getUuid())) {
                Operator.setOperator(player.getUuid(), false);
                sender.sendMessage(player.getName() + " are now no more an operator");
                player.sendMessage("You are now no more an operator");
            } else {
                sender.sendMessage(player.getName() + " are no operator");
            }
        });
    }
}
