package ua.polischuk.controller.command;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.polischuk.controller.command.AllUsersMenu;
import ua.polischuk.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AllUsersMenuTest {

    @InjectMocks
    private AllUsersMenu instance;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private UserService userService;

    @Test
    public void  shouldReturnAllUsersMenu(){
        when(request.getParameter("currentPage")).thenReturn("1");
        when(request.getSession()).thenReturn(session);
        when(userService.getAllUsers(anyInt(), anyInt())).thenReturn(Collections.emptyList());

        String result = instance.execute(request);
        assertThat(result).isEqualTo("redirect:/admin/all-users.jsp");
    }
}
