package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class HibernateConnection {
    private SessionFactory sessionFactory;
    private Transaction transaction;

    protected HibernateConnection(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public void saveUser(User user) {
        try (Session session = sessionFactory.getCurrentSession()) {

            Transaction t = session.beginTransaction();
            session.save(user);

            t.commit();
        } catch (Exception e) {
            System.out.println("Saving object failed: "+user.toString());
            e.printStackTrace();
        }
    }
    public void deleteUserById(Long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction t = session.beginTransaction();
            User instance = new User();
            instance.setId(id);
            session.delete((Object) instance);
            t.commit();
        } catch (Exception e) {
            System.out.println("Failed deleting user by id "+id);
            e.printStackTrace();
        }
    }
    public void execute(String query)  {
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction t = session.beginTransaction();
            session.createSQLQuery(query);
            t.commit();
        } catch (Exception e) {
            System.out.println("Query failed: "+query);
            e.printStackTrace();
        }
    }

    public void clearUsersTable()  {
        List<User> list = this.getUsers();

        try  (Session session = sessionFactory.getCurrentSession()) {

            Transaction ts = session.beginTransaction();
            for (User u : list) {
                session.delete(u);
            }
            ts.commit();

        } catch (Exception e) {
            System.out.println("Clearing users table failed");
            e.printStackTrace();
        }
    }

    public List<User> getUsers() {
        List<User> list = null;

        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction t = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> rootEntry = cq.from(User.class);
            CriteriaQuery<User> all = cq.select(rootEntry);

            TypedQuery<User> allQuery = session.createQuery(all);

            list = allQuery.getResultList();
            t.commit();
        } catch (Exception e) {
            System.out.println("Getting users failed");
            e.printStackTrace();
        }
        return list;
    }
    public void dropUsersTable() {
        this.clearUsersTable();
        try (Session session = sessionFactory.getCurrentSession()) {
            Transaction t = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS Users;");
            t.commit();
        } catch (Exception e) {
            System.out.println("couldn't drop table");
            e.printStackTrace();
        }
    }


}
