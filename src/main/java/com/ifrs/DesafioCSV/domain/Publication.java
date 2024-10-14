package com.ifrs.DesafioCSV.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Publication")
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(name = "Title", example = "Title 1", required = true)
    @NotNull
    @Column(length = 1000)
    private String title;

    @Schema(name = "Author", example = "Author A", required = true)
    @NotNull
    @Column(length = 1000)
    private String authors;

    @Schema(name = "Journal", example = "Journal Name", required = true)
    @NotNull
    @Column(length = 1000)
    private String journal;

    @Schema(name = "Year", example = "2023", required = true)
    @NotNull
    @Column(length = 1000)
    private Integer p_year;

    @Schema(name = "Published", example = "Published date", required = true)
    @NotNull
    @Column(length = 1000)
    private String published;

    @Schema(name = "E-Published", example = "E-Published date", required = true)
    @NotNull
    @Column(length = 1000)
    private String ePublished;

    @Schema(name = "Volume", example = "Volume 1", required = true)
    @NotNull
    @Column(length = 1000)
    private String volume;

    @Schema(name = "Issue", example = "Issue 1", required = true)
    @NotNull
    @Column(length = 1000)
    private String issue;

    @Schema(name = "Pages", example = "Pages 10-20", required = true)
    @NotNull
    @Column(length = 1000)
    private String pages;

    @Schema(name = "DOI", example = "10.1234/abcde1", required = true)
    @NotNull
    @Column(length = 1000)
    private String doi;

    @Schema(name = "Pmid", example = "PMID123456", required = true)
    @NotNull
    @Column(length = 1000)
    private String pmid;

    @Schema(name = "Labels", example = "Label 1", required = true)
    @NotNull
    @Column(length = 1000)
    private String labels;

    @Schema(name = "Qualifiers", example = "Qualifiers 1", required = true)
    @NotNull
    @Column(length = 1000)
    private String qualifiers;

    @Schema(name = "Iuid", example = "IUID123456", required = true)
    @NotNull
    @Column(length = 1000)
    private String iuid;

    @Schema(name = "Url", example = "https://example.com", required = true)
    @NotNull
    @Column(length = 1000)
    private String url;

    @Schema(name = "doiUrl", example = "https://doi.org/10.1234/abcde1", required = true)
    @NotNull
    @Column(length = 1000)
    private String doiUrl;

    @Schema(name = "doiUrl", example = "https://pubmed.ncbi.nlm.nih.gov/123456", required = true)
    @NotNull
    @Column(length = 1000)
    private String pubMedUrl;



}
