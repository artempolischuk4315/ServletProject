package ua.polischuk.controller.command;


import ua.polischuk.model.entity.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

public class CommandUtility {
    static void setUserRole(HttpServletRequest request,
                            User.ROLE role, String email) {
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        context.setAttribute("email", email);
        session.setAttribute("role", role);
        session.setAttribute("email", email);
    }

    static boolean checkUserIsLogged(HttpServletRequest request, String userName){

        HashSet<String> loggedUsers = (HashSet<String>) request.getServletContext()
                .getAttribute("loggedUsers");
        System.out.println("Logged: "+loggedUsers);
        System.out.println(userName);
        if(loggedUsers.stream().anyMatch(userName::equals)){
            return true;
        }
        System.out.println("COMMAND UT");
       /* loggedUsers.add(userName);
        request.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers);*/
        return false;
    }

    public static boolean deleteUserFromContext(HttpServletRequest request, String userName){
        HashSet<String> loggedUsers = (HashSet<String>) request.getServletContext()
                .getAttribute("loggedUsers");

        System.out.println("delete userName " + userName);
        System.out.println("COMMAND UT2");
        loggedUsers.remove(userName);
        request.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
        return true;
    }

    static void addUserToContext(HttpServletRequest request, String userName) {
        HashSet<String> loggedUsers = (HashSet<String>) request.getServletContext()
                .getAttribute("loggedUsers");
        System.out.println("COMMAND UT1");
        loggedUsers.add(userName);
        request.getServletContext()
                .setAttribute("loggedUsers", loggedUsers);

    }
}
