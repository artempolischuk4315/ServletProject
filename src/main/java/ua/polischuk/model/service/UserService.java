package ua.polischuk.model.service;

import org.apache.log4j.Logger;
import ua.polischuk.model.dao.DaoFactory;
import ua.polischuk.model.dao.TestRepository;
import ua.polischuk.model.dao.UserRepository;
import ua.polischuk.model.entity.Category;
import ua.polischuk.model.entity.Test;
import ua.polischuk.model.entity.User;
import ua.polischuk.utility.PasswordEncrypt;


import java.security.NoSuchAlgorithmException;
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


    public List<User> getAllUsers(int page, int recPerPage) {
        log.info(LoggerInfo.GETTING_ALL_USERS);
        List<User> users = new ArrayList<>();
        try {
            users = userRepository.findAll(page, recPerPage);
        } catch (SQLException e) {
            e.printStackTrace();//TODO
        }
        return users;
    }

    public int getNoOfRecords() {
        return userRepository.getNoOfRecords();//считается в том же обращении, что и взятие всех юзеров
    }

    public User findByEmail(String email) throws Exception {
       log.info(LoggerInfo.FINDING_BY_EMAIL);
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            log.info(LoggerInfo.USER_IS_PRESENT);
            return userRepository.findByEmail(email).get();
        }
       else throw new Exception();//TODO
    }


    public boolean setUserParamsAndSave(String fName, String fNameUa, String lName,
                                        String lNameUa, String email, String password) {

        PasswordEncrypt encryptor = new PasswordEncrypt();
        String encryptedPass = null; //в отедльный метод
        try {
            encryptedPass = encryptor.EncryptPassword(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();// TODO
        }
        User user = new User(fName, fNameUa, lName, lNameUa, email, encryptedPass, User.ROLE.USER);
        if (email.equals(ADMIN_MAIL)) {
            user.setRole(User.ROLE.ADMIN);
        }

        setRegistersOfNewUser(user);
        try {
            saveNewUser(user);
        } catch (SQLException e) {
            log.info(LoggerInfo.ERROR_SAVING_USER +user.getEmail());
            throw new RuntimeException();//TODO
        }
        return false;
    }

    public void saveNewUser(User user) throws SQLException {
        setRegistersOfNewUser(user);
        userRepository.save(user);

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


    public void addTestToAvailable(String email, String testName) throws Exception {

       try {
           userRepository.addTestToAvailable(email, testName);
       }catch (SQLException e){
           log.error(LoggerInfo.ERROR_ADD_TO_AVAILABLE);
           throw new Exception();//TODO
       }
    }


    public Set<Test> getAvailableTests(String email) {
        Set<Test> tests = new HashSet<>();

        try {
            tests = userRepository.getAvailableTestsSet(email);
        } catch (SQLException e) {
            log.error(LoggerInfo.ERROR_GETTING_AVAILABLE_TESTS);
        }
        return tests;
    }

    public Set<Test> getAvailableTestsByCategory(String email, String category){

        log.info("Category " +Category.getCategoryByString(category));
        return getAvailableTests(email)
                .stream()
                .filter(test -> test.getCategory().toString().equals(category))
                .filter(test -> test.isActive())
                .collect(Collectors.toSet());

    }


    public int setRandomResult() {
        return (MIN + (int) (Math.random() * MAX));
    }

    public void completeTest(String email, String testName, Integer result) throws Exception {
        log.info(LoggerInfo.COMPLETING_TEST);
        userRepository.completeTest(email, result, testName);
    }

    public ArrayList<Test> getCompletedTestsByEmail(String email) throws SQLException {
        log.info(LoggerInfo.GET_COMPLETED_TESTS);
        return userRepository.getCompletedTestsByEmail(email);
    }

}