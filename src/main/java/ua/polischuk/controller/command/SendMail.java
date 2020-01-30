package ua.polischuk.controller.command;

import ua.polischuk.model.service.ServiceFactory;
import ua.polischuk.model.service.TestService;
import ua.polischuk.model.service.UserService;
import ua.polischuk.utility.MailSender;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

public class SendMail implements Command {
    private TestService testService;

    public SendMail() {
        this.testService = ServiceFactory.getInstance().getTestService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        System.out.println("MAIL");
        MailSender mailSender = new MailSender();
        String email = (String) request.getSession().getAttribute("email");
        String nameOfTest = (String) request.getSession().getAttribute("lastCompletedTest");
        Integer result = (Integer) request.getSession().getAttribute("resultOfLastCompletedTest");
        try {
            mailSender.SendingEmail(email,"Your result of "+nameOfTest+" is "+result);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "redirect:/user/user-hello.jsp";
    }
}
