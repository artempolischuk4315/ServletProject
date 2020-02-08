package ua.polischuk.controller.command;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ua.polischuk.controller.command.ShowAllTests;
import ua.polischuk.service.TestService;
import ua.polischuk.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShowAllTestsMenuTest {

    @InjectMocks
    private ShowAllTests instance;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private TestService testService;

    @Test
    public void  execute(){
        when(request.getParameter("currentPage")).thenReturn("1");
        when(request.getSession()).thenReturn(session);
        when(testService.findAll(anyInt(), anyInt())).thenReturn(Collections.emptyList());

        String result = instance.execute(request);
        assertThat(result).isEqualTo("redirect:/admin/all-tests.jsp");
    }
}
