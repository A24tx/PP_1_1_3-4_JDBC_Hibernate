package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Singleton
public class Util {
    private static Connection connection;
    private static String USERNAME = "root";
    private static String PASSWORD = "root";
    private static String URL = "jdbc:mysql://localhost:3306/mysql";

    static {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getNewConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            System.out.println("Failed to establish a new connection, using old one");
        }
        return connection;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void configureConnection(String username, String psw, String link) {
        USERNAME = username;
        PASSWORD = psw;
        URL = link;
    }

}
