package dev.eztxm.minestomlobby.terminal;

import net.minestom.server.MinecraftServer;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

public class MineTerminal {
    private static final String PREFIX = "Lobby » ";
    private static volatile LineReader lineReader;
    private static volatile Terminal terminal;
    private static volatile boolean running = false;

    public static void start() {
        final Thread thread = new Thread(null, () -> {
            try {
                terminal = TerminalBuilder
                        .builder()
                        .system(true)
                        .dumb(true)
                        .encoding("UTF-8")
                        .name("Lobby-Terminal")
                        .build();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            lineReader = LineReaderBuilder
                    .builder()
                    .terminal(terminal)
                    .build();
            running = true;
            while (running) {
                String command;
                try {
                    command = lineReader.readLine(PREFIX);
                    MinecraftServer.getCommandManager().execute(MinecraftServer.getCommandManager().getConsoleSender(), command);
                } catch (UserInterruptException e) {
                    System.exit(0);
                    return;
                } catch (EndOfFileException e) {
                    return;
                }
            }
        }, "Lobby-Terminal");
        thread.setDaemon(true);
        thread.start();
    }

    public static void stop() {
        running = false;
        if (terminal != null) {
            try {
                terminal.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            lineReader = null;
        }
    }
}
