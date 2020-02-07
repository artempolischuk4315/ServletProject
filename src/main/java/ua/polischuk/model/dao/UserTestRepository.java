package ua.polischuk.model.dao;

import ua.polischuk.model.entity.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

public interface UserTestRepository {
    Set<Test> getAvailableTestsSetByEmail(String userEmail);
    void addTestToAvailableByEmailAndNameOfTest(String email, String testName) throws SQLException;
    boolean completeTest(String currentUserEmail, Integer completedTestsResult, String testName);
    ArrayList<Test> getCompletedTestsByEmail(String email) throws SQLException;

}
