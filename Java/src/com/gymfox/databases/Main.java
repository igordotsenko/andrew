package com.gymfox.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.gymfox.databases.Entity.setDatabase;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        initDatabase();
        User user = new User(5);
        user.setName("test");

        printAllUsers();
    }

    private static void printAllUsers() {
        for (User user : User.all()) {
            for ( Comment comment : user.getComments() ) {
                System.out.println(String.format("%s : %s - %s", user.getId(), user.getName(), comment.getText()));
            }
        }
    }

    private static void initDatabase() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");

        Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/orm", "postgres",
                    "13121993");
        setDatabase(connection);
    }
}
