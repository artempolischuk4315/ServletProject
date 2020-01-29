package ua.polischuk.controller.command;

import ua.polischuk.model.entity.Category;
import ua.polischuk.model.entity.Test;
import ua.polischuk.model.service.ServiceFactory;
import ua.polischuk.model.service.TestService;

import javax.servlet.http.HttpServletRequest;

import static ua.polischuk.utility.Constants.*;


public class CreateTest implements Command {

    private TestService testService;

    public CreateTest() {
        this.testService = ServiceFactory.getInstance().getTestService();
    }


    @Override
    public String execute(HttpServletRequest request) {

        String name = request.getParameter(TEST_NAME);
        String nameUa = request.getParameter(TEST_NAME_UA);
        Category category = Category.getCategoryByString(request.getParameter(CATEGORY));
        int difficulty = Integer.parseInt(request.getParameter(DIFFICULTY));
        int numberOfQuestions = Integer.parseInt(request.getParameter(NUMB_OF_QUESTIONS));
        int timeLimit = Integer.parseInt(request.getParameter(TIME_LIMIT));

        Test test = new Test(name, nameUa, category, difficulty, numberOfQuestions, timeLimit);


        try {
            testService.saveNewTest(test);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }

        return "redirect:/admin/admin-hello.jsp";
    }
}
