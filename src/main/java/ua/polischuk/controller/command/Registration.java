package ua.polischuk.controller.command;


import ua.polischuk.service.UserService;
import ua.polischuk.utility.Validator;

import javax.servlet.http.HttpServletRequest;



import static ua.polischuk.utility.Constants.*;

public class Registration implements Command {

    UserService userService;
    public Registration(UserService userService) {
        this.userService = userService;
    }



    @Override
    public String execute(HttpServletRequest request) {

        String firstName = request.getParameter(FIRST_NAME);
        String firstNameUa = request.getParameter(FIRST_NAME_UA);
        String lastName = request.getParameter(LAST_NAME);
        String lastNameUa = request.getParameter(LAST_NAME_UA);
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);

        Validator validator = new Validator();
        if(!validator.validateUser(firstName, firstNameUa, lastName, lastNameUa, email)){
            request.getSession().setAttribute("notValidData", true);
            return "redirect:/registration.jsp";
        }

        if( userService.setUserParamsAndSave(firstName, firstNameUa, lastName, lastNameUa, email, password)) {
            request.getSession().setAttribute("successRegistr", true);
            return "redirect:/login.jsp";
        }

            request.getSession().setAttribute("userAlreadyExists", true);
            return "redirect:/registration.jsp";



    }
}
