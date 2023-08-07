package com.pdfGenerator.repository;

;
import com.pdfGenerator.entity.PdfEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdfRepository extends JpaRepository<PdfEntity, Long> {

}