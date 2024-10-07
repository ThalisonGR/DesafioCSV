package com.ifrs.DesafioCSV.service;

import com.ifrs.DesafioCSV.domain.Publication;
import com.ifrs.DesafioCSV.exception.PublicationExcepitonNotFound;
import com.ifrs.DesafioCSV.repository.PublicationRepository;
import com.ifrs.DesafioCSV.util.CsvUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PublicationService implements IPublication {

    @Autowired
    PublicationRepository pRepository;

    @Override
    public void save(MultipartFile file) {
        try {
            List < Publication > stuList = CsvUtility.csvToStuList(file.getInputStream());
            pRepository.saveAll(stuList);
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

    public Publication findByID(Long id){
        Publication p  = pRepository.findById(id).orElseThrow(null);
        return  p;
    }




}
