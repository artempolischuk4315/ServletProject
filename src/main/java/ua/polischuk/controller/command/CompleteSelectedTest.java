package ua.polischuk.controller.command;

import ua.polischuk.model.entity.User;
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
        Integer result = userService.setRandomResult(); //в сервис


        User currentUser;
        try {
            userService.completeTest(email, nameOfTest, result);
            currentUser = userService.findByEmail(email);
        } catch (java.lang.Exception e) {
            request.getSession().setAttribute("TestDeleted", true);
            return "redirect:/user/user-hello.jsp";
        }
        new ShowAvailableTests(userService).updateListOfTests(request);
        request.getSession().setAttribute("resultOfLastCompletedTest", result);
        request.getSession().setAttribute("stats", currentUser.getStats());
        //обновляем список, чтобы при возвращении
        // назад поменялось кол-во тестов

        return "redirect:/user/test-over-window.jsp";
    }
}
