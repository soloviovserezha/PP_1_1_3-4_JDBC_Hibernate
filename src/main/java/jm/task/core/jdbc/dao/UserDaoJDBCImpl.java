package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {

    private final Logger logger = Logger.getLogger(UserDao.class.getName());
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        final String createUsersTable = "create table if not exists Users(" +
                "id BIGINT not null AUTO_INCREMENT, " +
                "name TINYTEXT NOT NULL, " +
                "last_name TINYTEXT NOT NULL, " +
                "age TINYINT NOT NULL, " +
                "PRIMARY KEY(id))";
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(createUsersTable)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.log(Level.INFO, "failed, table wasn't created", e);
        }
    }

    public void dropUsersTable() {
        final String dropUsersTable = "drop table if exists Users";
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(dropUsersTable)) {
            preparedStatement.execute();
            logger.info("table was dropped");
        } catch (SQLException e) {
            logger.log(Level.INFO, "fail drop - table not exists", e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "insert into Users (name, last_name, age) VALUES (?, ?, ?)";
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            logger.info(UserDaoJDBCImpl.class.getName() + ": user={" + name + "} was saved");
        } catch (SQLException e) {
            logger.log(Level.INFO, "failed, user wasn't saved", e);
        }
    }

    public void removeUserById(long id) {
        String removeUserById = "delete from Users where id = ?";
        try (Connection connection = Util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(removeUserById)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.log(Level.INFO, "failed, user wasn't removed", e);
        }
    }

    public List<User> getAllUsers() {
        final List<User> list = new ArrayList<>();
        String getAllUsers = "select name, last_name, age from Users";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getAllUsers);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                list.add(new User(
                        resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getByte("age")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.INFO, "failed, list of users wasn't got", e);
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            final String cleanUsersTable = "truncate Users";
            PreparedStatement preparedStatement = connection.prepareStatement(cleanUsersTable);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.INFO, "failed, table wasn't cleaned", e);
        }
    }
}
