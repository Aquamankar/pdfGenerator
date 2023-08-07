package com.pdfGenerator.entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pdf_data")
public class PdfEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(name = "flight_number", nullable = false)
    private String flightNumber;

    @Column(name = "airline", nullable = false)
    private String airline;

    @Column(name = "arrival_city", nullable = false)
    private String arrivalCity;

    @Column(name = "departure_city", nullable = false)
    private String departureCity;

    @Column(name = "estimate_time", nullable = false)
    private String estimateTime;

    @Column(name = "check_in", nullable = false)
    private String checkIn;

    private LocalDateTime createdAt;


}
