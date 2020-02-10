package ua.polischuk.controller.command;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.polischuk.controller.command.AllUsersMenu;
import ua.polischuk.controller.command.WatchAvailableTestsForSelectedUser;
import ua.polischuk.model.entity.User;
import ua.polischuk.service.TestService;
import ua.polischuk.service.UserInteractionWithTestService;
import ua.polischuk.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WatchAvailableTestsForSelectedUserTest {

    @InjectMocks
    private WatchAvailableTestsForSelectedUser instance;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private UserInteractionWithTestService userTestService;


    @Test
    public void shouldReturnInAdminMainIfSetIsEmpty() {
        when(request.getParameter("email")).thenReturn("email");
        when(userTestService.getAvailableTests("email")).thenReturn(Collections.emptySet());
        when(request.getSession()).thenReturn(session);

        String result = instance.execute(request);
        assertThat(result).isEqualTo("redirect:/admin/all-users.jsp");
    }

    @Test
    public void shouldReturnInAllUsersMenuIfSetIsNotEmpty() {

        Set<ua.polischuk.model.entity.Test> tests = new HashSet<>();
        tests.add(new ua.polischuk.model.entity.Test());

        when(request.getParameter("email")).thenReturn("email");
        when(userTestService.getAvailableTests("email")).thenReturn(tests);
        when(request.getSession()).thenReturn(session);
        String result = instance.execute(request);

        assertThat(result).isEqualTo("redirect:/admin/available-tests.jsp");
    }
}