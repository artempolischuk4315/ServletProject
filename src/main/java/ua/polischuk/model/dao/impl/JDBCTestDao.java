package ua.polischuk.model.dao.impl;

import org.apache.log4j.Logger;
import ua.polischuk.model.dao.TestRepository;
import ua.polischuk.model.dao.mappers.TestMapper;
import ua.polischuk.model.entity.Test;
import ua.polischuk.model.dao.SQLQwertys;

import java.sql.*;
import java.util.*;

public class JDBCTestDao implements TestRepository {

    private final ConnectionPoolHolder connectionPoolHolder;

    private int noOfRecords;

    private static final Logger log = Logger.getLogger( JDBCUserDao.class);

    public JDBCTestDao(final ConnectionPoolHolder connectionPoolHolder) {
        this.connectionPoolHolder = connectionPoolHolder;
    }

    @Override
    public Optional<Test> findByName(String name)  {
        TestMapper testMapper = new TestMapper();

        String q1 = "select * from test WHERE name = '" + name + "'";
        Test test = null;
        try (Connection connection = connectionPoolHolder.getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(q1);
            if (resultSet.next()) {
                test = testMapper.extractFromResultSet(resultSet);
            }
        }catch (SQLException e){
           // throw new SQLException();
        }
        return Optional.ofNullable(test);
    }

    @Override
    public int getNoOfRecords() {
        return noOfRecords;
    }


    @Override
    public boolean save(Test entity)  {

        String sql = SQLQwertys.ADD_NEW_TEST;
        try (Connection connection = connectionPoolHolder.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getNameUa());
            preparedStatement.setString(3, String.valueOf(entity.getCategory()));
            preparedStatement.setInt(4, entity.getDifficulty());
            preparedStatement.setInt(5, entity.getNumberOfQuestions());
            preparedStatement.setInt(6, entity.getTimeLimit());
            preparedStatement.setBoolean(7, entity.isActive());
            int result = preparedStatement.executeUpdate();

            return result ==1;
        } catch (SQLException e) {
            return false;
        }

    }

    @Override
    public Test findById(int id) {
        return null;
    }

    @Override
    public List<Test> findAll(int offset, int recordsPerPage)  {

        Map<Integer, Test> tests = new HashMap<>();
        String sql = "SELECT * FROM test "+
                " limit "+offset+", "+recordsPerPage+"";

        try(Connection connection = connectionPoolHolder.getConnection()){
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
            throw new SQLException(); //TODO
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(tests.values());
    }

    @Override
    public void update(Test entity) {

    }

    @Override
    public void delete(String testName) throws SQLException {

        String sql = "delete from test where name =?";
        try (Connection connection = connectionPoolHolder.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, testName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }

    }

    @Override
    public void enableOrDisableTest(String nameOfTest, boolean active) throws SQLException {

        String updateTest=
                        "   UPDATE test " +
                        "   SET active =  "+active +
                        "   WHERE name = '"+ nameOfTest+"'";
        try (Connection connection = connectionPoolHolder.getConnection()){
            Statement statement = connection.createStatement();
            // statement.executeQuery(dropTestFromAvailable);
            statement.executeUpdate(updateTest);
        }catch (SQLException e){
            e.printStackTrace();
            throw new SQLException(); //TODO
        }

    }

    @Override
    public void close() {

    }




}
