package ua.polischuk.model.dao.impl;

import ua.polischuk.model.dao.UserRepository;
import ua.polischuk.model.dao.mappers.TestMapper;
import ua.polischuk.model.dao.mappers.UserMapper;
import ua.polischuk.model.entity.Category;
import ua.polischuk.model.entity.Test;
import ua.polischuk.model.entity.User;
import ua.polischuk.utility.SQLQwertys;

import java.sql.*;
import java.util.*;

public class JDBCUserDao implements UserRepository {
    private int noOfRecords;
    private Connection connection;

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void save(User entity) throws SQLException {

        String sql = SQLQwertys.ADD_NEW_USER;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getFirstNameUa());
            preparedStatement.setString(3, entity.getLastName());
            preparedStatement.setString(4, entity.getLastNameUa());
            preparedStatement.setString(5, entity.getEmail());
            preparedStatement.setString(6, entity.getPassword());
            preparedStatement.setString(7, String.valueOf(entity.getRole()));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
           throw new SQLException();
        }

    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public List<User> findAll(int offset, int recordsPerPage) throws SQLException {

        Map<Integer, User> users = new HashMap<>();

        String sql = "SELECT * FROM user "+
                " limit "+offset+", "+recordsPerPage+"";

        try {
            Statement stmt = connection.createStatement();
            connection.setAutoCommit(false);
            ResultSet resultSet = stmt.executeQuery(sql);

            UserMapper userMapper = new UserMapper();
            TestMapper testMapper = new TestMapper();

            while (resultSet.next()) {
                User user = userMapper.extractFromResultSet(resultSet);
                user = userMapper.makeUnique(users, user);
            }
            resultSet.close();
            resultSet = stmt.executeQuery("SELECT COUNT(*) from user");

            if (resultSet.next())
                noOfRecords = resultSet.getInt(1);

            connection.commit();
            connection.setAutoCommit(true);

        }catch (SQLException e){
            connection.rollback();
            e.printStackTrace();
            throw new SQLException();
        }
        return new ArrayList<>(users.values());
    }


  /*  private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        try {

            System.out.println(user.toString());
        } catch (SQLException e) {
            System.out.println("here   " + user.toString());
            e.printStackTrace();
            throw new SQLException();
        }
        return user;
    }*/

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(String email) {

    }

    @Override
    public void close() {

    }

    @Override
    public Optional<User> findByEmail(String email)  {

        UserMapper userMapper = new UserMapper();
        String sql = "SELECT id, firstName, firstNameUa, lastName, lastNameUa, email, password, role FROM user WHERE email=?";
        User user = null;
      try(  Statement stmt = connection.createStatement()) {
          String q1 = "select * from user WHERE email = '" + email + "'";
          ResultSet resultSet = stmt.executeQuery(q1);

          if (resultSet.next()) {
              user = userMapper.extractFromResultSet(resultSet);
          }


      }catch (SQLException e){
          e.printStackTrace();
      }

        return Optional.of(user);
    }

    @Override
    public int getNoOfRecords() {
        return noOfRecords;
    }

    public void addTestToAvailable(String email, String testName)  {
        System.out.println("DAO");
        String sql = "INSERT INTO available_tests (user_id, test_id) " +
                "SELECT u.id, t.id " +
                "FROM user u, test t " +
                "WHERE u.email = ? " +
                "AND t.name = ?";
        System.out.println("AFTER QUERY");
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, testName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<Test> getAvailableTestsSet(String email) throws SQLException {
        Set<Test> tests = new HashSet<>();

        String sql = "SELECT test.* "+
                "FROM available_tests "+
                "INNER JOIN test "+
                "ON available_tests.test_id = test.id "+
                "INNER JOIN user "+
                "ON available_tests.user_id = user.id "+
                "WHERE user.email = '" + email + "'";
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        while (resultSet.next()){
            System.out.println("ID IS "+resultSet.getInt(1));
            System.out.println("NAME IS "+resultSet.getString(2));
            Test test = new Test();
            test.setId(resultSet.getInt(1));
            test.setName(resultSet.getString(2));
            test.setNameUa(resultSet.getString(3));
            test.setCategory(Category.getCategoryByString(resultSet.getString(8)));
            test.setDifficulty(resultSet.getInt(4));
            test.setNumberOfQuestions(resultSet.getInt(5));
            test.setTimeLimit(resultSet.getInt(6));
            tests.add(test);
        }
        return tests;
    }


}
