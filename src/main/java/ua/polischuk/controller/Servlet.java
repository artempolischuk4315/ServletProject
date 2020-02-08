package ua.polischuk.controller;

import ua.polischuk.controller.command.*;
import ua.polischuk.controller.command.Exception;
import ua.polischuk.service.ServiceFactory;
import ua.polischuk.service.TestService;
import ua.polischuk.service.UserInteractionWithTestService;
import ua.polischuk.service.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;



public class Servlet extends HttpServlet {

    private Map<String, Command> commands = new HashMap<>();
    UserService userService =  ServiceFactory.getInstance().getUserService();
    TestService testService =   ServiceFactory.getInstance().getTestService();
    UserInteractionWithTestService userTestService = ServiceFactory.getInstance().getUserTestService();

    public void init(ServletConfig servletConfig){


        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());

        commands.put("logout", new LogOut());
        commands.put("login", new Login(userService));
        commands.put("registration", new Registration(userService));
        commands.put("exception" ,  new Exception());
        commands.put("allUsersMenu", new AllUsersMenu(userService));
        commands.put("createTest", new CreateTest(testService));
        commands.put("allTests", new ShowAllTests(testService));
        commands.put("allowTest", new AllowTest(testService, userService, userTestService));
        commands.put("watchAvailableTestsForSelectedUser", new WatchAvailableTestsForSelectedUser(userTestService));
        commands.put("disableTest", new DisableTest(testService));
        commands.put("enableTest", new EnableTest(testService));
        commands.put("showAvailableTests", new ShowAvailableTests(userTestService));
        commands.put("completeTest", new CompleteSelectedTest(userService, userTestService));
        commands.put("sendMail", new SendMail());
        commands.put("watchCompletedTests", new WatchCompletedTest(userTestService));
        commands.put("goOnAllowPage", new GoOnAllowPage(testService, userService));
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
