package jm.task.core.jdbc.util;

import jm.task.core.jdbc.service.SQLConnection;

import java.sql.SQLException;

public class Util {
    public static SQLConnection configureConnection(SQLConnection con, String username, String psw, String link) {
        try {
            con.setUsername(username);
            con.setPassword(psw);
            con.setUrl(link);
            con.newConnection();
            return con;
        } catch (SQLException e) {
            return con;
        }
    }
}
