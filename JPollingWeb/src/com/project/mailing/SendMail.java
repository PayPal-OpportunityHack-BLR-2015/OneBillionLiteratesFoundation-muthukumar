/**
 * Project
 */
package com.project.mailing;

/**
 *
 * @author Muthukumar Nagappan
 * @since 1.0
 */
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
	
	public static void main(String[] args) {

		String to = "ayyasamy06@gmail.com";
		String subject = "Test Message";
		String message = "This is A test message sent via Gmail ";
		SendMail sendMail = new SendMail( );
		sendMail.send(to, subject, message);
	}

	private String userid = "muthukumar.annauniv@gmail.com";
	private String password = "9789562069@exterro";
	private String host = "smtp.gmail.com";
	
	private String to; 
	private String subject;
	private String text;
	

	public void send(String to, String subject, String text) {
		
		try {
			Properties props = System.getProperties();
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.setProperty("mail.transport.protocol", "smtps");
			props.put("mail.smtp.user", userid);
			props.put("mail.smtp.password", password);
			props.put("mail.smtp.port", "25");
			props.put("mail.smtps.auth", "true");
			Session session = Session.getDefaultInstance(props, null);
			MimeMessage message = new MimeMessage(session);
			InternetAddress fromAddress = null;
			InternetAddress toAddress = null;

			try {
				fromAddress = new InternetAddress(userid);
				toAddress = new InternetAddress(to);
			} catch (AddressException e) {

				e.printStackTrace();
			}
			message.setFrom(fromAddress);
			message.setRecipient(RecipientType.TO, toAddress);
			message.setSubject(subject);
			message.setText(text);

			// SMTPSSLTransport transport
			// =(SMTPSSLTransport)session.getTransport("smtps");

			Transport transport = session.getTransport("smtps");
			transport.connect(host, userid, password);
			transport.sendMessage(message, message.getAllRecipients());
			System.out.println("Mail successfully send for" + to);
			transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}