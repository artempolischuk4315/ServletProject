package ua.polischuk.controller.command;

import ua.polischuk.model.entity.Category;
import ua.polischuk.model.entity.Test;
import ua.polischuk.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public class ShowAvailableTests implements Command{

    private UserService userService;

    public ShowAvailableTests(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
       String currentUserEmail = (String) request.getSession().getAttribute("email");
       String categoryChosenByUser = request.getParameter("category");
       Set<Test> testsByCategory = userService.getAvailableTestsByCategory(currentUserEmail, categoryChosenByUser);
       request.getSession().setAttribute("availableTests", testsByCategory);
       if(testsByCategory.size()==0){
           request.getSession().setAttribute("noTests", true);
       }
       return "redirect:/user/available-tests-for-current-user.jsp";
    }

    public void updateListOfTests(HttpServletRequest request) {
        String currentUserEmail = (String) request.getSession().getAttribute("email");
        String categoryChosenByUser = request.getParameter("category");
        Set<Test> testsByCategory = userService.getAvailableTestsByCategory(currentUserEmail, categoryChosenByUser);
        request.getSession().setAttribute("availableTests", testsByCategory);
    }



}
