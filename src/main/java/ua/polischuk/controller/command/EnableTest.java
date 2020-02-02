package ua.polischuk.controller.command;

import org.apache.log4j.Logger;
import ua.polischuk.model.service.TestService;
import ua.polischuk.model.service.UserService;

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
        try {
            testService.enableOrDisableTest(testName, true);
           // request.getSession().setAttribute("IsAble", "Yes");
        } catch (java.lang.Exception e) {
            log.error("Exception in enabling test");
        }
        return new ShowAllTests(testService).execute(request);
    }

}
