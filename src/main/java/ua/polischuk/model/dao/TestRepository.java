package ua.polischuk.model.dao;

import ua.polischuk.model.entity.Test;


import java.sql.SQLException;
import java.util.Optional;

public interface TestRepository extends GenericDao<Test> {
    Optional<Test> findByName(String email) throws SQLException;
    int getNoOfRecords();

}
