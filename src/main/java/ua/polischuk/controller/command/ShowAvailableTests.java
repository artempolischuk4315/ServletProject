package ua.polischuk.controller.command;


import ua.polischuk.model.entity.Test;
import ua.polischuk.service.UserInteractionWithTestService;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public class ShowAvailableTests implements Command{

    private UserInteractionWithTestService userTestService;

    public ShowAvailableTests(UserInteractionWithTestService userTestService) {
        this.userTestService = userTestService;
    }

    @Override
    public String execute(HttpServletRequest request) {
       String currentUserEmail = (String) request.getSession().getAttribute("email");
       String categoryChosenByUser = request.getParameter("category");
       Set<Test> testsByCategory = userTestService.getAvailableTestsByCategory(currentUserEmail, categoryChosenByUser);
       request.getSession().setAttribute("availableTests", testsByCategory);
       request.getSession().setAttribute("currentCategory", categoryChosenByUser); // to normal update if test was completed
       if(testsByCategory.size()==0){
           request.getSession().setAttribute("noTests", true);
       }
       return "redirect:/user/available-tests-for-current-user.jsp";
    }



}
