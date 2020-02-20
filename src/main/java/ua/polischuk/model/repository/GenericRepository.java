package ua.polischuk.model.repository;

import java.sql.SQLException;
import java.util.List;

public interface GenericRepository<T> extends AutoCloseable {
    boolean save(T entity) ;
    T findById(int id);
    List<T> findAll(int offset, int recordsPerPage);
    void update(T entity);
    void delete(String name) throws SQLException;
    void close();
}
