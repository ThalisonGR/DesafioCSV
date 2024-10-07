package com.ifrs.DesafioCSV.service;

import com.ifrs.DesafioCSV.domain.Publication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IPublication {
    void save(MultipartFile file);
    Publication findByID(Long id);
    List<Publication> filterByDoi(String doi);
    List<Publication> getPublicationYear(Integer year);
}
