package ua.polischuk.controller.command;
import ua.polischuk.model.entity.Test;
import ua.polischuk.model.service.UserService;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WatchCompletedTest implements Command{

    UserService userService;
    public WatchCompletedTest(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        String email = (String) request.getSession().getAttribute("email");
        List<Test> completedTests = new ArrayList<>();

        try {
           completedTests = userService.getCompletedTestsByEmail(email);
        } catch (SQLException e) {
            return "redirect:/user/user-hello.jsp";
        }

        request.getSession().setAttribute("completedTests", completedTests);
        return "redirect:/user/completed-tests.jsp";
    }
}
