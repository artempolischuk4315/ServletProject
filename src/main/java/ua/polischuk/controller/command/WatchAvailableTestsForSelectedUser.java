package ua.polischuk.controller.command;

import ua.polischuk.model.entity.Test;
import ua.polischuk.model.service.TestService;
import ua.polischuk.model.service.UserService;
import ua.polischuk.utility.PrinterPreparerWithPagination;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

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

        Set<Test> availableTests = userService.getAvailableTests(email);
        if(availableTests.size()==0){
            request.getSession().setAttribute("noAvailableTests", true);
            return "redirect:/admin/all-users.jsp";
        }
        request.getSession().setAttribute("availableTests", availableTests);

        return "redirect:/admin/available-tests.jsp";
    }
}
