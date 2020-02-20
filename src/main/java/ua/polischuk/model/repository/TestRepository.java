package ua.polischuk.model.repository;

import ua.polischuk.model.entity.Test;

import java.util.Optional;

public interface TestRepository extends GenericRepository<Test> {
    Optional<Test> findByName(String email);
    int getNoOfRecords();
    boolean enableOrDisableTest(String nameOfTest, boolean active);

}
