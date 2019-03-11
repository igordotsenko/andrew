package com.gymfox.databases;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static com.gymfox.databases.Entity.setDatabase;

public class DBManagerImpl {
    private final static String CREATE_DATABASE  = "CREATE DATABASE ";
    private static Connection connection;

    private final String dbName;

    public DBManagerImpl(String dbName) {
        this.dbName = dbName;
    }

    /**
     * Считать файл statements.sql
     * Создать базу данных.
     * Создать таблицы базы данных.
     */

    public void importSQL(BufferedReader in) throws SQLException {

        Scanner s = new Scanner(in);
        s.useDelimiter("(;(\r)?\n)|(--\n)");

        Statement st = connection.createStatement();
        while (s.hasNext()) {
            String line = s.next();
            if (line.startsWith("/*!") && line.endsWith("*/")) {
                int i = line.indexOf(' ');
                line = line.substring(i + 1, line.length() - " */".length());
            }

            if (line.trim().length() > 0) {
                st.execute(line);
            }
        }
    }

    private void initDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/?",
                "postgres",
                "13121993");

        PreparedStatement statement = connection.prepareStatement(CREATE_DATABASE + dbName);
        statement.executeUpdate();

        setDatabase(connection);
        System.out.println(String.format("database %s was created", dbName));
    }

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(args[0]));
        DBManagerImpl dbManager = new DBManagerImpl(args[1]);
        dbManager.initDatabase();
        dbManager.importSQL(bufferedReader);
    }
}
