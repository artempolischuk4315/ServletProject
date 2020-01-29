package ua.polischuk.controller.command;

import javax.servlet.http.HttpServletRequest;

public class LogOut implements Command {
    @Override
    public String execute(HttpServletRequest request) {

        String email = String.valueOf(request.getSession().getAttribute("email"));
        CommandUtility.deleteUserFromContext(request, email);
        request.getSession().invalidate();
        return "redirect:/";
    }
}
