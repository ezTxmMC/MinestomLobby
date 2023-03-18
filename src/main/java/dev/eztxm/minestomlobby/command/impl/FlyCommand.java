package dev.eztxm.minestomlobby.command.impl;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;

public class FlyCommand extends Command {

    public FlyCommand() {
        super("fly");
        setDefaultExecutor((sender, context) -> {
            if (!(sender instanceof Player player)) return;
            if (player.isAllowFlying()) {
                player.setAllowFlying(false);
                player.setFlying(false);
                player.sendMessage("Flying disabled");
            } else {
                player.setAllowFlying(true);
                player.setFlying(false);
                player.sendMessage("Flying enabled");
            }
        });
        var playerName = ArgumentType.String("player");
        if (playerName.toString() == null) return;
        addSyntax((sender, context) -> {
            Player player = MinecraftServer.getConnectionManager().getPlayer(playerName.toString());
            if (player == null) sender.sendMessage("This player is not online");
                else{
                if (player.isAllowFlying()) {
                    player.setAllowFlying(false);
                    player.setFlying(false);
                    player.sendMessage("Flying disabled");
                } else {
                    player.setAllowFlying(true);
                    player.setFlying(false);
                    player.sendMessage("Flying enabled");
                }
            }
        });
        MinecraftServer.getCommandManager().register(this);
    }
}
