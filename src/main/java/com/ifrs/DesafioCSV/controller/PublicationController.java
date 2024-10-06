package com.ifrs.DesafioCSV.controller;

import java.net.http.HttpClient;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ifrs.DesafioCSV.domain.Publication;
import com.ifrs.DesafioCSV.service.PublicationService;
import com.ifrs.DesafioCSV.util.CsvUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class PublicationController {
    @Autowired
    PublicationService publicationService;

    @PostMapping("/csv/upload")
    public ResponseEntity < ? > uploadFile(@RequestParam("file") MultipartFile file) {
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

    @GetMapping("/publication-list")
    public ResponseEntity < ? > getStudents() {
        Map < String, Object > respPub = new LinkedHashMap < String, Object > ();
        List <Publication> studList = publicationService.findAll();
        if (!studList.isEmpty()) {
            respPub.put("status", 1);
            respPub.put("data", studList);
            return new ResponseEntity < > (studList, HttpStatus.OK);
        } else {
            respPub.clear();
            respPub.put("status", 0);
            respPub.put("message", "Data is not found");
            return new ResponseEntity < > (respPub, HttpStatus.NOT_FOUND);
        }
    }
}
