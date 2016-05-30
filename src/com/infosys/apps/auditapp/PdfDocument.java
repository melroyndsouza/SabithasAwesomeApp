package com.infosys.apps.auditapp;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfDocument { // Comment: Modularize your code. Start with small classes and move your way up

	private static final float HEIGHT = 300f; // Comment: Always add a name to
												// your constants
	private static final float WIDTH = 600f;
	public static final String JPG = "jpg";

	private Document pdfDocument;
	private int urlCount = 1;
	private ByteArrayOutputStream pdfByteStream = new ByteArrayOutputStream();

	public PdfDocument() throws DocumentException, FileNotFoundException {
		pdfDocument = new Document();
		PdfWriter writer = PdfWriter.getInstance(pdfDocument, pdfByteStream);
		writer.setFullCompression();

		pdfDocument.open();
		pdfDocument.add(new Paragraph("Snapshot of URLs listed in input"));

		setPdfAttributes();
	}

	public ByteArrayOutputStream getStream() {
		pdfDocument.close();
		return pdfByteStream;
	}
	
	// TODO remove the exceptions using lombok @SneakyThrows
	// Comment : never catch exceptions that you cant handle. Either throw them if not runtime.
	// 			  Better still cast them to runtime using lombok.@SneakyThrows
	public void toFile(String fileLocation) throws DocumentException, IOException {
		// cleaner way is to use apache commons (FileUtils.writeByteArrayToFile)
		FileOutputStream fos = new FileOutputStream(fileLocation);
		fos.write(getStream().toByteArray());
		fos.close();
	}

	public void addScreenshotToPdf(String imageUrl, Image image)
			throws BadElementException, IOException, DocumentException {
		// Add the screenshot image in the PDF page by page
		pdfDocument.add(new Paragraph(String.format("%s. Snapshot of %s", urlCount++, imageUrl)));
		pdfDocument.add(Chunk.NEWLINE);

		com.itextpdf.text.Image pdfImage = asPdfImage(image);
		pdfImage.scaleToFit(WIDTH, HEIGHT);
		pdfDocument.add(pdfImage);
		pdfDocument.newPage();
	}

	private com.itextpdf.text.Image asPdfImage(Image image) throws IOException, BadElementException {

		ByteArrayOutputStream opStream = new ByteArrayOutputStream();
		ImageIO.write((RenderedImage) image, JPG, opStream);

		return com.itextpdf.text.Image.getInstance(opStream.toByteArray());
	}

	private void setPdfAttributes() throws DocumentException {
		// Set attributes here //Comment: When adding comments make them
		// descriptive else dont bother adding them.
		pdfDocument.addAuthor("Sabitha Rebello");
		pdfDocument.addCreationDate();
		pdfDocument.addCreator("Infosys");
		pdfDocument.addTitle("Snapshots of listed URLs");
		pdfDocument.add(Chunk.NEWLINE);
		pdfDocument.addSubject("Used for audits and assessments ");
	}

}
