package ua.polischuk.controller.command;

import javax.servlet.http.HttpServletRequest;

public class Exception extends Throwable implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        throw new RuntimeException("Generated exception");
    }
}
