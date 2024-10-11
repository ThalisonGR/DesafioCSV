package com.ifrs.DesafioCSV.util;

import static org.junit.jupiter.api.Assertions.*;

import com.ifrs.DesafioCSV.domain.Publication;
import com.ifrs.DesafioCSV.repository.PublicationRepository;
import org.apache.poi.ss.usermodel.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;
class ExcelExportServiceTest {

    @InjectMocks
    private ExcelExportService excelExportService;

    @Mock
    private PublicationRepository publicationRepository;

    private List<Publication> mockPublications;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock data for publications
        mockPublications = Arrays.asList(
                new Publication(
                        1L,
                        "Title 1",
                        "Author A",
                        "Journal Name",
                        2023,
                        "Published Date",
                        "E-Published Date",
                        "Volume 1",
                        "Issue 1",
                        "Pages 10-20",
                        "10.1234/abcde1",
                        "PMID123456",
                        "Label 1",
                        "Label 1",
                        "IUID123456",
                        "https://example.com",
                        "https://doi.org/10.1234/abcde1",
                        "https://pubmed.ncbi.nlm.nih.gov/123456")
        );
    }


    @Test
    public void testExportPublicationsToExcel() throws Exception {
        // When the export service is called
        ByteArrayInputStream in = excelExportService.exportPublicationsToExcel(mockPublications);

        // Use Apache POI to read and verify the generated Excel file
        Workbook workbook = WorkbookFactory.create(in);
        Sheet sheet = workbook.getSheetAt(0);

        // Check header excel
        Row headerRow = sheet.getRow(0);
        assertEquals("Title", headerRow.getCell(0).getStringCellValue());
        assertEquals("Authors", headerRow.getCell(1).getStringCellValue());
        assertEquals("Journal", headerRow.getCell(2).getStringCellValue());
        assertEquals("Year", headerRow.getCell(3).getStringCellValue());
        assertEquals("Published", headerRow.getCell(4).getStringCellValue());
        assertEquals("E-published", headerRow.getCell(5).getStringCellValue());
        assertEquals("Volume", headerRow.getCell(6).getStringCellValue());
        assertEquals("Issue", headerRow.getCell(7).getStringCellValue());
        assertEquals("Pages", headerRow.getCell(8).getStringCellValue());
        assertEquals("DOI", headerRow.getCell(9).getStringCellValue());
        assertEquals("PMID", headerRow.getCell(10).getStringCellValue());
        assertEquals("Labels", headerRow.getCell(11).getStringCellValue());
        assertEquals("Qualifiers", headerRow.getCell(12).getStringCellValue());
        assertEquals("IUID", headerRow.getCell(13).getStringCellValue());
        assertEquals("URL", headerRow.getCell(14).getStringCellValue());
        assertEquals("DOI URL", headerRow.getCell(15).getStringCellValue());
        assertEquals("PubMed URL", headerRow.getCell(16).getStringCellValue());

        // Check data rows
        Row dataRow1 = sheet.getRow(1);
        assertEquals("Title 1", dataRow1.getCell(0).getStringCellValue());            // Verifica o título
        assertEquals("10.1234/abcde1", dataRow1.getCell(1).getStringCellValue());           // Verifica o autor
        assertEquals("Author A", dataRow1.getCell(2).getStringCellValue());       // Verifica o journal
        assertEquals(2023, (int) dataRow1.getCell(3).getNumericCellValue());          // Verifica o ano
        assertEquals("Published Date", dataRow1.getCell(4).getStringCellValue());     // Verifica a data publicada
        assertEquals("E-Published Date", dataRow1.getCell(5).getStringCellValue());   // Verifica a data E-Published
        assertEquals("Volume 1", dataRow1.getCell(6).getStringCellValue());           // Verifica o volume
        assertEquals("Issue 1", dataRow1.getCell(7).getStringCellValue());            // Verifica o issue
        assertEquals("Pages 10-20", dataRow1.getCell(8).getStringCellValue());        // Verifica as páginas
        assertEquals("10.1234/abcde1", dataRow1.getCell(9).getStringCellValue());     // Verifica o DOI
        assertEquals("PMID123456", dataRow1.getCell(10).getStringCellValue());        // Verifica o PMID
        assertEquals("Label 1", dataRow1.getCell(11).getStringCellValue());           // Verifica o label
        assertEquals("Label 1", dataRow1.getCell(12).getStringCellValue());           // Verifica o qualifier
        assertEquals("IUID123456", dataRow1.getCell(13).getStringCellValue());        // Verifica o IUID
        assertEquals("https://example.com", dataRow1.getCell(14).getStringCellValue());// Verifica a URL
        assertEquals("https://doi.org/10.1234/abcde1", dataRow1.getCell(15).getStringCellValue()); // Verifica o DOI URL
        assertEquals("https://pubmed.ncbi.nlm.nih.gov/123456", dataRow1.getCell(16).getStringCellValue()); // Verifica o PubMed URL
        // Ensure no more rows exist
        assertNull(sheet.getRow(2));

        workbook.close();
    }

}