package sistemaDeAlumbrado.demo.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import sistemaDeAlumbrado.demo.dtos.MailDto;


@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Async
    public void sendResetTokenMail(MailDto mail) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(mail.getDestinity());
            helper.setSubject(mail.getSubject());

            Context context = new Context();
            context.setVariable("message", mail.getBody());
            String contentHtml = templateEngine.process("resetPasswordEmail", context);

            helper.setText(contentHtml, true);
            javaMailSender.send(message);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Async
    public void sendWelcomeMail(MailDto mail) throws MessagingException {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(mail.getDestinity());
            helper.setSubject(mail.getSubject());

            Context context = new Context();
            String contentHtml = templateEngine.process("welcomeEmail", context);

            helper.setText(contentHtml, true);
            javaMailSender.send(message);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
