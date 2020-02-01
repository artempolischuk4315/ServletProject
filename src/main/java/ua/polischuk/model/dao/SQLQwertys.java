package ua.polischuk.model.dao;

public interface SQLQwertys {
    String ADD_NEW_USER = "INSERT INTO USER ( firstName, firstNameUa, lastName, lastNameUa, email, password, role )" +
            "VALUES(?,?,?,?,?,?,?)";
    String FIND_BY_EMAIL = "SELECT id, firstName, firstNameUa, lastName, lastNameUa, email, password, role FROM user WHERE email=?";
    String ADD_NEW_TEST = "INSERT INTO test (name, nameUa, category, difficulty, numberOfQuestions, timeLimit, active)" +
            "VALUES(?,?,?,?,?,?,?)";
}
