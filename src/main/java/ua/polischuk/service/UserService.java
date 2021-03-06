package ua.polischuk.service;

import org.apache.log4j.Logger;
import ua.polischuk.model.repository.RepositoryFactory;
import ua.polischuk.model.repository.UserRepository;
import ua.polischuk.model.entity.User;
import ua.polischuk.utility.PasswordEncrypt;
import java.util.*;


import static ua.polischuk.utility.Constants.ADMIN_MAIL;


public class UserService {
    private UserRepository userRepository;

    private static final Logger log = Logger.getLogger( UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserService() {
            this.userRepository = RepositoryFactory.getInstance().createUserRepos();
    }


    public List<User> getAllUsers(int page, int recPerPage) {
        log.info(LoggerInfo.GETTING_ALL_USERS);
        return userRepository.findAll(page, recPerPage);
    }

    public int getNoOfRecords() {
        return userRepository.getNoOfRecords();
    }

    public Optional<User> findByEmail(String email) {
       log.info(LoggerInfo.FINDING_BY_EMAIL);
        return userRepository.findByEmail(email);

    }


    public boolean setUserParamsAndSave(String fName, String fNameUa, String lName,
                                        String lNameUa, String email, String password)  {

        String encryptedPass = PasswordEncrypt.encryptPassword(password);

        User user = new User(fName, fNameUa, lName, lNameUa, email, encryptedPass, User.ROLE.USER);

        if (email.equals(ADMIN_MAIL)) {
            user.setRole(User.ROLE.ADMIN);
        }

        setRegistersOfNewUser(user);

        return saveNewUser(user).isPresent();

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

}