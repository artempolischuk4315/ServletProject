package ua.polischuk.controller.command;

import org.apache.log4j.Logger;
import ua.polischuk.exception.NoSuchRecordInTableException;
import ua.polischuk.model.entity.User;
import ua.polischuk.model.service.ServiceFactory;
import ua.polischuk.model.service.UserService;
import ua.polischuk.utility.PasswordEncrypt;
import ua.polischuk.utility.PasswordValidator;
import ua.polischuk.utility.Validator;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;


public class Login implements Command {
    private final static String ADMIN_MAIL = "art4315@gmail.com";
    private UserService userService;
    private static final Logger log = Logger.getLogger( DisableTest.class);

    public Login(UserService userService) {
            this.userService = userService;
    }
        @Override
        public String execute (HttpServletRequest request){
            User user;
            String email = request.getParameter("email");
            String pass = request.getParameter("pass");
            String encryptedPass = getEncryptedPassword(pass);

            if (checkIfNotEmpty(email, pass)) return "/login.jsp";

            if (CommandUtility.checkUserIsLogged(request, email)) return "redirect:/index.jsp";

            try {
                user = userService.findByEmail(email);
            } catch (NoSuchRecordInTableException e) {
                log.error("Error while logging");
                return "redirect:/login.jsp";
            }


            if (user.getEmail().equals(ADMIN_MAIL) && user.getPassword().equals(encryptedPass)) {
                setAllParams(request, user, User.ROLE.ADMIN, email);
                return "redirect:/admin/admin-hello.jsp";
            } else if (user.getEmail().equals(email) && user.getPassword().equals(encryptedPass)) {
                setAllParams(request, user, User.ROLE.USER, email);
                return "redirect:/user/user-hello.jsp";
            } else {
                return "/login.jsp";//todo  error
            }

        }

    private void setAllParams(HttpServletRequest request, User user, User.ROLE role, String email) {
        CommandUtility.setUserRole(request, role, email);
        CommandUtility.addUserToContext(request, email);
        setAttribute(request, user);
    }


    private boolean checkIfNotEmpty(String email, String pass) {
        if (email == null || email.equals("") || pass == null || pass.equals("")) {
            return true;
        }
        return false;
    }

    private String getEncryptedPassword(String pass) {

        PasswordEncrypt encryptor = new PasswordEncrypt();
        String encryptedPass = null;

        try {
            encryptedPass = encryptor.EncryptPassword(pass);
        } catch (NoSuchAlgorithmException e) {
           log.error("Error encrypting password");
        }
        return encryptedPass;
    }


    private void setAttribute (HttpServletRequest req, User user){
            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("role", user.getRole());
            req.getSession().setAttribute("email", user.getEmail());
            req.getSession().setAttribute("stats", user.getStats());

        }


    }
