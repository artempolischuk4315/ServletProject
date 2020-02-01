package ua.polischuk.controller.command;

import ua.polischuk.model.service.TestService;

import javax.servlet.http.HttpServletRequest;

public class DisableTest implements Command {
    private TestService testService;

    public DisableTest(TestService testService) {
        this.testService = testService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String testName = request.getParameter("testName");
        try {
            testService.enableOrDisableTest(testName, false);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        return new ShowAllTests(testService).execute(request);
    }
}
