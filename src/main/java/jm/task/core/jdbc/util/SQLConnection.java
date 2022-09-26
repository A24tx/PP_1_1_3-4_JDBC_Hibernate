package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Singleton
public class SQLConnection {
    // параметры по умолчанию
    private String USERNAME = "root";
    private String PASSWORD = "root";
    private String URL = "jdbc:mysql://localhost:3306/mysql";
    private static SQLConnection instance = new SQLConnection();

    protected static SQLConnection getInstance() {
        return instance;
    }

    private SQLConnection() {
        //
    }

    public List<User> fetchUsers()  {
        List<User> list = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Users;");
            ResultSet results = ps.executeQuery();

            while(results.next()) {
                User u = new User(results.getString("name"), results.getString("lastname"), results.getByte("age"));
                u.setId(results.getLong("id"));
                list.add(u);
            }
        } catch (Exception e) {
            System.out.println("Не получилось получить пользователей из базы данных");
            e.printStackTrace();
        }

        return list;
    }
    public void execute(String statement) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.execute(statement);
        } catch (Exception e) {
            System.out.println("Не получилось выполнить: "+statement);
            e.printStackTrace();
        }
    }
    public void executeUpdate(String statement) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement ps = connection.prepareStatement(statement);
            ps.executeUpdate(statement);
        } catch (Exception e) {
            System.out.println("Не получилось выполнить обновление: "+statement);
            e.printStackTrace();
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


}
