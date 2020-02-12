package ua.polischuk.controller.command;

import org.apache.log4j.Logger;
import ua.polischuk.model.entity.User;
import ua.polischuk.service.UserService;
import ua.polischuk.utility.PasswordEncrypt;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class Login implements Command {

    private final static String ADMIN_MAIL = "art4315@gmail.com";

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

            if (checkIfNotEmpty(email, pass)) {

                request.getSession().setAttribute("notFullData", true);
                return "/login.jsp";
            }
            String encryptedPass = PasswordEncrypt.EncryptPassword(pass);

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
            if (user.getEmail().equals(ADMIN_MAIL) && user.getPassword().equals(encryptedPass)) {
                setAllParams(request, user, User.ROLE.ADMIN, email);

                return "redirect:/admin/admin-hello.jsp";

            } else if (user.getEmail().equals(email) && user.getPassword().equals(encryptedPass)) {
                setAllParams(request, user, User.ROLE.USER, email);

                return "redirect:/user/user-hello.jsp";

            } else {
                request.getSession().setAttribute("invalidPassword", true);
                return "/login.jsp";//todo  error
        }
    }

    private void setAllParams(HttpServletRequest request, User user, User.ROLE role, String email) {
        CommandUtility.addUserToContext(request, email);
        setAttribute(request, user);
    }

    private void setAttribute (HttpServletRequest req, User user){
        ServletContext context = req.getServletContext();
        context.setAttribute("email", user.getEmail());
        req.getSession().setAttribute("user", user);
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
