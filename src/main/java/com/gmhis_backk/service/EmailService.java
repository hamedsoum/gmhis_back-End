package com.gmhis_backk.service;

import com.gmhis_backk.domain.EmailReceiver;
import com.gmhis_backk.enumeration.EmailTypeEnum;
import com.gmhis_backk.repository.EmailReceiverRepository;
import com.sun.mail.smtp.SMTPTransport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static com.gmhis_backk.constant.EmailConstant.*;
import static javax.mail.Message.RecipientType.CC;
import static javax.mail.Message.RecipientType.TO;
@Service
public class EmailService {
	
	@Autowired
	EmailReceiverRepository receiverRepo;

    public void sendNewPasswordEmail(String firstName, String username, String password, String email) throws MessagingException {
        Message message = createEmail(firstName, username, password, email);
        SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL);
        smtpTransport.connect(GMAIL_SMTP_SERVER, USERNAME, PASSWORD);
        smtpTransport.sendMessage(message, message.getAllRecipients());
        smtpTransport.close();
    }

    private Message createEmail(String firstName, String username, String password, String email) throws MessagingException {
       
    	Message message = new MimeMessage(getEmailSession());
    	
    	String body = "";
        body+="<!DOCTYPE html>\r\n"
        		+ "<html lang=\"fr\">\r\n"
        		+ "  <head>\r\n"
        		+ "    <meta charset=\"UTF-8\" />\r\n"
        		+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\r\n"
        		+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\r\n"
        		+ "    <link href=\"https://fonts.googleapis.com/css2?family=Roboto:wght@100;300;400&display=swap\" rel=\"stylesheet\">\r\n"
        		+ "    <title>Mail Hotel</title>\r\n"
        		+ "​\r\n"
        		+ "    <style>\r\n"
        		+ "        *, ::after, ::before{\r\n"
        		+ "            margin: 0;\r\n"
        		+ "            padding: 0;\r\n"
        		+ "            box-sizing: border-box;\r\n"
        		+ "            font-family: 'Roboto', sans-serif;\r\n"
        		+ "            color: gray;\r\n"
        		+ "        }\r\n"
        		+ "    </style>\r\n"
        		+ "  </head>\r\n"
        		+ "  <body>\r\n"
        		+ "    <div style=\"width: 100%; min-height: 100vh; background: #f8f8fa; padding: 5rem;\">\r\n"
        		+ "        <div style=\"width: 50%; min-height: 20rem; background-color: white; margin: auto\">\r\n"
        		+ "            <div style=\" padding: 2rem;\">\r\n"
        		+ "                <img width=\"150\" src=\"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSmMqZnND4cb-D-FYHuJWiKltYvr4p90fiUPypYQgrZuMuGEfgOGNVbR6JHQltA7FA_EfI&usqp=CAU\" alt=\"logo de l'entreprise\">\r\n"
        		+ "                <p style=\"margin: 1rem 0;\">Bonjour "+firstName+" </p>\r\n"
        		+ "                <p style=\"margin-bottom: 1rem;\">\r\n"
        		+ "                  Votre nom d'utilisateur est: " +username  +" <br><br> le mot de passe est :" + password +"<br><br> A la première connexion vous serez invité à changer votre mot de passe.\r\n"
        		+ "                </p>\r\n"
        		+ "  \r\n"
        		+ "                <p style=\"margin-top: 1rem;\">\r\n"
        		+ "                    Si vous n'êtes pas à l'origine de cette demande, ne prêtez pas attention à cet e-mail, votre mot de \r\n"
        		+ "                    passe restera inchangé et votre compte sécurisé.\r\n"
        		+ "                </p>\r\n"
        		+ "            </div>\r\n"
        		+ "            <div style=\"padding: 1.5rem 2rem; background-color: #0f5e99; text-align: center\">\r\n"
        		+ "                <small style=\"color: rgba(255, 255, 255, 0.751); font-size: .8rem;\">&copy; VNE - Tous droits réservés</small>\r\n"
        		+ "            </div>\r\n"
        		+ "        </div>\r\n"
        		+ "    </div>\r\n"
        		+ "  </body>\r\n"
        		+ "</html>";
     
    	
    	
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(TO, InternetAddress.parse(email, false));
        message.setRecipients(CC, InternetAddress.parse(CC_EMAIL, false));
        message.setSubject(EMAIL_SUBJECT);
        message.setContent(body, "text/html");
//      message.setText("Salut " + firstName + ", \n \n Votre nom d'utilisateur est: " +username  + "\n \n le mot de passe est :" + password + "\n \n A la première connexion vous serez invité à changer votre mot de passe. \n \n Equipe technique!");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }

    private Session getEmailSession() {
        Properties properties = System.getProperties();
        properties.put(SMTP_HOST, GMAIL_SMTP_SERVER);
        properties.put(SMTP_AUTH, true);
        properties.put(SMTP_PORT, DEFAULT_PORT);
        properties.put(SMTP_STARTTLS_ENABLE, true);
        properties.put(SMTP_STARTTLS_REQUIRED, true);
        return Session.getInstance(properties, null);
    }
    

    private Message createForgotEmail(String email,String code,String firstName, String lastName) throws MessagingException {
       
    	Message message = new MimeMessage(getEmailSession());
        
        String body = "";
          body+="<!DOCTYPE html>\r\n"
          		+ "<html lang=\"fr\">\r\n"
          		+ "  <head>\r\n"
          		+ "    <meta charset=\"UTF-8\" />\r\n"
          		+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\r\n"
          		+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\r\n"
          		+ "    <link href=\"https://fonts.googleapis.com/css2?family=Roboto:wght@100;300;400&display=swap\" rel=\"stylesheet\">\r\n"
          		+ "    <title>Mail Hotel</title>\r\n"
          		+ "​\r\n"
          		+ "    <style>\r\n"
          		+ "        *, ::after, ::before{\r\n"
          		+ "            margin: 0;\r\n"
          		+ "            padding: 0;\r\n"
          		+ "            box-sizing: border-box;\r\n"
          		+ "            font-family: 'Roboto', sans-serif;\r\n"
          		+ "            color: gray;\r\n"
          		+ "        }\r\n"
          		+ "    </style>\r\n"
          		+ "  </head>\r\n"
          		+ "  <body>\r\n"
          		+ "    <div style=\"width: 100%; min-height: 100vh; background: #f8f8fa; padding: 5rem;\">\r\n"
          		+ "        <div style=\"width: 50%; min-height: 20rem; background-color: white; margin: auto\">\r\n"
          		+ "            <div style=\" padding: 2rem;\">\r\n"
          		+ "                <img width=\"150\" src=\"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSmMqZnND4cb-D-FYHuJWiKltYvr4p90fiUPypYQgrZuMuGEfgOGNVbR6JHQltA7FA_EfI&usqp=CAU\" alt=\"logo de l'entreprise\">\r\n"
          		+ "                <p style=\"margin: 1rem 0;\">Bonjour "+lastName+"</p>\r\n"
          		+ "                <p style=\"margin-bottom: 1rem;\">\r\n"
          		+ "                    Vous avez fait une demande de réinitialisation de votre mot de passe pour accéder à votre compte hotel. \r\n"
          		+ "                    Pour finaliser ce changement, cliquez ci-dessous :\r\n"
          		+ "                </p>\r\n"
          		+ "                <a href=\"http://localhost:4200/account/reset-password/"+code+"\" style=\"text-decoration: none; display: inline-block; padding: .7rem 1rem; background-color: #009f60; color: white;\">Changer mon mot de passe</a>\r\n"
          		+ "                <p style=\"margin-top: 1rem;\">\r\n"
          		+ "                    Si vous n'êtes pas à l'origine de cette demande, ne prêtez pas attention à cet e-mail, votre mot de \r\n"
          		+ "                    passe restera inchangé et votre compte sécurisé.\r\n"
          		+ "                </p>\r\n"
          		+ "            </div>\r\n"
          		+ "            <div style=\"padding: 1.5rem 2rem; background-color: #0f5e99; text-align: center\">\r\n"
          		+ "                <small style=\"color: rgba(255, 255, 255, 0.751); font-size: .8rem;\">&copy; VNE - Tous droits réservés</small>\r\n"
          		+ "            </div>\r\n"
          		+ "        </div>\r\n"
          		+ "    </div>\r\n"
          		+ "  </body>\r\n"
          		+ "</html>";
       
        		
        		message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(TO, InternetAddress.parse(email, false));
        message.setRecipients(CC, InternetAddress.parse(CC_EMAIL, false));
        message.setSubject(EMAIL_SUBJECT1);
        message.setContent(body, "text/html");
        message.setSentDate(new Date());
        message.saveChanges();
        
        return message;
    }
    
    public void sendForgotEmail(String email,String code,String firstName, String lastName) throws MessagingException {
        Message message = createForgotEmail(email,code, firstName, lastName);
        SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL);
        smtpTransport.connect(GMAIL_SMTP_SERVER, USERNAME, PASSWORD);
        smtpTransport.sendMessage(message, message.getAllRecipients());
        smtpTransport.close();
    }
    
    
    
    public void sendPersonnResearchEmail(String email,String nom,String prenoms, String nomHotel, String localisation) throws MessagingException {
        Message message = createPersonnResearchEmail(email,nom, prenoms,nomHotel,localisation);
        SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL);
        smtpTransport.connect(GMAIL_SMTP_SERVER, USERNAME, PASSWORD);
        smtpTransport.sendMessage(message, message.getAllRecipients());
        smtpTransport.close();
    }
    
    
    private Message createPersonnResearchEmail(String email,String nom,String prenoms, String nomHotel, String localisation) throws MessagingException {
        
    	Message message = new MimeMessage(getEmailSession());
    	
    	String body = "";
        body+="<html lang=\"fr\">\r\n"
        		+ "  <head>\r\n"
        		+ "    <meta charset=\"UTF-8\" />\r\n"
        		+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\r\n"
        		+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\r\n"
        		+ "    <link href=\"https://fonts.googleapis.com/css2?family=Roboto:wght@100;300;400&display=swap\" rel=\"stylesheet\">\r\n"
        		+ "    <title>Mail Hotel</title>\r\n"
        		+ "​\r\n"
        		+ "    <style>\r\n"
        		+ "        *, ::after, ::before{\r\n"
        		+ "            margin: 0;\r\n"
        		+ "            padding: 0;\r\n"
        		+ "            box-sizing: border-box;\r\n"
        		+ "            font-family: 'Roboto', sans-serif;\r\n"
        		+ "            color: gray;\r\n"
        		+ "        }\r\n"
        		+ "    </style>\r\n"
        		+ "  </head>\r\n"
        		+ "  <body>\r\n"
        		+ "    <div style=\"width: 100%; min-height: 100vh; background: #f8f8fa; padding: 5rem;\">\r\n"
        		+ "        <div style=\"width: 50%; min-height: 20rem; background-color: white; margin: auto\">\r\n"
        		+ "            <div style=\" padding: 2rem;\">\r\n"
        		+ "                <img width=\"150\" src=\"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSmMqZnND4cb-D-FYHuJWiKltYvr4p90fiUPypYQgrZuMuGEfgOGNVbR6JHQltA7FA_EfI&usqp=CAU\" alt=\"logo de l'entreprise\">\r\n"
        		+ "                <p style=\"margin: 1rem 0;\">Alerte maximale</p>\r\n"
        		+ "                <p style=\"margin-bottom: 1rem;\">\r\n"
        		+ "                  Le recherché dénommé : "+nom+" "+prenoms+ " a été aperçu dans l'hotel " + nomHotel+ " situé à "+localisation+".<br><br> Veuillez vous y rendre immédiatement.\r\n"
        		+ "                </p>\r\n"
        		+ "  \r\n"
        		+ "                <p style=\"margin-top: 1rem;\">\r\n"
        		+ "                    Si vous n'êtes pas à l'origine de cette demande, ne prêtez pas attention à cet e-mail, votre mot de \r\n"
        		+ "                    passe restera inchangé et votre compte sécurisé.\r\n"
        		+ "                </p>\r\n"
        		+ "            </div>\r\n"
        		+ "            <div style=\"padding: 1.5rem 2rem; background-color: #0f5e99; text-align: center\">\r\n"
        		+ "                <small style=\"color: rgba(255, 255, 255, 0.751); font-size: .8rem;\">&copy; VNE - Tous droits réservés</small>\r\n"
        		+ "            </div>\r\n"
        		+ "        </div>\r\n"
        		+ "    </div>\r\n"
        		+ "  </body>\r\n"
        		+ "</html>";
     
    	
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(TO, InternetAddress.parse(email, false));
        message.setRecipients(CC, InternetAddress.parse(CC_EMAIL, false));
        message.setSubject(EMAIL_SUBJECT);
        message.setContent(body, "text/html");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }
}

