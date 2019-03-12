package com.gymfox.databases;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static com.gymfox.databases.Entity.setDatabase;

public class DBManagerImpl {
    private static final String CREATE_QUERY  = "CREATE DATABASE \"%1$s\"";
    private static final String DROP_QUERY    = "DROP DATABASE \"%1$s\"";
    private static final int FIRST_INDEX_PARAMETER = 1;
    private static Connection connection;
    private String DEFAULT_DATABASE = "postgres";

    private final String dbName;

    public DBManagerImpl(String dbName) {
        this.dbName = dbName;
    }

    public void initDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        createDatabase();
        connectToDatabase(dbName);
        setDatabase(connection);
    }

    /**
     * if connection is not null - it should be closed;
     * if connection is closed - we will open it;
     */
    private void connectToDatabase(String databaseName) throws SQLException {
        if (connection != null) {
            connection.close();
        }
        connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/" + databaseName,
                "postgres",
                "13121993");
    }

    public void importSQL(File in) throws SQLException, FileNotFoundException {
        Scanner s = new Scanner(in);
        s.useDelimiter("(;(\r)?\n)|(--\n)");

        Statement statement = connection.createStatement();
        while (s.hasNext()) {
            String line = s.next();
            if (line.startsWith("/*!") && line.endsWith("*/")) {
                int i = line.indexOf(' ');
                line = line.substring(i + 1, line.length() - " */".length());
            }

            if (line.trim().length() > 0) {
                statement.execute(line);
            }
        }
    }

    public void createDatabase() throws SQLException {
        connectToDatabase(DEFAULT_DATABASE);
        createStatement(CREATE_QUERY, dbName);
        connection.close();
    }

    public void dropDatabase() throws SQLException {
        connectToDatabase(DEFAULT_DATABASE);
        createStatement(DROP_QUERY, dbName);
        connection.close();
    }

    void createStatement(String query, String databaseName) throws SQLException {
        connection.createStatement().executeUpdate(String.format(query, databaseName));
    }

    static String getPreparedStatement(String query, int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(FIRST_INDEX_PARAMETER, id);

        return preparedStatement.toString();
    }
}
