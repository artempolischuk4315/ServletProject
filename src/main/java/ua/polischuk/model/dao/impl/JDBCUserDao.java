package ua.polischuk.model.dao.impl;

import ua.polischuk.model.dao.UserRepository;
import ua.polischuk.model.dao.mappers.UserMapper;
import ua.polischuk.model.entity.Category;
import ua.polischuk.model.entity.Test;
import ua.polischuk.model.entity.User;
import ua.polischuk.model.dao.SQLQwertys;

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
    public void completeTest(String email, Integer result, String testName) throws SQLException {

        try( Statement statement = connection.createStatement()){
            connection.setAutoCommit(false);
            int userId = findIdByField(email, statement, "user", "email");
            int testId = findIdByField(testName, statement, "test", "name");
            dropTestFromAvailable(userId, testId, statement);
            addTestToCompleted(userId, testId, result, statement);
            HashMap<Integer ,Integer> completedTestsAndResults = (HashMap<Integer, Integer>)
                    getSetOfCompletedTestsByUserId(statement, userId);
            updateUserStats(statement, recountSuccess(completedTestsAndResults), userId);

            connection.commit();
            connection.setAutoCommit(true);
        }catch (SQLException e){
            connection.rollback();
            e.printStackTrace();
            throw new SQLException();
        }

    }

    private void updateUserStats(Statement statement, double stats, int id) throws SQLException {
        String updateUserStats=
                "   UPDATE user " +
                "   SET stats =  "+stats +
                "   WHERE id = "+ id;
        try {
            // statement.executeQuery(dropTestFromAvailable);
            statement.executeUpdate(updateUserStats);
        }catch (SQLException e){
            throw new SQLException();
        }

    }

    private double recountSuccess(HashMap<Integer, Integer> completedTestsAndResults) {
        double success;
        if( completedTestsAndResults.keySet().isEmpty()){
            success = 0.0;
        }else{
            Integer size = completedTestsAndResults.keySet().size();
            double sumResults = completedTestsAndResults.keySet().stream()
                    .mapToInt(testId -> completedTestsAndResults.get(testId))
                    .sum();
            success = sumResults/size;
        }
        return success;
    }

    private Map<Integer, Integer> getSetOfCompletedTestsByUserId(Statement statement, int userId) throws SQLException {
        String getCompletedTests = "select test_id, result from completed_tests  WHERE user_id =" + " '" + userId + "'";
        Map<Integer, Integer> completedTestsIdWithResults = new HashMap<>();

        try {
            ResultSet  resultSet = statement.executeQuery(getCompletedTests);
            while (resultSet.next()){
                completedTestsIdWithResults.put(resultSet.getInt(1), resultSet.getInt(2));
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new SQLException();
        }
        return completedTestsIdWithResults;
    }

    private void addTestToCompleted(int userId, int testId, int result, Statement statement) throws SQLException {

        HashMap<Integer, Integer> completedTests = (HashMap<Integer, Integer>) getSetOfCompletedTestsByUserId(statement, userId);
        if(completedTests.containsKey(testId)){
           String dropTestFromCompleted = "delete from completed_tests where test_id = "+testId;
           try {
               System.out.println("DROP FROM COMPLETED");
               statement.executeUpdate(dropTestFromCompleted);
           }catch (SQLException e){
               System.out.println("Cam't drop");
               throw new SQLException();
           }
       }

        String addTestToCompleted =
                "INSERT INTO completed_tests (user_id, result, test_id) VALUES ("+userId +", "+ result+", "+ testId+")";
        try {
            System.out.println("DROP");
            // statement.executeQuery(dropTestFromAvailable);
            statement.executeUpdate(addTestToCompleted);
        }catch (SQLException e){
            System.out.println("Cam't drop");
            throw new SQLException();
        }
    }

    private void dropTestFromAvailable(int userId, int testId, Statement statement) throws SQLException {
        Integer id = null;
        String selectTestIdFromAvailable = "select test_id from available_tests where user_id ="+userId + " and test_id = "+testId ;
        ResultSet resultSet = statement.executeQuery(selectTestIdFromAvailable);
        if (resultSet.next()) {
            id = resultSet.getInt(1);
        }
        if(id == null){
            throw new SQLException("no such test in available");
        } //проверяем, не прошли ли тест в другом окне пока это ждало
        resultSet.close();


        String dropTestFromAvailable = "delete from available_tests where user_id ="+userId + " and test_id = "+testId ;
        try {

            statement.executeUpdate(dropTestFromAvailable);
        }catch (SQLException e){
            throw new SQLException();
        }
    }

    private int findIdByField(String key, Statement statement, String tableName, String fieldName) throws SQLException {
        int id = 0;
        String findCurrentUserId ="select id from "+tableName+" WHERE "+ fieldName +" ='"+key+"'";

        try {
            ResultSet resultSet = statement.executeQuery(findCurrentUserId);
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            resultSet.close();
        }catch (SQLException e){
            e.printStackTrace();
            throw new SQLException();
        }
        return id;
    }

    @Override
    public Set<Test> getAvailableTestsSet(String email) throws SQLException {
        Set<Test> tests = new HashSet<>();

        String sql = "SELECT test.* "+
                " FROM available_tests "+
                "INNER JOIN test "+
                "ON available_tests.test_id = test.id "+
                "INNER JOIN user "+
                "ON available_tests.user_id = user.id "+
                "WHERE user.email = '" + email + "'";
                //+" limit "+offset+", "+recordsPerPage;

       Statement stmt = connection.createStatement();
      //  connection.setAutoCommit(false);

        ResultSet resultSet = stmt.executeQuery(sql);
        while (resultSet.next()){
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
       // resultSet.close();
       /* resultSet = stmt.executeQuery("SELECT COUNT(*) from available_tests");

        if (resultSet.next())
            noOfRecords = resultSet.getInt(1);

        connection.commit();
        connection.setAutoCommit(true);*/
        return tests;
    }

}
