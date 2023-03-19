package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {

    private final Logger logger = Logger.getLogger(UserDao.class.getName());
    private List<User> list = new ArrayList<>();
    private final Util util = new Util();
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Statement statement = util.getConnection().createStatement()) {
            dropUsersTable();
            statement.execute("create table Users(" +
                    "id BIGINT not null AUTO_INCREMENT, " +
                    "name TINYTEXT NOT NULL, " +
                    "last_name TINYTEXT NOT NULL, " +
                    "age TINYINT NOT NULL, " +
                    "PRIMARY KEY(id))");
            logger.info(UserDao.class.getName() + ": create table");
        } catch (SQLException e) {
            logger.info(UserDao.class.getName() + ": failed, table wasn't created");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = util.getConnection().createStatement()) {
            statement.execute("drop table if exists Users");
            logger.info(UserDaoJDBCImpl.class.getName() + ": drop table");
        } catch (SQLException e) {
            logger.info(UserDaoJDBCImpl.class.getName() + ": fail drop - table not exists");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate("insert into Users (name, last_name, age) VALUES ('" + name + "', '" + lastName + "', '" + age + "')");
            System.out.println("User с именем - " + name + " добавлен в базу данных");
            logger.info(UserDaoJDBCImpl.class.getName() + ": user was saved");
        } catch (SQLException e) {
            logger.info(UserDaoJDBCImpl.class.getName() + ": failed, user wasn't saved");
        }
    }

    public void removeUserById(long id) {
        try (Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate("delete from Users where id = '" + id + "'");
            logger.info(UserDaoJDBCImpl.class.getName() + ": user with id = " + id +" was removed");
        } catch (SQLException e) {
            logger.info(UserDaoJDBCImpl.class.getName() + ": failed, user wasn't removed");
        }
    }

    public List<User> getAllUsers() {
        try (Statement statement = util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery("select name, last_name, age from Users");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("last_name");
                Byte age = resultSet.getByte("age");

                User user = new User(name, lastName, age);
                list.add(user);
            }
            System.out.println(list.toString());
            logger.info(UserDaoJDBCImpl.class.getName() + ": list of users was got");
        } catch (SQLException e) {
            logger.info(UserDaoJDBCImpl.class.getName() + ": failed, list of users wasn't got");
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Statement statement = util.getConnection().createStatement()) {
            statement.executeUpdate("delete from Users");
            logger.info(UserDaoJDBCImpl.class.getName() + "table of users was cleaned");
        } catch (SQLException e) {
            logger.info(UserDaoJDBCImpl.class.getName() + ": failed, table wasn't cleaned");
        }
    }
}
