package ua.polischuk.controller.command;

import ua.polischuk.service.TestService;
import ua.polischuk.utility.PrinterPreparerWithPagination;

import javax.servlet.http.HttpServletRequest;

public class ShowAllTests implements Command {
    private TestService testService;

    public ShowAllTests(TestService testService) {
        this.testService = testService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        PrinterPreparerWithPagination preparer = new PrinterPreparerWithPagination();
        preparer.prepareTestsListForPrintingByPages(request, testService);
        return "redirect:/admin/all-tests.jsp";
    }
}
