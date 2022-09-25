package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.SQLConnection;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private SQLConnection con;
    public UserDaoJDBCImpl() {
        con = Util.getInstance().getSQLConnection();
    }

    public void createUsersTable() {
        String query = """
                        CREATE TABLE IF NOT EXISTS users(
                            id mediumint AUTO_INCREMENT,
                            name varchar(255),
                            lastname varchar(255),
                            age tinyint(255),
                            KEY (id)
                        );
                        """;
        try {
            con.execute(query);
            System.out.println("Таблица users создана");
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String query = "DROP TABLE users;";

        try {
            con.execute(query);
            System.out.println("Таблица users удалена");
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO users " +
                "VALUES(null,'"+ name + "','" + lastName + "'," + age + ");";
        try {
            con.executeUpdate(query);
            System.out.println("User с именем ".concat(name).concat(" добавлен в базу данных"));
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM users WHERE id = " + String.valueOf(id)+ ";";
        try {
            con.execute(query);
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try {
            ResultSet results = con.executeQuery("SELECT * FROM users;");

            while (results.next()) {
                String name = results.getString("name");
                String lastname = results.getString("lastname");
                byte age = (byte) results.getInt("age");
                Long id = results.getLong("id");

                User u = new User(name, lastname, age);
                u.setId(id);
                users.add(u);

                System.out.println("DB: user - ".concat(u.toString()));
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }

        return users;
    }

    public void cleanUsersTable() {
        try {
            con.executeUpdate("DELETE FROM users;");
            System.out.println("Таблица users очищена");
        } catch(Exception e ) {
            // e.printStackTrace();
        }
    }
}
