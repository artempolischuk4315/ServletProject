package ua.polischuk.model.dao;

import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T> extends AutoCloseable {
    void save(T entity) throws SQLException;
    T findById(int id);
    List<T> findAll(int offset, int recPerPage);
    void update(T entity);
    void delete(String name) throws SQLException;
    void close();
}
