package ua.polischuk.controller.command;

import javax.servlet.http.HttpServletRequest;

public class GoOnMainPage implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "redirect:/user/user-hello.jsp";
    }
}
