package com.codegym.dao;

import com.codegym.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {

    private static String URL = "jdbc:mysql://localhost:3306/demo1";
    private static String Username = "root";
    private static String Password = "12345678";

    final String INSET_USER = "INSERT INTO users" + "(name,email,country) VALUES " +
            "(?,?,?)";
    private static final String SELECT_USER_BY_ID = "select id,name,email,country from users where id =?";
    private static final String SELECT_ALL_USERS = "select * from users";
    private static final String DELETE_USERS_SQL = "delete from users where id = ?;";
    private static final String UPDATE_USERS_SQL = "update users set name = ?,email= ?, country =? where id = ?;";

    public UserDAO() {
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, Username, Password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    @Override
    public void insertUser(User user) {
        System.out.println(INSET_USER);
        Connection conn = getConnection();

        try {
            PreparedStatement pre = conn.prepareStatement(INSET_USER);
            pre.setString(1, user.getName());
            pre.setString(2, user.getEmail());
            pre.setString(3, user.getCountry());

            pre.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public User selectUser(int id) {
        User user = null;
        Connection conn = getConnection();
        try {
            PreparedStatement pre = conn.prepareStatement(SELECT_USER_BY_ID);
            pre.setInt(1, id);
            System.out.println(pre);

            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");

                user = new User(id, name, email, country);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }


    @Override
    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        Connection conn = getConnection();

        try {
            PreparedStatement pre = conn.prepareStatement(SELECT_ALL_USERS);
            System.out.println(pre);

            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");

                users.add(new User(id, name, email, country));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS_SQL);) {
            preparedStatement.setInt(1, id);
            rowDeleted = preparedStatement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdate;
        try (Connection conn = getConnection();
             PreparedStatement pre = conn.prepareStatement(UPDATE_USERS_SQL);) {

            pre.setString(1, user.getName());
            pre.setString(2, user.getEmail());
            pre.setString(3, user.getCountry());
            pre.setInt(4, user.getId());
            rowUpdate = pre.executeUpdate() > 0;
        }
        return rowUpdate;
    }
    final String FIND_DATA = "select * from users where name like ? or email like ? or country like ?;";
    @Override
    public List<User> find(String keyWord) {
        List<User> list = new ArrayList<>();
        String key = "%" + keyWord + "%";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_DATA);) {
            preparedStatement.setString(1,key);
            preparedStatement.setString(2,key);
            preparedStatement.setString(3,key);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                User user = new User(id, name, email, country);
                list.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    private void printSQLException(SQLException ex){
        for (Throwable e : ex){if (e instanceof SQLException) {
            e.printStackTrace(System.err);
            System.err.println("SQLState: " + ((SQLException) e).getSQLState());
            System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
            System.err.println("Message: " + e.getMessage());
            Throwable t = ex.getCause();
            while (t != null) {
                System.out.println("Cause: " + t);
                t = t.getCause();
            }
        }
        }
    }
        }

