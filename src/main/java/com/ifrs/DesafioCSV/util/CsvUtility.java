package com.ifrs.DesafioCSV.util;

import com.ifrs.DesafioCSV.domain.Publication;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvUtility {
    public static String TYPE = "text/csv";
    static String[] HEADERs = {
            "Title",
            "Authors",
            "Journal",
            "Year",
            "Published",
            "E-published",
            "Volume",
            "Issue",
            "Pages",
            "DOI",
            "PMID",
            "Labels",
            "Qualifiers",
            "IUID",
            "URL",
            "DOI URL",
            "PubMed URL" };
    public static boolean hasCsvFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }
    public static List<Publication> csvToStuList(InputStream is) {
        try (BufferedReader bReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(bReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            List<Publication> publicationList = new ArrayList<Publication>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                    Publication publication = new Publication();
                    publication.setTitle(csvRecord.get("Title"));
                    publication.setAuthors(csvRecord.get("Authors"));
                    publication.setJournal(csvRecord.get("Journal"));
                    publication.setP_year(Integer.parseInt(csvRecord.get("Year")));
                    publication.setPublished(csvRecord.get("Published"));
                    publication.setEPublished(csvRecord.get("E-published"));
                    publication.setVolume(csvRecord.get("Volume"));
                    publication.setIssue(csvRecord.get("Issue"));
                    publication.setPages(csvRecord.get("Pages"));
                    publication.setDoi(csvRecord.get("DOI"));
                    publication.setPmid(csvRecord.get("PMID"));
                    publication.setLabels(csvRecord.get("Labels"));
                    publication.setQualifiers(csvRecord.get("Qualifiers"));
                    publication.setIuid(csvRecord.get("IUID"));
                    publication.setUrl(csvRecord.get("URL"));
                    publication.setDoiUrl(csvRecord.get("DOI URL"));
                    publication.setPubMedUrl(csvRecord.get("PubMed URL"));
                    publicationList.add(publication);
            }
            return publicationList;
        } catch (IOException e) {
            throw new RuntimeException("CSV data is failed to parse: " + e.getMessage());
        }
    }
}
