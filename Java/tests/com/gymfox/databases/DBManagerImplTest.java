package com.gymfox.databases;

import org.junit.BeforeClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.gymfox.databases.Entity.setDatabase;

public class DBManagerImplTest implements DBManager {
    private static Map<String, Object> fields = new HashMap<>();
    @BeforeClass
    public static void setUpConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");

        Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/orm", "postgres",
                "13121993");
        setDatabase(connection);
    }

    @Override
    public final Object getColumn(String name) {
        return "user_" + name;
    }
}
