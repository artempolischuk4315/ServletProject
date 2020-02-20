package ua.polischuk.controller.command;


import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;

public class CommandUtility {

    public static boolean checkUserIsLogged(HttpServletRequest request, String userName){

        HashSet<String> loggedUsers = (HashSet<String>) request.getServletContext()
                .getAttribute("loggedUsers");

        if(loggedUsers.stream().anyMatch(userName::equals)){
            return true;
        }

        return false;
    }

    public static boolean deleteUserFromContext(HttpServletRequest request, String userName){
        HashSet<String> loggedUsers = (HashSet<String>) request.getServletContext()
                .getAttribute("loggedUsers");

        loggedUsers.remove(userName);
        request.getSession().getServletContext()
                .setAttribute("loggedUsers", loggedUsers);
        return true;
    }

    static void addUserToContext(HttpServletRequest request, String userName) {
        HashSet<String> loggedUsers = (HashSet<String>) request.getServletContext()
                .getAttribute("loggedUsers");
        loggedUsers.add(userName);
        request.getServletContext()
                .setAttribute("loggedUsers", loggedUsers);

    }
}
