package ua.polischuk.controller.command;

import ua.polischuk.service.TestService;
import ua.polischuk.service.UserService;
import ua.polischuk.utility.PrinterPreparerWithPagination;

import javax.servlet.http.HttpServletRequest;

public class GoOnAllowPage implements Command {
    TestService testService;
    UserService userService;


    public GoOnAllowPage(TestService testService, UserService userService) {
        this.testService = testService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {



        PrinterPreparerWithPagination preparer = new PrinterPreparerWithPagination();
       // preparer.setFirstPage(request);
        preparer.prepareUsersListForPrintingByPages(request, userService);


        return "redirect:/admin/allow-test.jsp";
    }




}
