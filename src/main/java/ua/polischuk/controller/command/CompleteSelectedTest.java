package ua.polischuk.controller.command;

import ua.polischuk.model.entity.User;
import ua.polischuk.model.service.UserInteractionWithTestService;
import ua.polischuk.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class CompleteSelectedTest implements Command {

    UserService userService;

    UserInteractionWithTestService userTestService;

    public CompleteSelectedTest(UserService userService, UserInteractionWithTestService userTestService) {
        this.userService = userService;
        this.userTestService = userTestService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        String email = (String) request.getSession().getAttribute("email");

        String nameOfTest = request.getParameter("name");

        request.getSession().setAttribute("lastCompletedTest", nameOfTest);

        Integer result = userTestService.setRandomResult(); //в сервис

        User currentUser;

        boolean completing = userTestService.completeTest(email, nameOfTest, result);

        if(completing){
            currentUser = userService.findByEmail(email).get();
        } else  {
            request.getSession().setAttribute("TestDeleted", true);
            return "redirect:/user/user-hello.jsp";
        }
        new ShowAvailableTests(userTestService).updateListOfTests(request);
        request.getSession().setAttribute("resultOfLastCompletedTest", result);
        request.getSession().setAttribute("stats", currentUser.getStats());
        //обновляем список, чтобы при возвращении
        // назад поменялось кол-во тестов

        return "redirect:/user/test-over-window.jsp";
    }
}
