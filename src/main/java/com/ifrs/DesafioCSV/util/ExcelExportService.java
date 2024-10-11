package com.ifrs.DesafioCSV.util;

import com.ifrs.DesafioCSV.domain.Publication;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelExportService {
    public ByteArrayInputStream exportPublicationsToExcel(List<Publication> publications) throws IOException {
        // Criação do workbook e planilha
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Publications");
            // Criação do cabeçalho da planilha
            Row headerRow = sheet.createRow(0);
            String[] columns = {
                    "Title",
                    "Authors",
                    "Journal",
                    "Year",
                    "Published",
                    "E-published",
                    "Volume",
                    "Issue",
                    "Pages",
                    "DOI",
                    "PMID",
                    "Labels",
                    "Qualifiers",
                    "IUID",
                    "URL",
                    "DOI URL",
                    "PubMed URL"};
            for (int i = 0 ; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                CellStyle headerCellStyle = workbook.createCellStyle();
                headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
                headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cell.setCellStyle(headerCellStyle);
            }

            int rowIdx = 1;
            for (Publication publication : publications) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(publication.getTitle());
                row.createCell(1).setCellValue(publication.getDoi());
                row.createCell(2).setCellValue(publication.getAuthors());
                row.createCell(3).setCellValue(publication.getP_year());
                row.createCell(4).setCellValue(publication.getPublished());
                row.createCell(5).setCellValue(publication.getEPublished());
                row.createCell(6).setCellValue(publication.getVolume());
                row.createCell(7).setCellValue(publication.getIssue());
                row.createCell(8).setCellValue(publication.getPages());
                row.createCell(9).setCellValue(publication.getDoi());
                row.createCell(10).setCellValue(publication.getPmid());
                row.createCell(11).setCellValue(publication.getLabels());
                row.createCell(12).setCellValue(publication.getQualifiers());
                row.createCell(13).setCellValue(publication.getIuid());
                row.createCell(14).setCellValue(publication.getUrl());
                row.createCell(15).setCellValue(publication.getDoiUrl());
                row.createCell(16).setCellValue(publication.getPubMedUrl());
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
