package com.infosys.apps.auditapp;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.itextpdf.text.DocumentException;

public class WebsiteCaptureTask {

	// TODO use lombok
	public void perform(String inputFileLocation) throws AddressException, FileNotFoundException, MessagingException, DocumentException, IOException {
		perform(Files.readAllLines(Paths.get(inputFileLocation)));
	}
	
	public void perform(List<String> urls) throws AddressException, MessagingException, FileNotFoundException, DocumentException {
		PdfDocument pdfDocument = new PdfDocument();
		
		urls.forEach((url) -> {			//Comment: java 8 syntax for looping (Check out streams).
			try {
				pdfDocument.addScreenshotToPdf(url, getSnapshotForWebsite(url));
			} catch (Exception e) {		//Comment : I am catching exception here so that the rest of the files can be processed.
				// replace with a logger that can be configured to print to a
				// file
				System.err.println("Exception occurred when processing " + url);
				e.printStackTrace();
			}
		});
		
		new EmailNotifier()
			.setReceiver("rebello.sabitha@gmail.com")
			.setSubject("Hi from Melroy")
			.setBody("Awesome Body")
			.addAttachment("op.pdf",pdfDocument.getStream())
			.send();
	}

	private BufferedImage getSnapshotForWebsite(String url)
			throws IOException, URISyntaxException, InterruptedException, AWTException {
		//Comment : It does not close the browser. 
		//Another option http://www.programcreek.com/2009/05/using-java-open-ie-and-close-ie/
		//I dont recommend it though since it allows the input.txt to execute a command on your host
		// Check https://www.owasp.org/index.php/Command_Injection . Suggest using selenium instead.
		
		Desktop.getDesktop().browse(new URI(url));
		TimeUnit.SECONDS.sleep(5);

		Robot robot = new Robot();

		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
		return screenFullImage;
	}

}
