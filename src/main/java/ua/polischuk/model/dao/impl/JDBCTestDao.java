package ua.polischuk.model.dao.impl;

import ua.polischuk.model.dao.TestRepository;
import ua.polischuk.model.dao.mappers.TestMapper;
import ua.polischuk.model.entity.Category;
import ua.polischuk.model.entity.Test;
import ua.polischuk.model.dao.SQLQwertys;

import java.sql.*;
import java.util.*;

public class JDBCTestDao implements TestRepository {


    private int noOfRecords;
    private Connection connection;

    public JDBCTestDao(Connection connection) {
        this.connection = connection;
    }
    @Override
    public Optional<Test> findByName(String name) throws SQLException {
        TestMapper testMapper = new TestMapper();

        String q1 = "select * from test WHERE name = '" + name + "'";
        Test test = null;
        try {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(q1);
            if (resultSet.next()) {
                test = testMapper.extractFromResultSet(resultSet);
            }
        }catch (SQLException e){
            throw new SQLException();
        }
        return Optional.of(test);
    }

    @Override
    public int getNoOfRecords() {
        return noOfRecords;
    }




    @Override
    public void save(Test entity) throws SQLException {

        String sql = SQLQwertys.ADD_NEW_TEST;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getNameUa());
            preparedStatement.setString(3, String.valueOf(entity.getCategory()));
            preparedStatement.setInt(4, entity.getDifficulty());
            preparedStatement.setInt(5, entity.getNumberOfQuestions());
            preparedStatement.setInt(6, entity.getTimeLimit());
            preparedStatement.setBoolean(7, entity.isActive());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }

    }

    @Override
    public Test findById(int id) {
        return null;
    }

    @Override
    public List<Test> findAll(int offset, int recordsPerPage) throws SQLException {

        Map<Integer, Test> tests = new HashMap<>();
        String sql = "SELECT * FROM test "+
                " limit "+offset+", "+recordsPerPage+"";

        try( Statement stmt = connection.createStatement()) {
            connection.setAutoCommit(false);
            ResultSet resultSet = stmt.executeQuery(sql);

            TestMapper testMapper = new TestMapper();

            while (resultSet.next()) {
                Test test = testMapper.extractFromResultSet(resultSet);
                test = testMapper.makeUnique(tests, test);
            }
            resultSet.close();
            resultSet = stmt.executeQuery("SELECT COUNT(*) from test");

            if (resultSet.next())
                noOfRecords = resultSet.getInt(1);

            connection.commit();
            connection.setAutoCommit(true);

        }catch (SQLException e){
            connection.rollback();
            e.printStackTrace();
            throw new SQLException();
        }
        return new ArrayList<>(tests.values());
    }

    @Override
    public void update(Test entity) {

    }

    @Override
    public void delete(String testName) throws SQLException {

        String sql = "delete from test where name =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, testName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }

    }

    @Override
    public void enableOrDisableTest(String nameOfTest, boolean active) throws SQLException {
        Statement statement = connection.createStatement();
        String updateTest=
                        "   UPDATE test " +
                        "   SET active =  "+active +
                        "   WHERE name = '"+ nameOfTest+"'";
        try {
            // statement.executeQuery(dropTestFromAvailable);
            statement.executeUpdate(updateTest);
        }catch (SQLException e){
            e.printStackTrace();
            throw new SQLException();
        }

    }

    @Override
    public void close() {

    }
}
