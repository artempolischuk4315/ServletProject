package ua.polischuk.controller.command;

import org.apache.log4j.Logger;
import ua.polischuk.utility.MailSender;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

public class SendMail implements Command {
    private static final Logger log = Logger.getLogger( DisableTest.class);

    @Override
    public String execute(HttpServletRequest request) {
        log.info("Sending letter");
        MailSender mailSender = new MailSender();
        String email = (String) request.getSession().getAttribute("email");
        String nameOfTest = (String) request.getSession().getAttribute("lastCompletedTest");
        Integer result = (Integer) request.getSession().getAttribute("resultOfLastCompletedTest");

        if(result!=null) {
            try {
                mailSender.SendingEmail(email, "Your result of " + nameOfTest + " is " + result);
                request.getSession().removeAttribute("resultOfLastCompletedTest");
            } catch (MessagingException e) {
                log.info("Error while sending mail");
            }
        }
        return "redirect:/user/user-hello.jsp";
    }
}
