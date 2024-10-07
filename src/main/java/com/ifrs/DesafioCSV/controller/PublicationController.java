package com.ifrs.DesafioCSV.controller;

import java.net.http.HttpClient;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ifrs.DesafioCSV.domain.Publication;
import com.ifrs.DesafioCSV.exception.PublicationExcepitonNotFound;
import com.ifrs.DesafioCSV.service.PublicationService;
import com.ifrs.DesafioCSV.util.CsvUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class PublicationController {
    @Autowired
    PublicationService publicationService;



    @PostMapping("/csv/upload")
    public ResponseEntity  uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        if (CsvUtility.hasCsvFormat(file)) {
            try {
                publicationService.save(file);
                message = "The file is uploaded successfully: " + file.getOriginalFilename();
//                HttpClient client = HttpClient.newBuilder().;
                return ResponseEntity.status(HttpStatus.OK).body(message);
            } catch (Exception e) {
                message = "The file is not upload successfully: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }
        }
        message = "Please upload an csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }



    @GetMapping(path = "/doi")
    public ResponseEntity<List<Publication>> getPublicationsByDoi(
            @RequestParam(required = true) String doi  // Recebe uma lista de DOIs
    ) {
        List<Publication> publications = publicationService.filterByDoi(doi);

        if (!publications.isEmpty()) {
            return ResponseEntity.ok(publications);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    




    @GetMapping("/id/{id}")
    public ResponseEntity<Publication> getPublicationByID(@PathVariable Long id) {
        Publication p = publicationService.findByID(id);
        return ResponseEntity.ok().body(p);
    }
}