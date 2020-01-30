package ua.polischuk.controller.command;

import ua.polischuk.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class CompleteSelectedTest implements Command {

    UserService userService;

    public CompleteSelectedTest(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        String nameOfTest = request.getParameter("name");
        request.getSession().setAttribute("lastCompletedTest", nameOfTest);
        Integer result = userService.setRandomResult(); //нужно считать тут, чтобы добавить в сессию ?
        request.getSession().setAttribute("resultOfLastCompletedTest", result);
        try {
            userService.completeTest(email, nameOfTest, result);
        } catch (java.lang.Exception e) {
            return "redirect:/user/available-tests-for-current-user.jsp";
        }
        return "redirect:/user/test-over-window.jsp";
    }
}
