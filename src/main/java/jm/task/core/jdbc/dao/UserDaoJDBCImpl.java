package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Users (id INT KEY AUTO_INCREMENT," +
                    "name VARCHAR(255),lastName VARCHAR(255), age TINYINT)");
        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS Users");  // Не стал здесь использовать PreparedStatement,
        } catch (SQLException s) {                                      // так как удаляется вся таблица.
            s.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        final String insertUser = "INSERT INTO Users (name, lastName, age) VALUES (?, ?, ?)";

        try (Connection connection = Util.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(insertUser);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();

        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        final String removeUser = "DELETE FROM Users WHERE id = ?";
        try (Connection connection = Util.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(removeUser);
            statement.setLong(1, id);

        } catch (SQLException s) {
            s.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();                                        // Не стал здесь использовать PreparedStatement,
        try (Connection connection = Util.getConnection()) {                         // так как выбираются все Users.
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");
                User user = new User(name, lastName, age);
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }

        } catch (SQLException s) {
            s.printStackTrace();
        }

        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM Users");    // Здесь тоже не использовал PreparedStatement.
        } catch (
                SQLException s) {                               // Наврено я не прав и все-таки в этих случаях тоже надо его использовать?
            s.printStackTrace();
        }
    }
}
