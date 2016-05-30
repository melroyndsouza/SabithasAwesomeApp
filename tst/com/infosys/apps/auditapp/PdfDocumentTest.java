package com.infosys.apps.auditapp;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;

public class PdfDocumentTest {

	@Test
	public void testCreationOfPDfDocument()
			throws BadElementException, MalformedURLException, IOException, DocumentException {
		PdfDocument document = new PdfDocument();

		document.addScreenshotToPdf("www.google.com", ImageIO.read(new File("/home/dsouzam/Desktop/P1000303.JPG")));
		document.addScreenshotToPdf("www.yahoo.com", ImageIO.read(new File("/home/dsouzam/Desktop/P1000303.JPG")));
		document.addScreenshotToPdf("www.amazon.com", ImageIO.read(new File("/home/dsouzam/Desktop/P1000303.JPG")));

		document.toFile("/home/dsouzam/Desktop/P1000303.pdf");
	}
}
