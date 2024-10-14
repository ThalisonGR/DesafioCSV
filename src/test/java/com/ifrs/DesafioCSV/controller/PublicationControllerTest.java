package com.ifrs.DesafioCSV.controller;

import com.ifrs.DesafioCSV.domain.Publication;
import com.ifrs.DesafioCSV.service.PublicationService;
import com.ifrs.DesafioCSV.util.ExcelExportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PublicationControllerTest {

    @MockBean
    private MockMvc mockMvc;

    @Mock
    private PublicationService publicationService;

    @Mock
    private ExcelExportService excelExportService;

    @InjectMocks
    private PublicationController publicationController;

    private List<Publication> mockPublications;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(publicationController).build();

        mockPublications = Arrays.asList(
                new Publication(1L, "Title 1", "Author A", "Journal Name", 2023, "Published Date",
                        "E-Published Date", "Volume 1", "Issue 1", "Pages 10-20",
                        "10.1234/abcde1", "PMID123456", "Label 1", "Qualifiers",
                        "IUID123456", "https://example.com", "https://doi.org/10.1234/abcde1",
                        "https://pubmed.ncbi.nlm.nih.gov/123456")
        );
    }

    @Test
    public void testUploadFile() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "publications.csv", "text/csv", "data".getBytes());

        doNothing().when(publicationService).save(any(MultipartFile.class));

        mockMvc.perform(multipart("/api/csv/upload")
                .file(mockFile))
                .andExpect(status().isOk())
                .andExpect(content().string("The file is uploaded successfully: publications.csv"));

        verify(publicationService, times(1)).save(any(MultipartFile.class));
    }

    @Test
    public void testGetPublicationsByDoi() throws Exception {
        when(publicationService.filterByDoi("10.1234/abcde1")).thenReturn(mockPublications);

        mockMvc.perform(get("/api/doi").param("doi", "10.1234/abcde1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Title 1"));
        verify(publicationService, times(1)).filterByDoi("10.1234/abcde1");
    }

    @Test
    public void testGetPublicationByYear() throws Exception {
        when(publicationService.getPublicationYear(2023)).thenReturn(mockPublications);

        mockMvc.perform(get("/api/year").param("year", "2023"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.content[0].title").value("Title 1"));
        verify(publicationService, times(1)).getPublicationYear(2023);
    }

    @Test
    public void testGetPublicationByID() throws Exception {
        when(publicationService.findByID(1L)).thenReturn(mockPublications.get(0));

        mockMvc.perform(get("/api/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Title 1"));
        verify(publicationService, times(1)).findByID(1L);
    }

    @Test
    public void testExportPublicationsToExcel() throws Exception {
        when(publicationService.getAllPublications()).thenReturn(mockPublications);
        when(excelExportService.exportPublicationsToExcel(anyList())).thenReturn(new ByteArrayInputStream(new byte[0]));

        mockMvc.perform(get("/api/export"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=publications.xlsx"));

        verify(publicationService, times(1)).getAllPublications();
        verify(excelExportService, times(1)).exportPublicationsToExcel(anyList());
    }
}
