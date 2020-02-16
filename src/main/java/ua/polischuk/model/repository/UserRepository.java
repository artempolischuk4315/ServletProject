package ua.polischuk.model.repository;

import ua.polischuk.model.entity.User;

import java.util.Optional;

public interface UserRepository extends GenericDao<User> {

    Optional<User> findByEmail(String email) ;
    int getNoOfRecords();

}
