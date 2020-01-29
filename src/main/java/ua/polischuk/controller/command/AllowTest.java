package ua.polischuk.controller.command;

import ua.polischuk.model.service.TestService;
import ua.polischuk.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class AllowTest implements Command {
    private UserService userService;
    private TestService testService;

    public AllowTest(TestService testService, UserService userService) {
        this.userService = userService;
        this.testService = testService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String email = request.getParameter("email");
        String testName = request.getParameter("testName");
        try {
            System.out.println("EXECUTE "+email +" "+testName);
            userService.addTestToAvailable(email, testName);
        } catch (java.lang.Exception e) {
            System.out.println("+++");
            e.printStackTrace();
        }

        return "redirect:/admin/all-users.jsp";
    }
}
