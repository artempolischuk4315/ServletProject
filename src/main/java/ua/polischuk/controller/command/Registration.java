package ua.polischuk.controller.command;

import ua.polischuk.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static ua.polischuk.utility.Constants.*;

public class Registration implements Command {

    //TODO validation

    public Registration(UserService userService) {
        this.userService = userService;
    }

    UserService userService;

    @Override
    public String execute(HttpServletRequest request) {

        String firstName = request.getParameter(FIRST_NAME);
        String firstNameUa = request.getParameter(FIRST_NAME_UA);
        String lastName = request.getParameter(LAST_NAME);
        String lastNameUa = request.getParameter(LAST_NAME_UA);
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);

        try {
            userService.setUserParamsAndSave(firstName, firstNameUa, lastName, lastNameUa, email, password);
        }catch (RuntimeException e){
            System.out.println("LOLKEK");
            e.printStackTrace();
            return "/registration.jsp";
        }

        return "redirect:/index.jsp";

        //return "/index.jsp";
    }
}
