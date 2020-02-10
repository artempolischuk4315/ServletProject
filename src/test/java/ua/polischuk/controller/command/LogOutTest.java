package ua.polischuk.controller.command;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.polischuk.controller.command.CommandUtility;
import ua.polischuk.controller.command.LogOut;
import ua.polischuk.controller.command.Login;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class LogOutTest {

    @InjectMocks
    private LogOut instance;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private ServletContext context;

    @Test
    public void shouldRedirectOnIndexPage(){
        Set<String> logged = new HashSet<>();
        logged.add("email");

        when(request.getSession()).thenReturn(session);
        when(request.getServletContext()).thenReturn(context);
        when(request.getSession().getServletContext()).thenReturn(context);
        when(request.getServletContext().getAttribute("loggedUsers")).thenReturn(logged);
        when(request.getSession().getAttribute("email")).thenReturn("email");

        String result = instance.execute(request);
        assertThat(result).isEqualTo("redirect:/");
    }
}
