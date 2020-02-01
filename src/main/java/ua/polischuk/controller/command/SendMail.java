package ua.polischuk.controller.command;

import ua.polischuk.utility.MailSender;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

public class SendMail implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        System.out.println("MAIL");
        MailSender mailSender = new MailSender();
        String email = (String) request.getSession().getAttribute("email");
        String nameOfTest = (String) request.getSession().getAttribute("lastCompletedTest");
        Integer result = (Integer) request.getSession().getAttribute("resultOfLastCompletedTest");

        if(result!=null) {
            try {
                mailSender.SendingEmail(email, "Your result of " + nameOfTest + " is " + result);
                request.getSession().removeAttribute("resultOfLastCompletedTest");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/user/user-hello.jsp";
    }
}
