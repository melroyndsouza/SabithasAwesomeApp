package com.infosys.apps.auditapp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.junit.Test;

public class EmailNotifierTest {

	@Test
	public void testSendMessageWithoutAttachment() throws AddressException, MessagingException {
		new EmailNotifier()
				.setReceiver("melroy.weds.sabitha@gmail.com")
				.setSubject("Hi from Melroy")
				.setBody("Awesome Body")
				.send();
	}
	
	@Test
	public void testSendMessageWithAttachment() throws AddressException, MessagingException, IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(Files.readAllBytes(Paths.get("/home/dsouzam/Desktop/P1000303.JPG")));
		
		new EmailNotifier()
				.setReceiver("melroy.weds.sabitha@gmail.com")
				.setSubject("Hi from Melroy")
				.setBody("Awesome Body")
				.addAttachment("Me.jpg",baos)
				.send();
	}
}
