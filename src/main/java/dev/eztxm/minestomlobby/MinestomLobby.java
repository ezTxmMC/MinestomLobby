package dev.eztxm.minestomlobby;

import dev.eztxm.minestomlobby.command.Command;
import dev.eztxm.minestomlobby.config.ConfigManager;
import dev.eztxm.minestomlobby.listener.Listener;
import dev.eztxm.minestomlobby.sql.SQL;
import dev.eztxm.minestomlobby.terminal.MineTerminal;
import dev.eztxm.minestomlobby.world.WorldHelper;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.extras.bungee.BungeeCordProxy;
import net.minestom.server.extras.optifine.OptifineSupport;
import net.minestom.server.extras.velocity.VelocityProxy;
import net.minestom.server.instance.InstanceContainer;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MinestomLobby {

    public static void main(String[] args) {
        File worldsFolder = new File("worlds");
        if (!worldsFolder.exists()) worldsFolder.mkdir();
        ConfigManager configManager = new ConfigManager(null, "config.json");
        SQL.start();
        MinecraftServer minecraftServer = MinecraftServer.init();
        MinecraftServer.setTerminalEnabled(false);
        if ((boolean) configManager.getValue("Proxy")) {
            switch (configManager.getValue("Proxy-Type").toString().toLowerCase()) {
                case "bungeecord", "waterfall" -> BungeeCordProxy.enable();
                case "velocity" -> VelocityProxy.enable((String) configManager.getValueFromJSONObject("Velocity", "Secret"));
            }
        }
        MojangAuth.init();
        OptifineSupport.enable();
        new WorldHelper();
        WorldHelper.importWorld("world");
        InstanceContainer instanceContainer = WorldHelper.getWorld("world");
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        Listener.playerLogin(globalEventHandler, instanceContainer);
        Listener.playerSpawn(globalEventHandler);
        Command.activateCommands();
        MinecraftServer.setBrandName("Lobby");
        MinecraftServer.setChunkViewDistance((Integer) configManager.getValue("View-Distance"));
        MinecraftServer.setEntityViewDistance((Integer) configManager.getValue("Simulation-Distance"));
        minecraftServer.start((String) configManager.getValue("Server-Address"), (Integer) configManager.getValue("Port"));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("[info] [" + dateTimeFormatter.format(localDateTime)+ "] Loading Listeners...");
        System.out.println("[info] [" + dateTimeFormatter.format(localDateTime)+ "] Loaded Listeners");
        System.out.println("[info] [" + dateTimeFormatter.format(localDateTime)+ "] Set the View Distance to " + MinecraftServer.getChunkViewDistance());
        System.out.println("[info] [" + dateTimeFormatter.format(localDateTime)+ "] Set the Simulation Distance to " + MinecraftServer.getEntityViewDistance());
        System.out.println("[info] [" + dateTimeFormatter.format(localDateTime)+ "] Server started with brandname " + MinecraftServer.getBrandName());
        System.out.println("[info] [" + dateTimeFormatter.format(localDateTime)+ "] Server started on " + MinecraftServer.getServer().getAddress() + ":" + MinecraftServer.getServer().getPort());
        MineTerminal.start();
    }
}