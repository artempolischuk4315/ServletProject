import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.polischuk.model.repository.TestRepository;
import ua.polischuk.model.entity.Test;
import ua.polischuk.service.TestService;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestServiceTest {

    private static final String TEST_NAME = "test";
    private static final String INVALID_TEST_NAME = "invalid test";
    private static final int OFFSET = 1;
    private static final int REC_PER_PAGE =5;

    @InjectMocks
    private TestService instance;

    @Mock
    private TestRepository testRepository;

    @Mock
    private Test test;

    @Before
    public void setUp() {
        when(testRepository.findAll(OFFSET, REC_PER_PAGE)).thenReturn(Collections.singletonList(test));
        when(testRepository.findByName(TEST_NAME)).thenReturn(Optional.of(test));
        when(testRepository.findByName(INVALID_TEST_NAME)).thenReturn(Optional.empty());
        when(test.getName()).thenReturn(TEST_NAME);
        when(test.getResult()).thenReturn(100);

    }

    @org.junit.Test
    public void shouldReturnTestWhenSavingSuccess() {

        when(testRepository.save(any(Test.class))).thenReturn(Boolean.TRUE);

        Optional<Test> result = instance.saveNewTest(test);

        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(test);
    }

    @org.junit.Test
    public void ShouldReturnEmptyWhenSavingNotSuccess()
    {
        when(testRepository.save(any(Test.class))).thenReturn(Boolean.FALSE);
        Optional<Test> result = instance.saveNewTest(test);

        assertThat(result).isEmpty();
    }

    @org.junit.Test
    public void shouldReturnUserWhenUserWithThisEmailExists() {
        Optional<Test> result = instance.findTestByName(TEST_NAME);
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(test);
    }

    @org.junit.Test
    public void shouldReturnEmptyWhenUserWithThisEmailNotExists() {
        Optional<Test> result = instance.findTestByName(INVALID_TEST_NAME);
        assertThat(result).isEmpty();
    }

    @org.junit.Test
    public void shouldReturnAllUsersList(){
        when(testRepository.findAll(OFFSET, REC_PER_PAGE)).thenReturn(Collections.singletonList(test));
        List<Test> tests =instance.findAll(OFFSET, REC_PER_PAGE);
        assertThat(tests).isEqualTo(Collections.singletonList(test));
    }

}
