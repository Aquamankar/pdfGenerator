package com.pdfGenerator.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PdfRequestData {


    private  long id;
    private String content;
    private String flightNumber;
    private String airline;
    private String arrivalCity;
    private String departureCity;
    private String estimateTime;
    private String checkIn;


}
