package jm.task.core.jdbc.util;

import java.sql.SQLException;

//Singleton
public class Util {
    SQLConnection connection;
    private static Util instance = new Util();

    private Util() {
        //
    }

    public SQLConnection getSQLConnection() {
        if (connection == null) {
            connection = SQLConnection.getInstance();
        }
        return connection;
    }

    public static Util getInstance() {
        return instance;
    }

    public void configureConnection(String username, String psw, String link) {
        try {
            connection.setUsername(username);
            connection.setPassword(psw);
            connection.setUrl(link);
        } catch (Exception e) {
            //
        }
    }

}
