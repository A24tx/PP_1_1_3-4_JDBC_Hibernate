package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {


    public void createUsersTable() {
        String query = """
                CREATE TABLE IF NOT EXISTS Users(
                    id mediumint AUTO_INCREMENT,
                    name varchar(255),
                    lastname varchar(255),
                    age tinyint(255),
                    KEY (id)
                );
                """;
        execute(query);
    }

    public void dropUsersTable() {
        execute("DROP TABLE IF EXISTS Users;");
    }

    public void saveUser(String name, String lastName, byte age) {
        executeUpdate("INSERT INTO Users " +
                "VALUES(null,'" + name + "','" + lastName + "'," + age + ");");
    }

    public void removeUserById(long id) {
        execute("DELETE FROM users WHERE id = " + String.valueOf(id) + ";");
    }

    public void cleanUsersTable() {
        executeUpdate("DELETE FROM Users;");
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();

        try (Connection connection = Util.getNewConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Users;");
            ResultSet results = ps.executeQuery();

            while (results.next()) {
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


    private void executeUpdate(String query) {
        try (Connection connection = Util.getNewConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Не получилось выполнить обновление: " + query);
            e.printStackTrace();
        }
    }

    private void execute(String query) {
        try (Connection connection = Util.getNewConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.execute();
        } catch (Exception e) {
            System.out.println("Не получилось выполнить : " + query);
            e.printStackTrace();
        }
    }
}
