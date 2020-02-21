package ua.polischuk.model.repository.impl;

import org.apache.log4j.Logger;
import ua.polischuk.model.repository.UserRepository;
import ua.polischuk.model.repository.mapper.UserMapper;
import ua.polischuk.model.entity.User;

import java.sql.*;
import java.util.*;

public class JDBCUserRepository implements UserRepository {
    public static final String ERROR_WHILE_SAVING_USER = "Error while saving user";
    public static final String ERROR_FIND_ALL = "Error fond all";
    public static final String ERROR_GETTING_CONNECTION = "Error getting connection";
    public static final String ERROR_FINDING_BY_EMAIL = "Error finding by email";

    private int noOfRecords;

    private final ConnectionPoolHolder connectionPoolHolder;

    private static final Logger log = Logger.getLogger( JDBCUserRepository.class);

    public JDBCUserRepository(final ConnectionPoolHolder connectionPoolHolder) {
        this.connectionPoolHolder = connectionPoolHolder;
    }


    @Override
    public boolean save(User entity)  {

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
            int result = preparedStatement.executeUpdate();

            return result ==1;

        } catch (SQLException e) {
            log.error(ERROR_WHILE_SAVING_USER, e);
            return false;
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
        try(Statement stmt = connection.createStatement()) {
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
        // for now there is no need of this method in program
    }

    @Override
    public void delete(String email) {
        // for now there is no need of this method in program
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

    @Override
    public void close() {

    }
}
