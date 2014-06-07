package br.com.badrequest.insporte.service;

import java.util.Properties;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Stateless
public class EmailService {

	@Asynchronous
	public void send(String nome, String email, String assunto, String mensagem) {
		
		final String SMTP_USERNAME = "?";
		final String SMTP_PASSWORD = "?";
		final String HOST = "email-smtp.us-west-2.amazonaws.com";
		final String PORT = "587";
 
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", HOST);
		props.put("mail.smtp.port", PORT);


		Session session = Session.getInstance(props, new Authenticator(){
		    public PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
		    }
		});

		String data = "Email de: " + nome + " (" + email + ")" + "\n\n" + 
					  "Assunto: " + assunto + "\n\n" +
					  "Mensagem: " + mensagem; 
		
		System.out.println("Recebido mensagem de :" + nome + "("+ email +"");
		
		try {
			
	        // Create a message with the specified information. 
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("eduardocolabardini@gmail.com"));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("eduardocolabardini@gmail.com"));
			msg.setSubject("Contato - Insporte");
			msg.setContent(data,"text/plain");

            System.out.println("Attempting to send an email through the Amazon SES SMTP interface...");
			
            // Create a transport.        
	        Transport transport = session.getTransport();
            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
 
			System.out.println("Email sent!");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
 }
