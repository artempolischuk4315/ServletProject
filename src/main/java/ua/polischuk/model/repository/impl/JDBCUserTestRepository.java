package ua.polischuk.model.repository.impl;

import org.apache.log4j.Logger;
import ua.polischuk.model.repository.UserTestRepository;
import ua.polischuk.model.repository.mapper.TestMapper;
import ua.polischuk.model.entity.Test;

import java.sql.*;
import java.util.*;

import static ua.polischuk.model.repository.impl.SQLQwertys.SQL_ADDNIG_TO_AVAILABLE;


public class JDBCUserTestRepository implements UserTestRepository {

    public static final String ERROR_ADDING_TEST_TO_AVAILABLE = "Error adding test to available";
    public static final String TEST_ALREADY_REMOVED_FROM_AVAILABLE = "Test already removed from available";
    public static final String ERROR_WHILE_COMPLETING_TEST = "Error while completing test";
    public static final String CANT_UPDATE_USER_STATS = "Can`t update user stats";
    public static final String CANT_GET_COMPLETED_TESTS_BY_EMAIL = "Cant get completed tests by email";
    public static final String ERROR_DROP_TEST_FROM_AVAILABLE = "Error drop test from available";
    public static final String ERROR_ADD_TEST_TO_COMPLETED = "Error add test to completed";
    public static final String NO_SUCH_TEST_IN_AVAILABLE = "No such test in available";
    public static final String ERROR_DROPPING_FROM_AVAILABLE = "Error dropping from available";

    private final ConnectionPoolHolder connectionPoolHolder;

    private static final Logger log = Logger.getLogger( JDBCUserRepository.class);

    public JDBCUserTestRepository(final ConnectionPoolHolder connectionPoolHolder) {
        this.connectionPoolHolder = connectionPoolHolder;
    }


    @Override
    public boolean completeTest(String email, Integer result, String testName)  {

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
                return true;
            } catch (SQLException e) {
                log.error(ERROR_WHILE_COMPLETING_TEST, e);
                connection.rollback();
                return false;
            }
        } catch (SQLException ex) {
            log.error(ex);
            return false;
        }
    }

    private boolean checkIfTestIsActive(int idOfTest, Statement statement) throws SQLException {
        boolean isActive = false;
        String sql = "select active from test where id = "+idOfTest;

        ResultSet resultSet = statement.executeQuery(sql);
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
            int size = completedTestsAndResults.keySet().size();
            double sumResults = completedTestsAndResults.keySet().stream()
                    .mapToInt(completedTestsAndResults::get)
                    .sum();
            success = sumResults/size;
        }
        return success;
    }

    private Map<Integer, Integer> getSetOfCompletedTestsByUserId(Statement statement, int userId) throws SQLException {
        String getCompletedTests = "select test_id, result from completed_tests  WHERE user_id =" + " '" + userId + "'";
        Map<Integer, Integer> completedTestsIdWithResults = new HashMap<>();

        ResultSet  resultSet = statement.executeQuery(getCompletedTests);
        while (resultSet.next()){
            completedTestsIdWithResults.put(resultSet.getInt(1), resultSet.getInt(2));
        }
        resultSet.close();

        return completedTestsIdWithResults;
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
            throw new SQLException();
        } //проверяем, не прошли ли тест в другом окне пока это ждало
        resultSet.close();

        String dropTestFromAvailable = "delete from available_tests where user_id ="+userId + " and test_id = "+testId ;
        try {
            statement.executeUpdate(dropTestFromAvailable);
        }catch (SQLException e){
            log.error(ERROR_DROPPING_FROM_AVAILABLE, e);
            throw new SQLException();
        }
    }

    private int findIdByField(String key, Statement statement, String tableName, String fieldName) throws SQLException {
        int id = 0;
        String findCurrentUserId ="select id from "+tableName+" WHERE "+ fieldName +" ='"+key+"'";

        ResultSet resultSet = statement.executeQuery(findCurrentUserId);
        if (resultSet.next()) {
            id = resultSet.getInt(1);
        }
        resultSet.close();

        return id;
    }

    @Override
    public Set<Test> getAvailableTestsSetByEmail(String email)  {
        Set<Test> tests = new HashSet<>();
        TestMapper testMapper = new TestMapper();
        String sql = SQLQwertys.GET_AVAILABLE_TESTS_BY_EMAIL + email + "'";


        try( Connection connection = connectionPoolHolder.getConnection()) {

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

    @Override
    public ArrayList<Test> getCompletedTestsByEmail(String email) throws SQLException {
        ArrayList<Test> completedTests = new ArrayList<>();

        String sql = SQLQwertys.GET_COMPLETED_TESTS_BY_EMAIL+ email + "'";

        TestMapper testMapper = new TestMapper();

        try(Connection connection = connectionPoolHolder.getConnection()) {
            try {
                Statement stmt = connection.createStatement();
                connection.setAutoCommit(false);
                ResultSet resultSet = stmt.executeQuery(sql);
                while (resultSet.next()) {
                    Test test = testMapper.extractFromResultSet(resultSet);
                    completedTests.add(test);
                }
                resultSet.close();

                String getResults = SQLQwertys.GET_RESULTS + email + "'";

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
            }
        }

        return completedTests;

    }

    @Override
    public void addTestToAvailableByEmailAndNameOfTest(String email, String testName) throws SQLException {

        try (Connection connection = connectionPoolHolder.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADDNIG_TO_AVAILABLE);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, testName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(ERROR_ADDING_TEST_TO_AVAILABLE, e);
            throw new SQLException();
        }
    }
}
