package ua.polischuk.model.repository.impl;

public interface SQLQwertys {
    String ADD_NEW_USER = "INSERT INTO USER ( firstName, firstNameUa, lastName, lastNameUa, email, password, role )" +
            "VALUES(?,?,?,?,?,?,?)";
    String FIND_BY_EMAIL = "SELECT id, firstName, firstNameUa, lastName, lastNameUa, email, password, role FROM user WHERE email=?";
    String ADD_NEW_TEST = "INSERT INTO test (name, nameUa, category, difficulty, numberOfQuestions, timeLimit, active)" +
            "VALUES(?,?,?,?,?,?,?)";
    String GET_AVAILABLE_TESTS_BY_EMAIL = "SELECT test.* "+
            " FROM available_tests "+
            "INNER JOIN test "+
            "ON available_tests.test_id = test.id "+
            "INNER JOIN user "+
            "ON available_tests.user_id = user.id "+
            "WHERE user.email = '";

    String GET_COMPLETED_TESTS_BY_EMAIL ="SELECT  test.* "+
            " FROM completed_tests "+
            "INNER JOIN test "+
            "ON completed_tests.test_id = test.id "+
            "INNER JOIN user "+
            "ON completed_tests.user_id = user.id "+
            "WHERE user.email = '";
    String GET_RESULTS = "SELECT result FROM completed_tests" +
            " INNER JOIN user ON completed_tests.user_id =" +
            " user.id WHERE user.email = '";

    String SQL_ADDNIG_TO_AVAILABLE = "INSERT INTO available_tests (user_id, test_id) " +
            "SELECT u.id, t.id " +
            "FROM user u, test t " +
            "WHERE u.email = ? " +
            "AND t.name = ?";

}
