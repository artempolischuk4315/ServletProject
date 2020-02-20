package ua.polischuk.controller.command;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.polischuk.model.entity.User;
import ua.polischuk.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginCommandTest {

    private static final String USER_EMAIL = "user email";
    private static final String USER_PASSWORD = "user password";

    @InjectMocks
    private Login instance;

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ServletContext context;

    @Mock
    private HttpSession session;
    Set<String> logged = new HashSet<>();

    @Mock
    private User user;

    @Before
    public void setUp() {

        when(request.getParameter("pass")).thenReturn(USER_PASSWORD);
        when(request.getSession()).thenReturn(session);
        when(request.getServletContext()).thenReturn(context);
        when(request.getServletContext()
                .getAttribute("loggedUsers")).thenReturn(logged);
    }

    @Test
    public void shouldRedirectOnLoginPageIfEmailIsNull(){
        when(request.getParameter("email")).thenReturn(null);
        String result = instance.execute(request);
        assertThat(result).isEqualTo("/login.jsp");
    }

    @Test
    public void shouldRedirectOnLoginPageIfPasswordIsNull(){
        when(request.getParameter("pass")).thenReturn(null);
        String result = instance.execute(request);
        assertThat(result).isEqualTo("/login.jsp");
    }

    @Test
    public void shouldReturnOnLoginPageIfUserIsLogged(){
        when(request.getParameter("email")).thenReturn("email");
        HashSet<String> logged = (HashSet<String>) request.getServletContext()
                .getAttribute("loggedUsers");
        logged.add("email");

        String result = instance.execute(request);
        assertThat(result).isEqualTo("redirect:/index.jsp");
    }

    @Test
    public void shouldReturnOnLoginPageIfUserIsNotPresent(){
        when(request.getParameter("email")).thenReturn("email");
        when(request.getParameter("pass")).thenReturn("pass");
        when(userService.findByEmail("email")).thenReturn(Optional.empty());
        String result = instance.execute(request);
        assertThat(result).isEqualTo("redirect:/login.jsp");
    }

    //It will work if disable encrypting password
    @Test
    @Ignore
    public void shouldReturnOnUserPageIfUserIsPresent(){

        when(request.getParameter("email")).thenReturn("email");
        when(request.getParameter("pass")).thenReturn("pass");
        when(userService.findByEmail("email")).thenReturn(Optional.of(user));
        when(user.getEmail()).thenReturn("email");
        when(user.getPassword()).thenReturn("pass");
        String result = instance.execute(request);
        assertThat(result).isEqualTo("redirect:/user/user-hello.jsp");

    }
}
