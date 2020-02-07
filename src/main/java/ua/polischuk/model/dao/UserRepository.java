package ua.polischuk.model.dao;

import ua.polischuk.model.entity.Test;
import ua.polischuk.model.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends GenericDao<User> {

    Optional<User> findByEmail(String email) ;
    int getNoOfRecords();
    Set<Test> getAvailableTestsSet(String userEmail); //TODO в testRepos
    void addTestToAvailable(String email, String testName) throws SQLException;
    boolean completeTest(String currentUserEmail, Integer completedTestsResult, String testName);
    ArrayList<Test> getCompletedTestsByEmail(String email) throws SQLException;
}
