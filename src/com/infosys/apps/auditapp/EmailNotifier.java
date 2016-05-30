package com.infosys.apps.auditapp;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class EmailNotifier {

	private static final String PASSWORD = "";
	private static final String USER_NAME = "melroyndsouza@outlook.com";
	
	private static final String SMTP_PORT = "587";
	private static final String SMTP_SERVER = "smtp-mail.outlook.com";
	
	private String receiver;
	private String subject = "Captured URLs attached as PDF";

	private Multipart bodyParts = new MimeMultipart();

	
	public EmailNotifier setReceiver(String receiver) {
		this.receiver = receiver;
		return this;
	}
	
	public EmailNotifier setSubject(String subject) {
		this.subject = subject;
		return this;
	}
	
	public EmailNotifier setBody(String text) throws MessagingException {
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText(text);

		bodyParts.addBodyPart(messageBodyPart);
		return this;
	}

	public EmailNotifier addAttachment(String fileName, ByteArrayOutputStream os) throws MessagingException {
		BodyPart attachmentBodyPart = new MimeBodyPart();
		DataSource source = new ByteArrayDataSource(os.toByteArray(), "application/octet-stream");
		attachmentBodyPart.setDataHandler(new DataHandler(source));
		attachmentBodyPart.setFileName(fileName);

		bodyParts.addBodyPart(attachmentBodyPart);
		return this;
	}

	public void send() throws AddressException, MessagingException {
		Message message = new MimeMessage(getSmtpSession());
		message.setFrom(new InternetAddress(USER_NAME)); // May be different
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
		message.setSubject(subject);

		// Send the complete message parts
		message.setContent(bodyParts);

		// Send message
		Transport.send(message, USER_NAME, PASSWORD);

	}

	private Session getSmtpSession() {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", SMTP_SERVER);
		props.put("mail.smtp.port", SMTP_PORT);
		return Session.getInstance(props);
	}

}
