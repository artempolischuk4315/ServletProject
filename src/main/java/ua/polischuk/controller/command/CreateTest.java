package ua.polischuk.controller.command;

import ua.polischuk.model.entity.Category;
import ua.polischuk.model.entity.Test;
import ua.polischuk.model.service.ServiceFactory;
import ua.polischuk.model.service.TestService;
import ua.polischuk.utility.Validator;

import javax.servlet.http.HttpServletRequest;

import static ua.polischuk.utility.Constants.*;


public class CreateTest implements Command {

    private TestService testService;

    public CreateTest(TestService testService) {
        this.testService = testService;
    }


    @Override
    public String execute(HttpServletRequest request) {

        String name = request.getParameter(TEST_NAME);
        String nameUa = request.getParameter(TEST_NAME_UA);
        Category category = Category.getCategoryByString(request.getParameter(CATEGORY));
        int difficulty = Integer.parseInt(request.getParameter(DIFFICULTY));
        int numberOfQuestions = Integer.parseInt(request.getParameter(NUMB_OF_QUESTIONS));
        int timeLimit = Integer.parseInt(request.getParameter(TIME_LIMIT));

        System.out.println("HERE I AM");
        System.out.println(String.valueOf(category));
        Test test = new Test(name, nameUa, category, difficulty, numberOfQuestions, timeLimit);
        Validator validator = new Validator();
        if(!validator.validateTest(name, nameUa, category, difficulty, numberOfQuestions, timeLimit)){
            return "redirect:/admin/add-test.jsp";
        }

        try {
            testService.saveNewTest(test);
            request.getSession().setAttribute("createdTest", true);
        } catch (java.lang.Exception e) {
            request.getSession().setAttribute("notCreatedTest", true);
            return "redirect:/admin/add-test.jsp";
        }

        return "redirect:/admin/admin-hello.jsp";
    }
}
