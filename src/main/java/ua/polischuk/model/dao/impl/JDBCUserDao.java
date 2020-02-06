package ua.polischuk.model.dao.impl;

import org.apache.log4j.Logger;
import ua.polischuk.model.dao.UserRepository;
import ua.polischuk.model.dao.mappers.TestMapper;
import ua.polischuk.model.dao.mappers.UserMapper;
import ua.polischuk.model.entity.Test;
import ua.polischuk.model.entity.User;
import ua.polischuk.model.dao.SQLQwertys;

import java.sql.*;
import java.util.*;

public class JDBCUserDao implements UserRepository {
    public static final String ERROR_WHILE_SAVING_USER = "Error while saving user";
    public static final String ERROR_FIND_ALL = "Error fond all";
    public static final String ERROR_GETTING_CONNECTION = "Error getting connection";
    public static final String ERROR_FINDING_BY_EMAIL = "Error finding by email";
    public static final String SQL_ADDNIG_TO_AVAILABLE = "INSERT INTO available_tests (user_id, test_id) " +
            "SELECT u.id, t.id " +
            "FROM user u, test t " +
            "WHERE u.email = ? " +
            "AND t.name = ?";
    public static final String ERROR_ADDING_TEST_TO_AVAILABLE = "Error adding test to available";
    public static final String TEST_ALREADY_REMOVED_FROM_AVAILABLE = "Test already removed from available";
    public static final String ERROR_WHILE_COMPLETING_TEST = "Error while completing test";
    public static final String CANT_UPDATE_USER_STATS = "Can`t update user stats";
    public static final String CANT_GET_SET_OF_COMPLETED_TESTS_BY_ID = "Cant get set of completed tests by id";
    public static final String CANT_GET_COMPLETED_TESTS_BY_EMAIL = "Cant get completed tests by email";
    public static final String ERROR_DROP_TEST_FROM_AVAILABLE = "Error drop test from available";
    public static final String ERROR_ADD_TEST_TO_COMPLETED = "Error add test to completed";
    public static final String NO_SUCH_TEST_IN_AVAILABLE = "No such test in available";
    public static final String ERROR_DROPPING_FROM_AVAILABLE = "Error dropping from available";
    private int noOfRecords;

    private final ConnectionPoolHolder connectionPoolHolder;

    private static final Logger log = Logger.getLogger( JDBCUserDao.class);

    public JDBCUserDao(final ConnectionPoolHolder connectionPoolHolder) {
        this.connectionPoolHolder = connectionPoolHolder;
    }


    @Override
    public void save(User entity) throws SQLException {

        String sql = SQLQwertys.ADD_NEW_USER;
        try (Connection connection = connectionPoolHolder.getConnection() ) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getFirstNameUa());
            preparedStatement.setString(3, entity.getLastName());
            preparedStatement.setString(4, entity.getLastNameUa());
            preparedStatement.setString(5, entity.getEmail());
            preparedStatement.setString(6, entity.getPassword());
            preparedStatement.setString(7, String.valueOf(entity.getRole()));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            log.error(ERROR_WHILE_SAVING_USER, e);
           throw new SQLException();
        }

    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public List<User> findAll(int offset, int recordsPerPage) {

        Map<Integer, User> users = new HashMap<>();

        String sql = "SELECT * FROM user "+
                " limit "+offset+", "+recordsPerPage+"";

        try(Connection connection = connectionPoolHolder.getConnection()){
        try(Statement stmt = connection.createStatement();) {
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
            log.error(ERROR_FIND_ALL, e);
        }
        }catch (SQLException ex ){
            log.error(ERROR_GETTING_CONNECTION, ex);
        }
        return new ArrayList<>(users.values());
    }



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
      try(  Connection connection = connectionPoolHolder.getConnection();) {
          log.info("FINDING");

          Statement stmt = connection.createStatement();
          String q1 = "select * from user WHERE email = '" + email + "'";
          ResultSet resultSet = stmt.executeQuery(q1);

          if (resultSet.next()) {
              user = userMapper.extractFromResultSet(resultSet);
          }
      }catch (SQLException e){
          log.error(ERROR_FINDING_BY_EMAIL, e);
      }

        return Optional.ofNullable(user);
    }

    @Override
    public int getNoOfRecords() {
        return noOfRecords;
    }

    public void addTestToAvailable(String email, String testName) throws SQLException {
        String sql =
                SQL_ADDNIG_TO_AVAILABLE;

        try (Connection connection = connectionPoolHolder.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, testName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(ERROR_ADDING_TEST_TO_AVAILABLE, e);
           throw new SQLException();
        }
    }

    @Override
    public void completeTest(String email, Integer result, String testName) throws SQLException {

        try(Connection connection = connectionPoolHolder.getConnection() ) {
            try {
                Statement statement = connection.createStatement();
                connection.setAutoCommit(false);
                int userId = findIdByField(email, statement, "user", "email");
                int testId = findIdByField(testName, statement, "test", "name");

                if (!checkIfTestIsActive(testId, statement)) {
                    log.info(TEST_ALREADY_REMOVED_FROM_AVAILABLE);
                    throw new SQLException();
                }

                dropTestFromAvailable(userId, testId, statement);
                addTestToCompleted(userId, testId, result, statement);
                HashMap<Integer, Integer> completedTestsAndResults = (HashMap<Integer, Integer>)
                        getSetOfCompletedTestsByUserId(statement, userId);
                updateUserStats(statement, recountSuccess(completedTestsAndResults), userId);

                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                log.error(ERROR_WHILE_COMPLETING_TEST, e);
                connection.rollback();
                throw new SQLException();
            }
        }
    }

    private boolean checkIfTestIsActive(int idOfTest, Statement statement) throws SQLException {
        boolean isActive = false;
        String sql = "select active from test where id = "+idOfTest;

            ResultSet  resultSet = statement.executeQuery(sql);
            if (resultSet.next()){
                isActive = resultSet.getBoolean(1);
            }
            resultSet.close();
            //C

        return isActive;
    }

    private void updateUserStats(Statement statement, double stats, int id) throws SQLException {
        String updateUserStats=
                "   UPDATE user " +
                "   SET stats =  "+stats +
                "   WHERE id = "+ id;
        try {
            statement.executeUpdate(updateUserStats);
        }catch (SQLException e){
            log.error(CANT_UPDATE_USER_STATS, e);
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

        //try {
            ResultSet  resultSet = statement.executeQuery(getCompletedTests);
            while (resultSet.next()){
                completedTestsIdWithResults.put(resultSet.getInt(1), resultSet.getInt(2));
            }
            resultSet.close();
        /*} catch (SQLException e) {
            log.error(CANT_GET_SET_OF_COMPLETED_TESTS_BY_ID, e);//C
            throw new SQLException();
        }*/
        return completedTestsIdWithResults;
    }

    @Override
    public ArrayList<Test> getCompletedTestsByEmail(String email) throws SQLException {
        ArrayList<Test> completedTests = new ArrayList<>();

        String sql =
                "SELECT  test.* "+
                " FROM completed_tests "+
                "INNER JOIN test "+
                "ON completed_tests.test_id = test.id "+
                "INNER JOIN user "+
                "ON completed_tests.user_id = user.id "+
                "WHERE user.email = '" + email + "'";

        Map<Integer, Test> tests = new HashMap<>();

        TestMapper testMapper = new TestMapper();

        try(Connection connection = connectionPoolHolder.getConnection()) {
            try {
                Statement stmt = connection.createStatement();
                connection.setAutoCommit(false);
                ResultSet resultSet = stmt.executeQuery(sql);
                while (resultSet.next()) {
                    Test test = testMapper.extractFromResultSet(resultSet);
                    test = testMapper.makeUnique(tests, test);
                    completedTests.add(test);
                }
                resultSet.close();

                String getResults =
                        "SELECT result FROM completed_tests" +
                                " INNER JOIN user ON completed_tests.user_id =" +
                                " user.id WHERE user.email = '" + email + "'";

                resultSet = stmt.executeQuery(getResults);

                int count = 0;
                while (resultSet.next()) {
                    completedTests.get(count).setResult(resultSet.getInt(1));
                    count++;
                }

                connection.commit();
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                log.error(CANT_GET_COMPLETED_TESTS_BY_EMAIL, e);
                connection.rollback();
                //throw new SQLException(); //C Проверка в сервисе на null?
            }
        }
        return completedTests;

    }

    private void addTestToCompleted(int userId, int testId, int result, Statement statement) throws SQLException {
        HashMap<Integer, Integer> completedTests = (HashMap<Integer, Integer>) getSetOfCompletedTestsByUserId(statement, userId);

        if(completedTests.containsKey(testId)){
           String dropTestFromCompleted = "delete from completed_tests where test_id = "+testId;
           try {
               statement.executeUpdate(dropTestFromCompleted);
           }catch (SQLException e){
                log.error(ERROR_DROP_TEST_FROM_AVAILABLE, e);
               throw new SQLException();
           }
       }

        String addTestToCompleted =
                "INSERT INTO completed_tests (user_id, result, test_id) VALUES ("+userId +", "+ result+", "+ testId+")";
        try {
            // statement.executeQuery(dropTestFromAvailable);
            statement.executeUpdate(addTestToCompleted);
        }catch (SQLException e){
            log.error(ERROR_ADD_TEST_TO_COMPLETED, e);
            throw new SQLException(); // сделать свой exception?
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
            log.info(NO_SUCH_TEST_IN_AVAILABLE);
            throw new SQLException(); // own exception?
        } //проверяем, не прошли ли тест в другом окне пока это ждало
        resultSet.close();

        String dropTestFromAvailable = "delete from available_tests where user_id ="+userId + " and test_id = "+testId ;
        try {
            statement.executeUpdate(dropTestFromAvailable);
        }catch (SQLException e){
            log.error(ERROR_DROPPING_FROM_AVAILABLE, e);
          //  throw new SQLException();// ?
        }
    }

    private int findIdByField(String key, Statement statement, String tableName, String fieldName) throws SQLException {
        int id = 0;
        String findCurrentUserId ="select id from "+tableName+" WHERE "+ fieldName +" ='"+key+"'";

        //try {
            ResultSet resultSet = statement.executeQuery(findCurrentUserId);
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            resultSet.close();
     /*   }catch (SQLException e){
            log.error("Cant find by field", e);
            throw new SQLException();
        }*/
        return id;
    }

    @Override
    public Set<Test> getAvailableTestsSet(String email) throws SQLException {
        Set<Test> tests = new HashSet<>();
        TestMapper testMapper = new TestMapper();
        String sql =
                "SELECT test.* "+
                " FROM available_tests "+
                "INNER JOIN test "+
                "ON available_tests.test_id = test.id "+
                "INNER JOIN user "+
                "ON available_tests.user_id = user.id "+
                "WHERE user.email = '" + email + "'";


        try(  Connection connection = connectionPoolHolder.getConnection()) {

            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                Test test;
                test = testMapper.extractFromResultSet(resultSet);
                tests.add(test);
            }
        }catch (SQLException e){
            log.error("Available tests exception");
        }
        return tests;
    }
}
