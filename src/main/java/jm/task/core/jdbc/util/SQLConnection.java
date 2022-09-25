package jm.task.core.jdbc.util;

import java.sql.*;

// Singleton
public class SQLConnection {
    // параметры по умолчанию
    private String USERNAME = "root";
    private String PASSWORD = "root";
    private String URL = "jdbc:mysql://localhost:3306/mysql";
    private Connection connection;
    private static SQLConnection instance = new SQLConnection();

    protected static SQLConnection getInstance() {
        return instance;
    }

    private SQLConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        ResultSet results = null;

        try  {
            Statement statement = connection.createStatement();
            results = statement.executeQuery(query);
        } catch (Exception e) {
            throw new SQLException(e);
        }

        return results;
    }
    public boolean execute(String statement) throws SQLException {
        try {
            Statement s = connection.createStatement();
            return s.execute(statement);
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }
    public void executeUpdate(String statement) throws SQLException {
        try {
            Statement s = connection.createStatement();
            s.executeUpdate(statement);
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    // методы изменения параметров работы с DB

    protected void setUsername(String username) {
        USERNAME = username;
    }
    protected void setPassword(String psw) {
        PASSWORD = psw;
    }
    protected void setUrl(String url) {
        URL = url;
    }

    protected void newConnection() {
        try {
            connection.close();
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            //
        }
    }
    protected void closeConnection() {
        try {
            connection.close();
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            //
        }
    }

}
