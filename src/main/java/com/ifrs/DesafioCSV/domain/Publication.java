package com.ifrs.DesafioCSV.domain;

import jakarta.persistence.*;
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

    @Column(length = 1000)
    private String title;

    @Column(length = 1000)
    private String authors;

    @Column(length = 1000)
    private String journal;

    @Column(length = 1000)
    private Integer p_year;

    @Column(length = 1000)
    private String published;

    @Column(length = 1000)
    private String ePublished;

    @Column(length = 1000)
    private String volume;

    @Column(length = 1000)
    private String issue;

    @Column(length = 1000)
    private String pages;

    @Column(length = 1000)
    private String doi;

    @Column(length = 1000)
    private String pmid;

    @Column(length = 1000)
    private String labels;

    @Column(length = 1000)
    private String qualifiers;

    @Column(length = 1000)
    private String iuid;

    @Column(length = 1000)
    private String url;

    @Column(length = 1000)
    private String doiUrl;

    @Column(length = 1000)
    private String pubMedUrl;

}
