package ua.polischuk.service;

import org.apache.log4j.Logger;
import ua.polischuk.model.dao.DaoFactory;
import ua.polischuk.model.repository.TestRepository;
import ua.polischuk.model.entity.Test;

import java.util.List;
import java.util.Optional;

public class TestService {

    private TestRepository testRepository;
    private static final Logger log = Logger.getLogger( TestService.class);

    public TestService() {
            this.testRepository = DaoFactory.getInstance().createTestDao();
    }

    public Optional<Test> findTestByName(String name)  {
        log.info(LoggerInfo.FINDING_BY_NAME);

        return testRepository.findByName(name);
    }

    public List<Test> findAll(int offset, int recPerPage)  {
        log.info(LoggerInfo.FINDING_ALL);
        return testRepository.findAll(offset, recPerPage);
    }
    public int getNoOfRecords() {
        return testRepository.getNoOfRecords();//считается в том же обращении, что и взятие всех юзеров
    }

    public Optional<Test> saveNewTest (Test test)  {
        log.info(LoggerInfo.SAVING_TEST);

        test.setActive(true);

        boolean result = testRepository.save(test);

        return result ?
                Optional.of(test)
                : Optional.empty();
    }


    public boolean enableOrDisableTest(String nameOfTest, boolean active)  {
        log.info(LoggerInfo.ENABLE_OR_DISABLE_TEST);
        return testRepository.enableOrDisableTest(nameOfTest, active);
    }


}
