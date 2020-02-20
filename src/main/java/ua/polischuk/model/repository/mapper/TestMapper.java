package ua.polischuk.model.repository.mapper;

import ua.polischuk.model.entity.Category;
import ua.polischuk.model.entity.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class TestMapper implements ObjectMapper<Test> {
    @Override
    public Test extractFromResultSet(ResultSet resultSet) throws SQLException {
        Test test = new Test();
        test.setId(resultSet.getInt(1));
        test.setName(resultSet.getString(2));
        test.setNameUa(resultSet.getString(3));
        test.setCategory(Category.getCategoryByString(resultSet.getString(8)));
        test.setDifficulty(resultSet.getInt(5));
        test.setNumberOfQuestions(resultSet.getInt(6));
        test.setTimeLimit(resultSet.getInt(6));
        test.setActive(resultSet.getBoolean(9));
        return test;
    }

    @Override
    public Test makeUnique(Map<Integer, Test> cache, Test test) {
        cache.putIfAbsent(test.getId(), test);
        return cache.get(test.getId());
    }
}
