package ua.polischuk.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.polischuk.model.entity.User;
import ua.polischuk.service.TestService;
import ua.polischuk.service.UserInteractionWithTestService;
import ua.polischuk.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AllowTestForUserTest {

    @InjectMocks
    private AllowTest instance;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private UserInteractionWithTestService userTestService;

    @Mock
    private UserService userService;

    @Mock
    private TestService testService;

    @Mock
    ua.polischuk.model.entity.Test test;

    @Mock
    User user;

    @Before
    public void setUp(){
        when(request.getParameter("email")).thenReturn("email");
        when(request.getParameter("testName")).thenReturn("name");
        when( request.getSession()).thenReturn(session);
    }

    @Test
    public void shouldStayOnMenuAttributeIfNoSuchUser(){

        when(userService.findByEmail("email")).thenReturn(Optional.empty());
        when(testService.findTestByName("test")).thenReturn(Optional.of(test));


        String stringResult = instance.execute(request);

        assertThat(stringResult).isEqualTo("redirect:/admin/allow-test.jsp");

    }

    @Test
    public void shouldStayOnMenuIfNoSuchTest(){

        when(userService.findByEmail("email")).thenReturn(Optional.of(user));
        when(testService.findTestByName("name")).thenReturn(Optional.empty());


        String stringResult = instance.execute(request);

        assertThat(stringResult).isEqualTo("redirect:/admin/allow-test.jsp");
    }

    @Test
    public void shouldStayOnMenuIfTestAlreadyAdded()  {
        when(userService.findByEmail("email")).thenReturn(Optional.of(user));
        when(testService.findTestByName("name")).thenReturn(Optional.of(test));

        when(userTestService.addTestToAvailable("email", "name")).thenReturn(Boolean.FALSE);
        String stringResult = instance.execute(request);

        assertThat(stringResult).isEqualTo("redirect:/admin/allow-test.jsp");
    }

    @Test
    public void shouldGoOnMainIfTestAddedSuccessfully()  {
        when(userService.findByEmail("email")).thenReturn(Optional.of(user));
        when(testService.findTestByName("name")).thenReturn(Optional.of(test));

        when(userTestService.addTestToAvailable("email", "name")).thenReturn(Boolean.TRUE);
        String stringResult = instance.execute(request);

        assertThat(stringResult).isEqualTo("redirect:/admin/admin-hello.jsp");
    }
}
