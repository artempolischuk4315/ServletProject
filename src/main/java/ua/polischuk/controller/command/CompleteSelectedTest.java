package ua.polischuk.controller.command;

import ua.polischuk.model.entity.Test;
import ua.polischuk.model.entity.User;
import ua.polischuk.service.UserInteractionWithTestService;
import ua.polischuk.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

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

        updateListOfTests(request);

        changeSessionAttributes(request, result, currentUser);


        return "redirect:/user/test-over-window.jsp";
    }

    private void changeSessionAttributes(HttpServletRequest request, Integer result, User currentUser) {
        request.getSession().setAttribute("resultOfLastCompletedTest", result);
        request.getSession().setAttribute("stats", String.format("%.2f", currentUser.getStats())+"%");

    }


    public void updateListOfTests(HttpServletRequest request) {
        //refresh list to change number of tests if it will be back button
        String currentUserEmail = (String) request.getSession().getAttribute("email");
        String categoryChosenByUser = (String) request.getSession().getAttribute("currentCategory");
        Set<Test> testsByCategory = userTestService.getAvailableTestsByCategory(currentUserEmail, categoryChosenByUser);
        request.getSession().setAttribute("availableTests", testsByCategory);
    }
}
