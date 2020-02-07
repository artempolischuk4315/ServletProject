package ua.polischuk.model.service;

import org.apache.log4j.Logger;
import ua.polischuk.exception.AddingTestToAvailableException;
import ua.polischuk.model.dao.DaoFactory;
import ua.polischuk.model.dao.TestRepository;
import ua.polischuk.model.dao.UserRepository;
import ua.polischuk.model.entity.Category;
import ua.polischuk.model.entity.Test;
import ua.polischuk.model.entity.User;
import ua.polischuk.utility.PasswordEncrypt;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static ua.polischuk.utility.Constants.ADMIN_MAIL;


public class UserService {
    private UserRepository userRepository;
    private TestRepository testRepository;
    private final static int MAX = 100;
    private final static int MIN = 1;
    private static final Logger log = Logger.getLogger( UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserService() {
            this.userRepository = DaoFactory.getInstance().createUserDao();
            this.testRepository = DaoFactory.getInstance().createTestDao();
    }


    public List<User> getAllUsers(int page, int recPerPage) { //TODO TEST
        log.info(LoggerInfo.GETTING_ALL_USERS);
        return userRepository.findAll(page, recPerPage);
    }

    public int getNoOfRecords() {
        return userRepository.getNoOfRecords();//считается в том же обращении, что и взятие всех юзеров
    }

    public Optional<User> findByEmail(String email) {
       log.info(LoggerInfo.FINDING_BY_EMAIL);
        return userRepository.findByEmail(email);

    }


    public boolean setUserParamsAndSave(String fName, String fNameUa, String lName,
                                        String lNameUa, String email, String password)  {

        String encryptedPass = PasswordEncrypt.EncryptPassword(password);//changed

        User user = new User(fName, fNameUa, lName, lNameUa, email, encryptedPass, User.ROLE.USER);

        if (email.equals(ADMIN_MAIL)) {
            user.setRole(User.ROLE.ADMIN);
        }

        setRegistersOfNewUser(user);

        if(saveNewUser(user).isPresent()){
            return true;
        }

        return false;

    }

    public Optional<User> saveNewUser(User user)  {

        boolean result = userRepository.save(user);
        return result ?
                Optional.of(user)
                : Optional.empty();
    }

    private void setRegistersOfNewUser(User user) {

        user.setFirstName(user.getFirstName().substring(0, 1).toUpperCase() +
                user.getFirstName().substring(1).toLowerCase());
        user.setLastName(user.getLastName().substring(0, 1).toUpperCase() +
                user.getLastName().substring(1).toLowerCase());
        user.setFirstNameUa(user.getFirstNameUa().substring(0, 1).toUpperCase() +
                user.getFirstNameUa().substring(1).toLowerCase());
        user.setLastNameUa(user.getLastNameUa().substring(0, 1).toUpperCase() +
                user.getLastNameUa().substring(1).toLowerCase());

    }

    public void addTestToAvailable(String email, String testName) throws AddingTestToAvailableException {//TODO TEST

       try {
           userRepository.addTestToAvailable(email, testName);
       }catch (SQLException e){
           log.error(LoggerInfo.ERROR_ADD_TO_AVAILABLE);
           throw new AddingTestToAvailableException();
       }
    }

    public Set<Test> getAvailableTests(String email) {//TODO TEST
        Set<Test> tests =  userRepository.getAvailableTestsSet(email);

        return tests;
    }

    public Set<Test> getAvailableTestsByCategory(String email, String category){ //TODO TEST

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

        return userRepository.completeTest(email, result, testName);
    }

    public ArrayList<Test> getCompletedTestsByEmail(String email) throws SQLException { //TODO ?
        log.info(LoggerInfo.GET_COMPLETED_TESTS);
        return userRepository.getCompletedTestsByEmail(email);
    }
}