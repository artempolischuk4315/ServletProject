package ua.polischuk.model.repository.impl;

import org.apache.log4j.Logger;
import ua.polischuk.model.repository.TestRepository;
import ua.polischuk.model.repository.mapper.TestMapper;
import ua.polischuk.model.entity.Test;

import java.sql.*;
import java.util.*;

public class JDBCTestRepository implements TestRepository {

    private final ConnectionPoolHolder connectionPoolHolder;

    private int noOfRecords;

    private static final Logger log = Logger.getLogger( JDBCUserRepository.class);

    public JDBCTestRepository(final ConnectionPoolHolder connectionPoolHolder) {
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
           log.error("Exception in saving by name", e);
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

        List<Test> tests = new ArrayList<>();

        String sql = "SELECT * FROM test "+
                " limit "+offset+", "+recordsPerPage+"";

        try(Connection connection = connectionPoolHolder.getConnection()){
            Statement stmt = connection.createStatement();

            connection.setAutoCommit(false);

            ResultSet resultSet = stmt.executeQuery(sql);

            TestMapper testMapper = new TestMapper();

            while (resultSet.next()) {
                Test test = testMapper.extractFromResultSet(resultSet);
                tests.add(test);

            }
            resultSet.close();
            resultSet = stmt.executeQuery("SELECT COUNT(*) from test");

            if (resultSet.next())
                noOfRecords = resultSet.getInt(1);


        } catch (SQLException e) {
            log.error(e);
        }
        return tests;
    }

    @Override
    public void update(Test entity) {
        // for now there is no need of this method in program
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
    public boolean enableOrDisableTest(String nameOfTest, boolean active)  {

        String updateTest=
                        "   UPDATE test " +
                        "   SET active =  "+active +
                        "   WHERE name = '"+ nameOfTest+"'";
        try (Connection connection = connectionPoolHolder.getConnection()){
            Statement statement = connection.createStatement();
            statement.executeUpdate(updateTest);
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    @Override
    public void close() {

    }




}
