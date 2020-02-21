package ua.polischuk.controller.command;

import org.apache.log4j.Logger;
import ua.polischuk.model.entity.User;
import ua.polischuk.service.UserService;
import ua.polischuk.utility.PasswordEncrypt;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class Login implements Command {


    private UserService userService;

    private static final Logger log = Logger.getLogger( DisableTest.class);

    public Login(UserService userService) {
            this.userService = userService;
    }

        @Override
        public String execute (HttpServletRequest request){
            User user = null;
            String email = request.getParameter("email");
            String pass = request.getParameter("pass");

            log.info("LOGIN");

            if (checkIfNotEmpty(email, pass)) {

                request.getSession().setAttribute("notFullData", true);
                return "/login.jsp";
            }
            String encryptedPass = PasswordEncrypt.encryptPassword(pass);

            if (CommandUtility.checkUserIsLogged(request, email)) {

                request.getSession().setAttribute("alreadyLogged", true);
                return "redirect:/index.jsp";
            };

            Optional<User> userOptional = userService.findByEmail(email);
            if(!userOptional.isPresent()){
                request.getSession().setAttribute("invalidEmail", true);
                return "redirect:/login.jsp";
            }

            user = userOptional.get();
            return redirectByRoleIfPasswordCorrect(request, user, email, encryptedPass);
        }


        private String redirectByRoleIfPasswordCorrect(HttpServletRequest request, User user, String email, String encryptedPass) {
            if (user.getRole().toString().equals("ADMIN") && user.getPassword().equals(encryptedPass)) {
                setAllParams(request, user, email);

                return "redirect:/admin/admin-hello.jsp";

            } else if (user.getEmail().equals(email) && user.getPassword().equals(encryptedPass)) {
                setAllParams(request, user, email);

                return "redirect:/user/user-hello.jsp";

            } else {
                request.getSession().setAttribute("invalidPassword", true);
                return "/login.jsp";//todo  error
        }
    }

    private void setAllParams(HttpServletRequest request, User user, String email) {
        CommandUtility.deleteUserFromContext(request, (String) request.getSession().getAttribute("email"));
        //deleting from context user that can be logged now
        deleteOldAttributes(request);

        CommandUtility.addUserToContext(request, email);
        setNewAttributes(request, user);
    }

    private void deleteOldAttributes(HttpServletRequest req){
        req.getSession().removeAttribute("role");
        req.getSession().removeAttribute("email");
        req.getSession().removeAttribute("stats");
    }

    private void setNewAttributes (HttpServletRequest req, User user){

        req.getSession().setAttribute("role", user.getRole());
        req.getSession().setAttribute("email", user.getEmail());
        req.getSession().setAttribute("stats",  String.format("%.2f", user.getStats())+"%");
    }

    private boolean checkIfNotEmpty(String email, String pass) {
        if (email == null || email.equals("") || pass == null || pass.equals("")) {
            return true;
        }
        return false;
    }

}
