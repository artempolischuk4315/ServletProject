package ua.polischuk.controller.command;

import ua.polischuk.model.service.TestService;

import javax.servlet.http.HttpServletRequest;

public class DeleteTest implements Command {
    private TestService testService;

    public DeleteTest(TestService testService) {
        this.testService = testService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String testName = request.getParameter("testName");
        try {
            testService.deleteTest(testName);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        return new ShowAllTests(testService).execute(request);
    }
}
