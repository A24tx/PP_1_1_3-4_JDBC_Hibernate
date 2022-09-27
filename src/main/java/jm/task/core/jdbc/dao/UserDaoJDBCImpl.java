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

        try (Connection connection = Util.getNewConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.execute();
        } catch (Exception e) {
            System.out.println("Не получилось создать таблицу : ");
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS Users;";
        try (Connection connection = Util.getNewConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.execute();
        } catch (Exception e) {
            System.out.println("Не получилось удалить таблицу");
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String query = "INSERT INTO Users " +
                "VALUES(null,'" + name + "','" + lastName + "'," + age + ");";

        try (Connection connection = Util.getNewConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Не получилось сохранить пользователя " + query);
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM users WHERE id = " + String.valueOf(id) + ";";
        try (Connection connection = Util.getNewConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.execute();
        } catch (Exception e) {
            System.out.println("Не получилось удалить пользователя по ID");
            e.printStackTrace();
        }
    }

    public void cleanUsersTable() {
        String query = "DELETE FROM Users;";
        try (Connection connection = Util.getNewConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Не получилось очистить таблицу ");
            e.printStackTrace();
        }
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
}
