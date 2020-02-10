package ua.polischuk.controller.command;

import org.apache.log4j.Logger;
import ua.polischuk.service.TestService;

import javax.servlet.http.HttpServletRequest;

public class EnableTest implements Command {
    TestService testService;
    private static final Logger log = Logger.getLogger( EnableTest.class);

    public EnableTest(TestService testService) {
        this.testService = testService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String testName = request.getParameter("testName");

        if(!testService.enableOrDisableTest(testName, true)) {
            request.getSession().setAttribute("EnablingError", true);
        }

        return new ShowAllTests(testService).execute(request);
    }

}
