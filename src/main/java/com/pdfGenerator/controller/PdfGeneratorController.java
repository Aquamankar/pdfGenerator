package com.pdfGenerator.controller;

import com.itextpdf.text.DocumentException;
import com.pdfGenerator.entity.PdfEntity;
import com.pdfGenerator.payload.PdfRequestData;
import com.pdfGenerator.service.PdfGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/pdfs")
public class PdfGeneratorController {


    private  PdfGeneratorService pdfGeneratorService;
    @Autowired
    public PdfGeneratorController(PdfGeneratorService pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @PostMapping(produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateAndSavePdf(@RequestBody PdfRequestData requestData) {
        try {
            // Call the PdfGeneratorService to generate and save the PDF
            byte[] pdfContent = pdfGeneratorService.generatePdf(requestData);

            // Return the PDF content in the response with appropriate headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "generated-pdf.pdf");

            // Return the PDF content in the response
            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
        } catch (DocumentException | IOException e) {
            // Handle the exception appropriately (e.g., return an error response)
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
