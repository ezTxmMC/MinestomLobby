package dev.eztxm.minestomlobby.listener;

import dev.eztxm.minestomlobby.tablist.TablistGroup;
import dev.eztxm.minestomlobby.tablist.TablistTeam;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.instance.InstanceContainer;

import java.awt.*;

public class Listener {

    public static void playerLogin(GlobalEventHandler globalEventHandler, InstanceContainer instanceContainer) {
        globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(new Pos(0.5, 65, 0.5, 0, 0));
        });
    }

    public static void playerSpawn(GlobalEventHandler globalEventHandler) {
        globalEventHandler.addListener(PlayerSpawnEvent.class, event -> {
            TablistGroup tablistGroup = new TablistGroup();
            tablistGroup.addTeam(new TablistTeam("Admin", 0, "§4§lOwner §8| ", NamedTextColor.GRAY, player -> true));
            tablistGroup.calculateTeams();
            tablistGroup.setTablist();
        });
    }
}
