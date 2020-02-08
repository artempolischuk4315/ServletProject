package ua.polischuk.controller.command;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.polischuk.model.entity.User;
import ua.polischuk.service.UserInteractionWithTestService;
import ua.polischuk.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CompleteTest {

    @InjectMocks
    private CompleteSelectedTest instance;

    @Mock
    private UserInteractionWithTestService userTestService;

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    User user;

    @Before
    public void setUp() {
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("email")).thenReturn("email");
        when(request.getParameter("name")).thenReturn("name");

    }

    @Test
    public void shouldReturnTestOverIfTestCompletedCorrect(){
        when(userTestService.completeTest(anyString(), anyString(), anyInt())).thenReturn(Boolean.TRUE);
        when(userService.findByEmail("email")).thenReturn(Optional.of(user));
        String stringResult = instance.execute(request);

        assertThat(stringResult).isEqualTo("redirect:/user/test-over-window.jsp");
    }

    @Test
    public void shouldReturnTestMenuIfTestWasNotCompletedCorrect(){
        when(userTestService.completeTest(anyString(), anyString(), anyInt())).thenReturn(Boolean.FALSE);
        when(userService.findByEmail("email")).thenReturn(Optional.of(user));
        String stringResult = instance.execute(request);

        assertThat(stringResult).isEqualTo("redirect:/user/user-hello.jsp");
    }
}
