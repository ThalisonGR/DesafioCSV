package com.ifrs.DesafioCSV.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.ifrs.DesafioCSV.domain.Publication;
import com.ifrs.DesafioCSV.service.PublicationService;
import com.ifrs.DesafioCSV.util.CsvUtility;
import com.ifrs.DesafioCSV.util.ExcelExportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@Tag(
        name = "Publicações API",
        description = "Endpoints das publicações")
public class PublicationController {

    @Autowired
    PublicationService publicationService;

    @Autowired
    private ExcelExportService excelExportService;


    @Operation(
            summary = "Salva o arquivo CSV",
            description  = "salva o arquivo")
    @ApiResponse(
            responseCode = "200",
            description = "Operção realizada com sucesso")
    @ApiResponse(
            responseCode = "417",
            description = " Não pôde ser atendida a solicitação")
    @PostMapping("/csv/upload")
    public ResponseEntity  uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        if (CsvUtility.hasCsvFormat(file)) {
            try {
                publicationService.save(file);
                message = "The file is uploaded successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(message);
            } catch (Exception e) {
                message = "The file is not upload successfully: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }
        }
        message = "Please upload an csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }


    @Operation(
            summary = "Exportar os dados em Excel",
            description  = "Exportar os dados em excel, pois gera e disponibiliza para dowloand")
    @ApiResponse(
            responseCode = "200",
            description = "Operção realizada com sucesso")
    @ApiResponse(
            responseCode = "404",
            description = " Problema ao exportar xls")
    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportPublicationsToExcel() throws IOException {
       try {
           List<Publication> publications = publicationService.getAllPublications();
           ByteArrayInputStream in = excelExportService.exportPublicationsToExcel(publications);

           HttpHeaders headers = new HttpHeaders();
           headers.add("Content-Disposition", "attachment; filename=publications.xlsx");

           return ResponseEntity.ok()
                   .headers(headers)
                   .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                   .body(new InputStreamResource(in));
       }catch (Exception e){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
       }
    }


    @Operation(
            summary = "Retorna filtrando DOI",
            description  = "Retorna o objeto em json de acordo com o parametro passado no DOI")
    @ApiResponse(
            responseCode = "200",
            description = "Operção realizada com sucesso")
    @ApiResponse(
            responseCode = "404",
            description = " Problema ao retornar o DOI")
    @GetMapping(path = "/doi")
    public ResponseEntity<CollectionModel<EntityModel<Publication>>> getPublicationsByDoi(
            @RequestParam(required = true) String doi
    ) {
        List<Publication> publications = publicationService.filterByDoi(doi);
        /* Converta cada publicação para um EntityModel e adicione links HATEOAS
            Adiciona um link para a própria publicação
            Converte publicatuin para um EntityModel e adicione links HATEOAS
         */
        if (!publications.isEmpty()) {
            List<EntityModel<Publication>> publicationModels = publications.stream()
                    .map(publication -> EntityModel.of(publication,
                            linkTo(methodOn(PublicationController.class).getPublicationByID(publication.getId())).withSelfRel(),
                            linkTo(methodOn(PublicationController.class).getAllPublication()).withRel("all-publications")))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(
                    CollectionModel.of(publicationModels,
                            linkTo(methodOn(PublicationController.class).getPublicationsByDoi(doi)).withSelfRel())
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @Operation(
            summary = "Retorna uma lista",
            description  = "Retorna uma lista com o ano informado")
    @ApiResponse(
            responseCode = "200",
            description = "Operção realizada com sucesso")
    @ApiResponse(
            responseCode = "404",
            description = " Problema ao retornar a lista do ano informado")
    @GetMapping("/year")
    public ResponseEntity<CollectionModel<EntityModel<Publication>>> getPublicationByYear(
            @RequestParam(required = true) Integer year
    ){
        List<Publication> publications = publicationService.getPublicationYear(year);

        if (!publications.isEmpty()) {
            List<EntityModel<Publication>> publicationModels = publications.stream()
                    .map(publication -> EntityModel.of(publication,
                            linkTo(methodOn(PublicationController.class).getPublicationByID(publication.getId())).withSelfRel(),
                            linkTo(methodOn(PublicationController.class).getAllPublication()).withRel("all-publications")))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(
                    CollectionModel.of(publicationModels,
                            linkTo(methodOn(PublicationController.class).getPublicationByYear(year)).withSelfRel())
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }


    @Operation(
            summary = "Retorna o objeto com ID informado",
            description  = "Retorna o objeto com o determinado ID informado")
    @ApiResponse(
            responseCode = "200",
            description = "Operção realizada com sucesso")
    @ApiResponse(
            responseCode = "404",
            description = " Problema retornar o ID")
    @GetMapping("/id/{id}")
    public ResponseEntity<Publication> getPublicationByID(@PathVariable Long id) {
        Publication p = publicationService.findByID(id);
        return ResponseEntity.ok().body(p);
    }

    @Operation(
            summary = "Retorna a lista de todas as publicações",
            description  = "Retorna todas as prublicações")
    @ApiResponse(
            responseCode = "200",
            description = "Operção realizada com sucesso")
    @ApiResponse(
            responseCode = "404",
            description = " Problema ao retornar as publicações")
    @GetMapping("/list-all-publication")
    public ResponseEntity<List<Publication>> getAllPublication() {
       List<Publication> listPublication= publicationService.getAllPublications();
        return ResponseEntity.ok().body(listPublication);
    }

}