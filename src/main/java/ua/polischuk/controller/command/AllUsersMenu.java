package ua.polischuk.controller.command;

import ua.polischuk.model.service.ServiceFactory;
import ua.polischuk.model.service.UserService;
import ua.polischuk.utility.PrinterPreparerWithPagination;
import javax.servlet.http.HttpServletRequest;


public class AllUsersMenu implements Command {
    private UserService userService;

    public AllUsersMenu() {
        this.userService = ServiceFactory.getInstance().getUserService();
    }

    @Override
    public String execute(HttpServletRequest request) {

       /* try {
            System.out.println("COMPLETING TEST");
            userService.completeTest("art4315@gmail.com", "Math1");
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }*/

        PrinterPreparerWithPagination preparer = new PrinterPreparerWithPagination();
        preparer.prepareUsersListForPrintingByPages(request, userService);
        return "redirect:/admin/all-users.jsp";
    }
}
