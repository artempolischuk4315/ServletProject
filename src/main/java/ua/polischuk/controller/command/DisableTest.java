package ua.polischuk.controller.command;

import org.apache.log4j.Logger;
import ua.polischuk.service.TestService;

import javax.servlet.http.HttpServletRequest;

public class DisableTest implements Command {
    private TestService testService;
    private static final Logger log = Logger.getLogger( DisableTest.class);

    public DisableTest(TestService testService) {
        this.testService = testService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String testName = request.getParameter("testName");

        if(!testService.enableOrDisableTest(testName, false)) {
            request.getSession().setAttribute("DisablingError", true);
        }
        return new ShowAllTests(testService).execute(request);
    }
}
