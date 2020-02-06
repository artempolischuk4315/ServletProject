package ua.polischuk.model.service;

import org.apache.log4j.Logger;
import ua.polischuk.exception.NoSuchRecordInTableException;
import ua.polischuk.exception.SaveTestException;
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
    private static final Logger log = Logger.getLogger( TestService.class);

    public TestService() {
            this.testRepository = DaoFactory.getInstance().createTestDao();
    }

    public Test findTestByName(String name) throws NoSuchRecordInTableException {
        log.info(LoggerInfo.FINDING_BY_NAME);
        Optional<Test> test = testRepository.findByName(name);
        if(test.isPresent()){
            log.info(LoggerInfo.OPERATION_SUCCESSFUL);
            return testRepository.findByName(name).get();
        }else throw new NoSuchRecordInTableException(); //TODO


    }

    public List<Test> findAll(int offset, int recPerPage)  {
        log.info(LoggerInfo.FINDING_ALL);
        return testRepository.findAll(offset, recPerPage);
    }
    public int getNoOfRecords() {
        return testRepository.getNoOfRecords();//считается в том же обращении, что и взятие всех юзеров
    }

    public void saveNewTest (Test test) throws SaveTestException {
        log.info(LoggerInfo.SAVING_TEST);
        test.setActive(true);
        try {
            testRepository.save(test);
        }catch (SQLException e){
            throw new SaveTestException();
        }
    }


    public void enableOrDisableTest(String nameOfTest, boolean active) throws SQLException {
        log.info(LoggerInfo.ENABLE_OR_DISABLE_TEST);
        testRepository.enableOrDisableTest(nameOfTest, active);
    }


}
