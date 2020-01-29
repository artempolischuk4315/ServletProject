package ua.polischuk.controller.command;

import ua.polischuk.model.entity.User;
import ua.polischuk.model.service.ServiceFactory;
import ua.polischuk.model.service.UserService;
import ua.polischuk.utility.PasswordEncrypt;
import ua.polischuk.utility.PasswordValidator;
import ua.polischuk.utility.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Optional;

public class Login implements Command {

    private UserService userService;

    public Login() {
            this.userService = ServiceFactory.getInstance().getUserService();
    }
        @Override
        public String execute (HttpServletRequest request){

            System.out.println("1");

            String email = request.getParameter("email");
            String pass = request.getParameter("pass");
            System.out.println("2");
            if (email == null || email.equals("") || pass == null || pass.equals("")) {
                return "/login.jsp";
            }
            System.out.println("3");
            PasswordEncrypt encryptor = new PasswordEncrypt();
            String encryptedPass = null; //в отедльный метод
            try {
                encryptedPass = encryptor.EncryptPassword(pass);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            System.out.println("4");

          /*  if (!validation(request, email)) {
                System.out.println("KEKSTER");
                return "/login.jsp";//todo  error
            }*/

            System.out.println("5");
            User user = new User();
            if (CommandUtility.checkUserIsLogged(request, email)) {
                return "redirect:/error.jsp"; //todo redirect on right error
            }

            System.out.println("6");

                try {
                    user = userService.findByEmail(email);
                } catch (java.lang.Exception e) {
                    e.printStackTrace();
                    return "redirect:/login.jsp";
                }


            System.out.println(user.getEmail());

            System.out.println("7");
            if (user.getEmail().equals("art4315@gmail.com") && user.getPassword().equals(encryptedPass)) {
                CommandUtility.setUserRole(request, User.ROLE.ADMIN, email);
                CommandUtility.addUserToContext(request, email);
                setAttribute(request, user);
                return "redirect:/admin/admin-hello.jsp";
            } else if (user.getEmail().equals(email) && user.getPassword().equals(encryptedPass)) {
                CommandUtility.setUserRole(request, User.ROLE.USER, email);
                CommandUtility.addUserToContext(request, email);
                setAttribute(request, user);
                return "redirect:/user/user-hello.jsp";
            } else {
               // CommandUtility.setUserRole(request, User.ROLE.UNKNOWN, email);
                System.out.println("KEK IS HERE");
                return "/login.jsp";//todo  error
            }

        }

        private boolean validation (HttpServletRequest req, String email){


            boolean validPassOrEmail = true;
            if (!Validator.checkEmail(email)) {
                req.setAttribute("errEmailMessage", "not valid email");
                validPassOrEmail = false;
            }
            return validPassOrEmail;
        }

        private void setAttribute (HttpServletRequest req, User user){
            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("role", user.getRole());

        }


    }
