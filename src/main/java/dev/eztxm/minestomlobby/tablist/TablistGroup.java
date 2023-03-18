package dev.eztxm.minestomlobby.tablist;

import net.minestom.server.MinecraftServer;
import net.minestom.server.scoreboard.TeamManager;

import java.util.ArrayList;
import java.util.List;

public class TablistGroup {
    private final TeamManager teamManager = new TeamManager();
    private final List<TablistTeam> tablistTeams = new ArrayList<>();

    public void calculateTeams() {
        tablistTeams.forEach(tablistTeam -> tablistTeam.toTeam(teamManager));
    }

    public void setTablist() {
        MinecraftServer.getConnectionManager().getOnlinePlayers().forEach(player -> {
            tablistTeams.stream().filter(tablistTeam -> tablistTeam.getTabPredicate().test(player)).findFirst().ifPresent(tablistTeam -> {
                if (!tablistTeam.getTeam().getMembers().contains(player.getUsername())) {
                    tablistTeam.getTeam().addMember(player.getUsername());
                }
            });
        });
    }

    public void addTeam(TablistTeam team) {
        tablistTeams.add(team);
    }

    public void removeTeam(TablistTeam team) {
        tablistTeams.forEach(teams -> teams.getTeam().getPlayers().forEach(player -> teams.getTeam().removeMember(player.getUsername())));
    }
}
