package com.ifrs.DesafioCSV.service;

import com.ifrs.DesafioCSV.domain.Publication;
import com.ifrs.DesafioCSV.repository.PublicationRepository;
import com.ifrs.DesafioCSV.util.CsvUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class PublicationService implements IPublication {

    @Autowired
    PublicationRepository pRepository;

    @Override
    public void save(MultipartFile file) {
        try {
            List < Publication > publications = CsvUtility.csvToStuList(file.getInputStream());
            pRepository.saveAll(publications);
            log.info("Saved successfully CSV");
        } catch (IOException ex) {
            log.error("Not save");
            throw new RuntimeException("Data is not store successfully: " + ex.getMessage());
        }
    }

    @Override
    public List<Publication> filterByDoi(String doi) {
       return pRepository.searchByDoi(doi);

    }

    @Override
    public Publication findByID(Long id){
        Publication p  = pRepository.findById(id).orElseThrow(null);
        return  p;
    }

    @Override
    public List<Publication> getPublicationYear(Integer year){
        List<Publication> publications = pRepository.findByP_year(year);
        return publications;
    }

    public List<Publication> getAllPublications() {
        return pRepository.findAll();
    }
}
