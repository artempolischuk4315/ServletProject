package ua.polischuk.controller.command;

import org.apache.log4j.Logger;
import ua.polischuk.exception.AddingTestToAvailableException;
import ua.polischuk.model.service.TestService;
import ua.polischuk.model.service.UserService;
import javax.servlet.http.HttpServletRequest;



public class AllowTest implements Command {

    private UserService userService;

    private TestService testService;

    private static final Logger log = Logger.getLogger( AllowTest.class);

    public AllowTest(TestService testService, UserService userService) {
        this.userService = userService;
        this.testService = testService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        String email = request.getParameter("email");
        String testName = request.getParameter("testName");


        if( userService.findByEmail(email).isPresent()&& testService.findTestByName(testName).isPresent()) {
            request.getSession().setAttribute("addedTestToAvailable", true);
        }
        else {
            request.getSession().setAttribute("noSuchTestOrUser", true);
            return "redirect:/admin/allow-test.jsp";
        }

        try {
            userService.addTestToAvailable(email, testName);
        } catch (AddingTestToAvailableException e) {
            log.error("Exception in allow test while adding test ");
            request.getSession().setAttribute("unSuccessFullCreated", true);
            return "redirect:/admin/allow-test.jsp";
        }

        return "redirect:/admin/admin-hello.jsp";
    }


}
