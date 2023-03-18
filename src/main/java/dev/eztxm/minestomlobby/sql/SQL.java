package dev.eztxm.minestomlobby.sql;

import dev.eztxm.sql.SQLiteConnection;
import lombok.Getter;

import java.sql.SQLException;

public class SQL {
    @Getter
    private static SQLiteConnection sqlConnection;

    public static void start() {
        sqlConnection = new SQLiteConnection("data", "lobby");
    }

    public static void stop() {
        try {
            if (sqlConnection.getConnection() != null) {
                sqlConnection.getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
