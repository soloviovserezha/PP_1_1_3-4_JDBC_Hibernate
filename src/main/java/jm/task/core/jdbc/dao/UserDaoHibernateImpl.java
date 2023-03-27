package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    private final Logger logger = Logger.getLogger(UserDaoHibernateImpl.class.getName());
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Transaction transaction;
        final String createUsersTable = "CREATE TABLE IF NOT EXISTS Users(" +
                "id BIGINT NOT NULL AUTO_INCREMENT, " +
                "name TINYTEXT NOT NULL, " +
                "last_name TINYTEXT NOT NULL, " +
                "age TINYINT NOT NULL, " +
                "PRIMARY KEY(id))";
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(createUsersTable).executeUpdate();
            logger.info(UserDaoHibernateImpl.class.getName() + ": table was created");
            transaction.commit();
        } catch (Exception e) {
            logger.log(Level.INFO, "failed, table wasn't created", e);
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction;
        final String dropUsersTable = "DROP TABLE IF EXISTS Users";
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(dropUsersTable).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            logger.log(Level.INFO, "fail drop - table not exists", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            logger.info(UserDaoJDBCImpl.class.getName() + ": user={" + name + "} was saved");
            transaction.commit();
        } catch (Exception e) {
            logger.log(Level.INFO, "failed, user wasn't saved", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            logger.info(UserDaoJDBCImpl.class.getName() + ": user={" + user.getName() + "} was removed");
            transaction.commit();
        } catch (Exception e) {
            logger.log(Level.INFO, "failed, user wasn't removed", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = null;
        Transaction transaction;
        final String getAllUsers = "FROM " + User.class.getSimpleName();
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createQuery(getAllUsers);
            userList = query.list();
            transaction.commit();
        } catch (Exception e) {
            logger.log(Level.INFO, "failed, list of users wasn't got", e);
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction;
        final String cleanUsersTable = "TRUNCATE Users";
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(cleanUsersTable);
            query.executeUpdate();
            logger.info(UserDaoJDBCImpl.class.getName() + ": table was cleaned");
            transaction.commit();
        } catch (Exception e) {
            logger.log(Level.INFO, "failed, table wasn't cleaned", e);
        }
    }
}
