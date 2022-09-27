package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public void createUsersTable() {

        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            //! SQLQuery устарел
            session.createSQLQuery("CREATE TABLE Users");
            session.flush();
        } catch (Exception e) {
            System.out.println("Не удалось создать таблицу Users");
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        cleanUsersTable();

        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            //! SQLQuery устарел
            session.createSQLQuery("DROP TABLE IF EXISTS Users");
            session.flush();
        } catch (Exception e) {
            System.out.println("Не удалось удалить таблицу Users");
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User u = new User(name, lastName, age);

        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.save(u);
            session.flush();
        } catch (Exception e) {
            System.out.println("Не удалось добавить пользователя с именем " + name);
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            User u = new User();
            u.setId(id);
            session.beginTransaction();
            session.remove(u);
        } catch (Exception e) {
            System.out.println("Не получилось удалить Пользователя по Id");
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> results = null;

        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cr = cb.createQuery(User.class);
            Root<User> root = cr.from(User.class);
            cr.select(root);

            Query<User> query = session.createQuery(cr);
            results = query.getResultList();

            session.flush();
        } catch (Exception e) {
            System.out.println("Не получилось достать всех пользователей");
            e.printStackTrace();
        }

        return results;
    }

    @Override
    public void cleanUsersTable() {
        List<User> list = getAllUsers();

        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction t = session.beginTransaction();
            for (User u : list) {
                session.remove(u);
            }
            session.flush();
        } catch (Exception e) {
            System.out.println("Не удалось очистить таблицу Users");
            e.printStackTrace();
        }

    }
}
