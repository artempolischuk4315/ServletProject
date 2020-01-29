package ua.polischuk.model.service;

import ua.polischuk.model.dao.DaoFactory;
import ua.polischuk.model.dao.TestRepository;
import ua.polischuk.model.dao.UserRepository;
import ua.polischuk.model.entity.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        Test test = testRepository.findByName(name).orElseThrow(Exception::new);
        return test;
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
        testRepository.save(test);
    }

    public void deleteTest (String testName) throws Exception{

        testRepository.delete(testName);
    }


}
