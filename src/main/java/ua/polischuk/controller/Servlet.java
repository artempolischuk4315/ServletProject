package ua.polischuk.controller;

import ua.polischuk.controller.command.*;
import ua.polischuk.controller.command.Exception;
import ua.polischuk.model.dao.DaoFactory;
import ua.polischuk.model.dao.UserRepository;
import ua.polischuk.model.dao.impl.JDBCUserDao;
import ua.polischuk.model.service.ServiceFactory;
import ua.polischuk.model.service.TestService;
import ua.polischuk.model.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;



public class Servlet extends HttpServlet {

    private Map<String, Command> commands = new HashMap<>();
    UserService userService =  ServiceFactory.getInstance().getUserService();
    TestService testService =   ServiceFactory.getInstance().getTestService();

    public void init(ServletConfig servletConfig){


        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());


        commands.put("logout", new LogOut());
        commands.put("login", new Login(userService));
        commands.put("registration", new Registration(userService));
        commands.put("exception" ,  new Exception());
        commands.put("allUsersMenu", new AllUsersMenu());
        commands.put("createTest", new CreateTest());
        commands.put("allTests", new ShowAllTests(testService));
        commands.put("allowTest", new AllowTest(testService, userService));
        commands.put("watchAvailableTestsForSelectedUser", new WatchAvailableTestsForSelectedUser(userService, testService));
        commands.put("deleteTest", new DeleteTest(testService));
        commands.put("showAvailableTests", new ShowAvailableTests(userService));
        commands.put("completeTest", new CompleteSelectedTest(userService));
        commands.put("sendMail", new SendMail());

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }


    private void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        System.out.println(path);
        path = path.replaceAll(".*/api/" , "");
        System.out.println(path);
              Command command = commands.getOrDefault(path ,
                (r)->"/index.jsp)");

        System.out.println("Current command: "+command.getClass().getName());
        String page = command.execute(request);
        if(page.contains("redirect")){
            response.sendRedirect(page.replace("redirect:", "/api"));
        }else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }



}
