package ua.polischuk.controller.command;

import ua.polischuk.model.entity.Test;
import ua.polischuk.service.UserInteractionWithTestService;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public class WatchAvailableTestsForSelectedUser implements Command {
    private UserInteractionWithTestService userTestService;


    public WatchAvailableTestsForSelectedUser(UserInteractionWithTestService userTestService) {
        this.userTestService = userTestService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        String email = request.getParameter("email");

        Set<Test> availableTests = userTestService.getAvailableTests(email);
        if(availableTests.size()==0){
            request.getSession().setAttribute("noAvailableTests", true);
            return "redirect:/admin/all-users.jsp";
        }
        request.getSession().setAttribute("availableTests", availableTests);

        return "redirect:/admin/available-tests.jsp";
    }
}
