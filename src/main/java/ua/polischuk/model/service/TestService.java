package ua.polischuk.model.service;

import ua.polischuk.model.dao.DaoFactory;
import ua.polischuk.model.dao.TestRepository;
import ua.polischuk.model.dao.UserRepository;
import ua.polischuk.model.entity.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TestService {

    private TestRepository testRepository;

    public TestService() {
        try{
            this.testRepository = DaoFactory.getInstance().createTestDao();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Test findTestByName(String name) throws Exception {
        Optional<Test> test = testRepository.findByName(name);
        if(test.isPresent()){
            return testRepository.findByName(name).get();
        }else throw new Exception();
    }

    public List<Test> findAll(int offset, int recPerPage)  {
        List<Test> tests = new ArrayList<>();
        try{
             tests = testRepository.findAll(offset, recPerPage);
    }catch (Exception e){
            
        }
        return tests;
    }
    public int getNoOfRecords() {
        return testRepository.getNoOfRecords();//считается в том же обращении, что и взятие всех юзеров
    }

    public void saveNewTest (Test test) throws Exception{
        test.setActive(true);
        testRepository.save(test);
    }

    public void deleteTest (String testName) throws Exception{
        testRepository.delete(testName);
    }

    public void enableOrDisableTest(String nameOfTest, boolean active) throws SQLException {
        testRepository.enableOrDisableTest(nameOfTest, active);
    }


}
