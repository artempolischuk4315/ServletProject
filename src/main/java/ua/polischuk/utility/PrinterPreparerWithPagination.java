package ua.polischuk.utility;

import ua.polischuk.model.entity.Test;
import ua.polischuk.model.entity.User;
import ua.polischuk.service.TestService;
import ua.polischuk.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class PrinterPreparerWithPagination {
    private int currentPage;
    private int recordsPerPage = 5;
    private int nOfPages;


    public void prepareUsersListForPrintingByPages(HttpServletRequest request, UserService userService){


        setCurrentPage(request);
        List<User> allUsers =userService.getAllUsers((currentPage-1)*recordsPerPage,
                recordsPerPage);
        request.getSession().setAttribute("allUsers", allUsers);
        int rows = userService.getNoOfRecords();

        setnOfPages(rows);
        setSessionParams(request, nOfPages, currentPage, recordsPerPage);
    }

    public void prepareTestsListForPrintingByPages(HttpServletRequest request, TestService testService){
        setCurrentPage(request);
        List<Test> allTests = testService.findAll((currentPage-1)*recordsPerPage, recordsPerPage);
        request.getSession().setAttribute("allTests", allTests);
        int rows = testService.getNoOfRecords();
        setnOfPages(rows);
        setSessionParams(request, nOfPages, currentPage, recordsPerPage);

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
