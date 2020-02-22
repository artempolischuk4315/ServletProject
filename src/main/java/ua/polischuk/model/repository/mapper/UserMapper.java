package ua.polischuk.model.repository.mapper;

import ua.polischuk.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ObjectMapper<User>{

    private static final String ADMIN = "ADMIN";

    @Override
        public User extractFromResultSet(ResultSet resultSet) throws SQLException {
            User user = new User();
            user.setId(resultSet.getInt(1));
            user.setFirstName(resultSet.getString(2));
            user.setFirstNameUa(resultSet.getString(3));
            user.setLastName(resultSet.getString(4));
            user.setLastNameUa(resultSet.getString(5));
            user.setEmail(resultSet.getString(6));
            user.setPassword(resultSet.getString(7));
            if (resultSet.getString(8).equals(ADMIN)) {
                user.setRole(User.ROLE.ADMIN);
            } else user.setRole(User.ROLE.USER);
            user.setStats(resultSet.getDouble(9));
            return user;
        }




}
