package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.TransactionScoped;
import java.util.List;

// !!!! РЕАЛИЗАЦИЯ ДАННОГО КЛАССА НЕ ВЫПОЛНЕНА !!!! //

public class HibernateConnection {
    private SessionFactory sessionFactory;
    private Transaction transaction;

    protected HibernateConnection(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public void saveObject(User object) {
        try (Session session = sessionFactory.getCurrentSession()) {

            Transaction t = session.beginTransaction();
            session.save(object);

            t.commit();
        } catch (Exception e) {
            System.out.println("Saving object failed: "+object.toString());
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
    // lazy implementation
    public void clearUsersTable()  {
        List<User> list = this.getUsers();

        try  (Session session = sessionFactory.getCurrentSession()) {

            //TODO: rewrite with proper table clearing
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
            // Criteria API
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
