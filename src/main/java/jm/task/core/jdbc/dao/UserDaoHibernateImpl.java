package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateConnection;
import jm.task.core.jdbc.util.HibernateUtil;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    private HibernateConnection connection;

    public UserDaoHibernateImpl() {
        this.connection = HibernateUtil.getHibernateConnection();
    }

    @Override
    public void createUsersTable() {

        connection.execute("CREATE TABLE Users;");

    }

    @Override
    public void dropUsersTable() {

        connection.dropUsersTable();

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User u = new User(name, lastName, age);

        connection.saveUser(u);
    }

    @Override
    public void removeUserById(long id) {
        connection.deleteUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = null;
        list = connection.getUsers();

        return list;
    }

    @Override
    public void cleanUsersTable() {

        connection.clearUsersTable();

    }
}
