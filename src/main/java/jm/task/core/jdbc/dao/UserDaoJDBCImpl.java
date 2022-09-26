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
                        CREATE TABLE IF NOT EXISTS Users(
                            id mediumint AUTO_INCREMENT,
                            name varchar(255),
                            lastname varchar(255),
                            age tinyint(255),
                            KEY (id)
                        );
                        """;

        con.execute(query);
    }

    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS Users;";

        con.execute(query);
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO Users " +
                "VALUES(null,'"+ name + "','" + lastName + "'," + age + ");";

        con.executeUpdate(query);

    }

    public void removeUserById(long id) {
        String query = "DELETE FROM users WHERE id = " + String.valueOf(id)+ ";";

        con.execute(query);
    }

    public List<User> getAllUsers() {
        return con.fetchUsers();
    }

    public void cleanUsersTable() {
        con.executeUpdate("DELETE FROM Users;");
    }
}
