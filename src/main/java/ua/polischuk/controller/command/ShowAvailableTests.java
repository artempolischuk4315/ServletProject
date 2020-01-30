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
        System.out.println("CATKA "+ Category.getCategoryByString(categoryChosenByUser));
       Set<Test> testsByCategory = userService.getAvailableTestsByCategory(currentUserEmail, categoryChosenByUser);
        System.out.println("AAAAA "+ testsByCategory.size());
       request.getSession().setAttribute("availableTests", testsByCategory);
       return "redirect:/user/available-tests-for-current-user.jsp";
    }
}
