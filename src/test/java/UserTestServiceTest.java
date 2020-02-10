import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.polischuk.model.dao.UserRepository;
import ua.polischuk.model.entity.User;
import ua.polischuk.service.UserInteractionWithTestService;
import ua.polischuk.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserTestServiceTest {

    private static final String USER_EMAIL = "email@gmail.com";
    private static final String INVALID_EMAIL = "invalidmail@gmail.com";
    private static final String TEST_NAME = "test";
    private static final String INVALID_TEST_NAME = "invalid test";

    @InjectMocks
    private UserInteractionWithTestService instance;

    @Mock
    private UserInteractionWithTestService userTestRepository;
    @Mock
    private User user;
    @Mock
    private ua.polischuk.model.entity.Test test;

  /*  @Test
    public void shouldReturnTrueWhenCompletingTestWasSuccessful()  {
        test.setActive(true);
        when(userTestRepository.completeTest(anyString(), anyString(), anyInt())).thenReturn(Boolean.TRUE);

        boolean result = instance.completeTest(USER_EMAIL, TEST_NAME, test.getResult());

        assertThat(result).isTrue();
    }*/

    @Test
    public void shouldReturnFalseWhenCompletingTestWasNotSuccessful() {
        when(userTestRepository.completeTest(INVALID_EMAIL,  INVALID_TEST_NAME, test.getResult())).thenReturn(Boolean.FALSE);

        boolean result = instance.completeTest(USER_EMAIL, INVALID_TEST_NAME, test.getResult());

        assertThat(result).isFalse();
    }
}
