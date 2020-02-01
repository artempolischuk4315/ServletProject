package ua.polischuk.controller.command;

import ua.polischuk.model.entity.User;
import ua.polischuk.model.service.TestService;
import ua.polischuk.model.service.UserService;
import ua.polischuk.utility.PrinterPreparerWithPagination;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GoOnAllowPage implements Command {
    TestService testService;
    UserService userService;

    private int currentPage;
    private int recordsPerPage = 5;
    private int nOfPages;

    public GoOnAllowPage(TestService testService, UserService userService) {
        this.testService = testService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        setCurrentPage(request);
        List<User> users =userService.getAllUsers((currentPage-1)*recordsPerPage,
                recordsPerPage);
        request.getSession().setAttribute("users", users);
        int rows = userService.getNoOfRecords();
        setnOfPages(rows);
        setSessionParams(request, nOfPages, currentPage, recordsPerPage);


        return "redirect:/admin/allow-test.jsp";
    }



    public void setCurrentPage(HttpServletRequest request) {
        String currentPageStr = request.getParameter("currentPage");
        if (currentPageStr != null) {
            request.getSession().setAttribute("currentPage", Integer.valueOf(currentPageStr));
        }

        if(request.getSession().getAttribute("currentPage") != null) {
            currentPage = (int) (request.getSession().getAttribute("currentPage"));
        }
        else currentPage=1;
    }

    public void setnOfPages(int rows) {
        nOfPages = rows  / recordsPerPage;

        if (nOfPages % recordsPerPage > 0) {
            nOfPages++;
        }
    }

    public void setSessionParams(HttpServletRequest request, int nOfPages, int currentPage, int recordsPerPage){
        request.getSession().setAttribute("noOfPages", nOfPages);
        request.getSession().setAttribute("currentPage", currentPage);
        request.getSession().setAttribute("recordsPerPage", recordsPerPage);
    }
}
