package ua.polischuk.service;

import org.apache.log4j.Logger;
import ua.polischuk.model.repository.RepositoryFactory;
import ua.polischuk.model.repository.UserTestRepository;
import ua.polischuk.model.entity.Category;
import ua.polischuk.model.entity.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

public class UserInteractionWithTestService {

    private final static int MAX = 100;

    private final static int MIN = 1;

    private UserTestRepository userInteractionWithTestRepository;

    private static final Logger log = Logger.getLogger( UserInteractionWithTestService.class);

    public UserInteractionWithTestService() {
        this.userInteractionWithTestRepository = RepositoryFactory.getInstance().createUserAndTestRepos();
    }

    public boolean addTestToAvailable(String email, String testName) {

        try {
            userInteractionWithTestRepository.addTestToAvailableByEmailAndNameOfTest(email, testName);
            return true;
        }catch (SQLException e){
            log.error(LoggerInfo.ERROR_ADD_TO_AVAILABLE);
          return false;
        }
    }

    public Set<Test> getAvailableTests(String email) {

        return userInteractionWithTestRepository.getAvailableTestsSetByEmail(email);
    }

    public Set<Test> getAvailableTestsByCategory(String email, String category){
        log.info(Category.getCategoryByString(category));

        return getAvailableTests(email)
                .stream()
                .filter(test -> test.getCategory().toString().equals(category))
                .filter(test -> test.isActive())
                .collect(Collectors.toSet());
    }


    public int setRandomResult() {
        return (MIN + (int) (Math.random() * MAX));
    }


    public boolean completeTest(String email, String testName, Integer result)  {
        log.info(LoggerInfo.COMPLETING_TEST);
        return userInteractionWithTestRepository.completeTest(email, result, testName);
    }


    public ArrayList<Test> getCompletedTestsByEmail(String email) throws SQLException {
        log.info(LoggerInfo.GET_COMPLETED_TESTS);
        return userInteractionWithTestRepository.getCompletedTestsByEmail(email);
    }
}
