import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.polischuk.exception.AddingTestToAvailableException;
import ua.polischuk.model.dao.UserRepository;
import ua.polischuk.model.entity.User;
import ua.polischuk.model.service.UserService;

import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private static final String USER_EMAIL = "email@gmail.com";
    private static final String INVALID_EMAIL = "invalidmail@gmail.com";
    private static final String TEST_NAME = "test";
    private static final String INVALID_TEST_NAME = "invalid test";
    private static final Integer TEST_RESULT = 100;

    @InjectMocks
    private UserService instance;

    @Mock
    private UserRepository userRepository;
    @Mock
    private User user;
    @Mock
    private ua.polischuk.model.entity.Test test;


    @Before
    public void setUp() {
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(INVALID_EMAIL)).thenReturn(Optional.empty());
        when(user.getEmail()).thenReturn(USER_EMAIL);
        when(test.getResult()).thenReturn(TEST_RESULT);

        user.setEmail(USER_EMAIL);

    }

    @Test
    public void shouldReturnUserWhenSavingSuccess() {

        when(userRepository.save(any(User.class))).thenReturn(Boolean.TRUE);

        Optional<User> result = instance.saveNewUser(user);

        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(user);
    }

    @Test
    public void ShouldReturnEmptyWhenSavingNotSuccess()
    {
        when(userRepository.save(any(User.class))).thenReturn(Boolean.FALSE);
        Optional<User> result = instance.saveNewUser(user);

        assertThat(result).isEmpty();
    }

    @Test
    public void shouldReturnUserWhenUserWithThisEmailExists() {
        Optional<User> result = instance.findByEmail(USER_EMAIL);
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(user);
    }

    @Test
    public void shouldReturnEmptyWhenUserWithThisEmailNotExists() {
        Optional<User> result = instance.findByEmail(INVALID_EMAIL);
        assertThat(result).isEmpty();
    }

    @Test
    public void shouldReturnOkWhenAddingTestToAvailableWasSuccessful()  {

    }


    @Test
    public void shouldReturnTrueWhenCompletingTestWasSuccessful()  {
        when(userRepository.completeTest(anyString(), anyInt(), anyString())).thenReturn(Boolean.TRUE);

        boolean result = instance.completeTest(USER_EMAIL, TEST_NAME, test.getResult());

        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnFalseWhenCompletingTestWasNotSuccessful()  {
        when(userRepository.completeTest(INVALID_EMAIL, test.getResult(), INVALID_TEST_NAME)).thenReturn(Boolean.FALSE);

        boolean result = instance.completeTest(USER_EMAIL, INVALID_TEST_NAME, test.getResult());

        assertThat(result).isFalse();
    }

}
