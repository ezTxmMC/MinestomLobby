package dev.eztxm.minestomlobby.operator;

import dev.eztxm.minestomlobby.MinestomLobby;
import dev.eztxm.sql.SQLiteConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Operator {
    private static final SQLiteConnection sqlConnection = MinestomLobby.getSqlConnection();

    public static void setOperator(UUID uuid, boolean bool) {
        if (bool) {
            sqlConnection.update("INSERT INTO operators(`uuid`) VALUES ('" + uuid.toString() + "')");
        } else {
            sqlConnection.update("DELETE FROM operators WHERE \"uuid\"='" + uuid.toString() + "')");
        }
    }

    public static boolean isOperator(UUID uuid) {
        try (ResultSet resultSet = sqlConnection.query("SELECT \"uuid\" FROM operators WHERE \"uuid\"='" + uuid.toString() + "'")) {
            if (resultSet.next()) {
                return resultSet.getString("uuid") != null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
