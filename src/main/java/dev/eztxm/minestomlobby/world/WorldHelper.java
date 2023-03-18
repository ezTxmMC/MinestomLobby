package dev.eztxm.minestomlobby.world;

import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.AnvilLoader;
import net.minestom.server.instance.InstanceContainer;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class WorldHelper {
    private static final Map<String, InstanceContainer> worlds = new HashMap<>();

    public WorldHelper() {
        File folder = new File("worlds");
        if (!folder.exists()) folder.mkdir();
    }

    public static void importWorld(@NotNull String name) {
        InstanceContainer container = MinecraftServer.getInstanceManager().createInstanceContainer(new AnvilLoader("worlds/" + name));
        worlds.put(name, container);
    }

    public static InstanceContainer getWorld(String name) {
        return worlds.get(name);
    }
}
