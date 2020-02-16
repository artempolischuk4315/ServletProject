import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.polischuk.model.repository.UserRepository;
import ua.polischuk.model.entity.User;
import ua.polischuk.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private static final String USER_EMAIL = "email@gmail.com";
    private static final String INVALID_EMAIL = "invalidmail@gmail.com";
    private static final String TEST_NAME = "test";
    private static final String INVALID_TEST_NAME = "invalid test";
    private static final Integer TEST_RESULT = 100;
    private static final int OFFSET = 1;
    private static final int REC_PER_PAGE =5;

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
        when(userRepository.findAll(OFFSET, REC_PER_PAGE)).thenReturn(Collections.singletonList(user));
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
    public void shouldReturnTrueForGlobalSave(){
        when(userRepository.save(any(User.class))).thenReturn(Boolean.TRUE);
        boolean result =  instance.setUserParamsAndSave("name", "name",
                "name", "name", "fffy@fff","pass");

         assertThat(result).isEqualTo(true);
    }

    @Test
    public void shouldReturnFalseForGlobalSave(){
        when(userRepository.save(any(User.class))).thenReturn(Boolean.FALSE);
        boolean result =  instance.setUserParamsAndSave("name", "name",
                "name", "name", "fffy@fff","pass");

        assertThat(result).isEqualTo(false);
    }

    @Test
    public void shouldReturnAllUsersList(){
        when(userRepository.findAll(OFFSET, REC_PER_PAGE)).thenReturn(Collections.singletonList(user));
        List<User> users =instance.getAllUsers(OFFSET, REC_PER_PAGE);
        assertThat(users).isEqualTo(Collections.singletonList(user));
    }

   /* @Test
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
    }*/

}
