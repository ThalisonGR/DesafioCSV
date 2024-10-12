package com.ifrs.DesafioCSV.service;

import com.ifrs.DesafioCSV.domain.Publication;
import com.ifrs.DesafioCSV.repository.PublicationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PublicationServiceTest {

    @InjectMocks
    private  PublicationService publicationService;

    @Mock
    private PublicationRepository publicationRepository;

    private Publication mockPublication;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        mockPublication = new Publication(1L, "Title 1", "Author A", "Journal Name", 2023, "Published Date",
                "E-Published Date", "Volume 1", "Issue 1", "Pages 10-20",
                "10.1234/abcde1", "PMID123456", "Label 1", "Qualifiers",
                "IUID123456", "https://example.com", "https://doi.org/10.1234/abcde1",
                "https://pubmed.ncbi.nlm.nih.gov/123456");
    }

    @Test
    void testSavePublication() {
        when(publicationRepository.save(mockPublication)).thenReturn(mockPublication);
        Publication savedPublication = publicationRepository.save(mockPublication);

        assertNotNull(savedPublication);
        assertEquals(mockPublication.getTitle(), savedPublication.getTitle());
        assertEquals(mockPublication.getDoi(), savedPublication.getDoi());
        verify(publicationRepository, times(1)).save(mockPublication);
    }

    @Test
    void testFilterByDoi() {
        String doi = "10.1234/abcde1";
        when(publicationRepository.searchByDoi(doi)).thenReturn(Arrays.asList(mockPublication));
        List<Publication> result = publicationService.filterByDoi(doi);

        assertEquals(1, result.size());
        assertEquals(mockPublication.getDoi(), result.get(0).getDoi());
        verify(publicationRepository, times(1)).searchByDoi(doi);
    }


    @Test
    void testfindByID() {
        Long id = 1L;
        when(publicationRepository.findById(id)).thenReturn(Optional.of(mockPublication));
        Publication result = publicationService.findByID(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(publicationRepository, times(1)).findById(id);
    }
    @Test
    void getPublicationYear(){
        int year = 2023;
        when(publicationRepository.findByP_year(year)).thenReturn(Arrays.asList(mockPublication));
        List<Publication> result = publicationService.getPublicationYear(year);

        assertEquals(1, result.size());
        assertEquals(2023, result.get(0).getP_year());
        verify(publicationRepository, times(1)).findByP_year(year);
    }

}