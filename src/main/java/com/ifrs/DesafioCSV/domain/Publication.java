package com.ifrs.DesafioCSV.domain;

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


    @NotNull
    @Column(length = 1000)
    private String title;

    @NotNull
    @Column(length = 1000)
    private String authors;

    @NotNull
    @Column(length = 1000)
    private String journal;

    @NotNull
    @Column(length = 1000)
    private Integer p_year;

    @NotNull
    @Column(length = 1000)
    private String published;

    @NotNull
    @Column(length = 1000)
    private String ePublished;

    @NotNull
    @Column(length = 1000)
    private String volume;

    @NotNull
    @Column(length = 1000)
    private String issue;

    @NotNull
    @Column(length = 1000)
    private String pages;

    @NotNull
    @Column(length = 1000)
    private String doi;

    @NotNull
    @Column(length = 1000)
    private String pmid;

    @NotNull
    @Column(length = 1000)
    private String labels;

    @NotNull
    @Column(length = 1000)
    private String qualifiers;

    @NotNull
    @Column(length = 1000)
    private String iuid;

    @NotNull
    @Column(length = 1000)
    private String url;

    @NotNull
    @Column(length = 1000)
    private String doiUrl;

    @NotNull
    @Column(length = 1000)
    private String pubMedUrl;



}
