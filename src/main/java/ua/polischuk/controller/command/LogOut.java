package ua.polischuk.controller.command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class LogOut implements Command {
    private static final Logger log = Logger.getLogger( DisableTest.class);


    @Override
    public String execute(HttpServletRequest request) {

        String email = String.valueOf(request.getSession().getAttribute("email"));
        CommandUtility.deleteUserFromContext(request, email);
        request.getSession().invalidate();
        request.getSession().setAttribute("logout", true);
        log.info("LOGOUT");
        return "redirect:/";
    }
}
