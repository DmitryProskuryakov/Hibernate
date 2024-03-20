package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Users(id INT KEY AUTO_INCREMENT," +
                    "name VARCHAR(255),lastName VARCHAR(255), age TINYINT)");
        } catch (SQLException s) {
            s.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS Users");
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (SessionFactory sessionFactory = Util.getSessionFactory()) {
            Session session = null;

            try {
                session = sessionFactory.getCurrentSession();
                User user = new User(name, lastName, age);
                session.beginTransaction();
                session.save(user);
                session.getTransaction().commit();
            } finally {
                session.close();
            }

        } catch (HibernateException h) {
            h.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (SessionFactory sessionFactory = Util.getSessionFactory()) {
            Session session = null;

            try {
                session = sessionFactory.getCurrentSession();
                session.beginTransaction();
                User user = session.get(User.class, id);
                session.delete(user);
                session.getTransaction().commit();
            } finally {
                session.close();
            }

        } catch (HibernateException h) {
            h.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (SessionFactory sessionFactory = Util.getSessionFactory()) {
            Session session = null;

            try {
                session = sessionFactory.getCurrentSession();
                session.beginTransaction();
                userList = session.createQuery("from User", User.class).getResultList();
                session.getTransaction().commit();
            } finally {
                session.close();
                sessionFactory.close();
            }

        } catch (HibernateException h) {
            h.printStackTrace();
        }

        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (SessionFactory sessionFactory = Util.getSessionFactory()) {
            Session session = null;

            try {
                session = sessionFactory.getCurrentSession();
                session.beginTransaction();
                session.createQuery("DELETE FROM User").executeUpdate();
                session.getTransaction().commit();
            } finally {
                session.close();
                sessionFactory.close();
            }

        } catch (HibernateException h) {
            h.printStackTrace();
        }
    }
}
