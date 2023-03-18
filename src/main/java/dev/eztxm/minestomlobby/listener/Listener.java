package dev.eztxm.minestomlobby.listener;

import dev.eztxm.minestomlobby.tablist.TablistGroup;
import dev.eztxm.minestomlobby.tablist.TablistTeam;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.instance.Instance;

public class Listener {

    public static void playerLogin(GlobalEventHandler globalEventHandler, Instance instance) {
        globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(instance);
            player.setRespawnPoint(new Pos(0.5, -60, 0.5, 0, 0));
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
