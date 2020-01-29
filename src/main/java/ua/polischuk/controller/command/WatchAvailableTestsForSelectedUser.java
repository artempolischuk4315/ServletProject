package ua.polischuk.controller.command;

import ua.polischuk.model.service.TestService;
import ua.polischuk.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class WatchAvailableTestsForSelectedUser implements Command {
    private UserService userService;
    private TestService testService;

    public WatchAvailableTestsForSelectedUser(UserService userService, TestService testService) {
        this.userService = userService;
        this.testService = testService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String email = request.getParameter("email");
        request.getSession().setAttribute("availableTests", userService.getAvailableTests(email));

        return "redirect:/admin/available-tests.jsp";
    }
}
