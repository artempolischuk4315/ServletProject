package ua.polischuk.controller.command;

import ua.polischuk.service.UserService;
import ua.polischuk.utility.PrinterPreparerWithPagination;
import javax.servlet.http.HttpServletRequest;



public class AllUsersMenu implements Command {
    private UserService userService;



    public AllUsersMenu(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        request.getSession().removeAttribute("currentPage");// on this command always from first page
        PrinterPreparerWithPagination preparer = new PrinterPreparerWithPagination();
        preparer.prepareUsersListForPrintingByPages(request, userService);
        return "redirect:/admin/all-users.jsp";
    }
}
