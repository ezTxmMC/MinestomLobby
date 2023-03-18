package dev.eztxm.minestomlobby.world;

import net.minestom.server.coordinate.Point;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;

import java.util.Random;

public class WorldGeneration {

    public static void gen(Instance instance) {
        instance.setGenerator(unit -> {
            Random random = new Random();
            Point start = unit.absoluteStart();
            unit.modifier().fillHeight(-64, -60, Block.SNOW_BLOCK);
            if (start.y() > -64 || random.nextInt(6) != 0) {
                return;
            }
            unit.fork(setter -> {
                for (int x = 0; x < 3; x++) {
                    for (int y = 0; y < 19; y++) {
                        for (int z = 0; z < 3; z++) {
                            setter.setBlock(start.add(x, y, z), Block.SNOW_BLOCK);
                        }
                    }
                }
                setter.setBlock(start.add(1, 19, 1), Block.JACK_O_LANTERN);
            });
        });
    }
}
