package com.pdfGenerator.service;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pdfGenerator.entity.PdfEntity;
import com.pdfGenerator.payload.PdfRequestData;
import com.pdfGenerator.repository.PdfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
public class PdfGeneratorService {
   private  PdfRepository pdfRepository;
    @Autowired
    public PdfGeneratorService(PdfRepository pdfRepository) {
        this.pdfRepository = pdfRepository;
    }

    public byte[] generatePdf(PdfRequestData requestData) throws DocumentException, IOException {

        // Save PDF data to the database
        PdfEntity pdfEntity = new PdfEntity();

        pdfEntity.setContent(requestData.getContent());
        pdfEntity.setFlightNumber(requestData.getFlightNumber());
        pdfEntity.setAirline(requestData.getAirline());
        pdfEntity.setArrivalCity(requestData.getArrivalCity());
        pdfEntity.setDepartureCity(requestData.getDepartureCity());
        pdfEntity.setEstimateTime(requestData.getEstimateTime());
        pdfEntity.setCheckIn(requestData.getCheckIn());
        pdfEntity.setCreatedAt(LocalDateTime.now());
        pdfRepository.save(pdfEntity);



        String  filePath= "E:\\test1\\tickets\\reservation_" + pdfEntity.getId() + ".pdf";

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));

        document.open();

        // Add content paragraph
        document.add(new Paragraph(requestData.getContent()));


        // Format the createdAt timestamp to a readable date-time format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedCreatedAt = pdfEntity.getCreatedAt().format(formatter);

        // Add createdAt timestamp to the PDF content
        document.add(new Paragraph("Created At: " + formattedCreatedAt));

        // Add content paragraph (you can move this above if you prefer)
        document.add(new Paragraph(requestData.getContent()));

        // Add flight details table
        PdfPTable table = new PdfPTable(2); // 2 columns for key-value pairs
        table.setWidthPercentage(80);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        addRowToTable(table, "Flight Number:", requestData.getFlightNumber());
        addRowToTable(table, "Airline:", requestData.getAirline());
        addRowToTable(table, "Arrival City:", requestData.getArrivalCity());
        addRowToTable(table, "Departure City:", requestData.getDepartureCity());
        addRowToTable(table, "Estimate Time:", requestData.getEstimateTime());
        addRowToTable(table, "Check-In:", requestData.getCheckIn());


        document.add(table);

        document.close();

        // Read the PDF file content and return it as a byte array
        File pdfFile = new File(filePath);
        byte[] pdfContent = Files.readAllBytes(pdfFile.toPath());

        // Delete the generated PDF file after reading its content
          pdfFile.delete();

        return pdfContent;

        //return filePath.getBytes(); // Return the file path as byte array if needed
    }

    private void addRowToTable(PdfPTable table, String key, String value) {
        PdfPCell keyCell = new PdfPCell(new Paragraph(key));
        PdfPCell valueCell = new PdfPCell(new Paragraph(value));
        table.addCell(keyCell);
        table.addCell(valueCell);
    }
}