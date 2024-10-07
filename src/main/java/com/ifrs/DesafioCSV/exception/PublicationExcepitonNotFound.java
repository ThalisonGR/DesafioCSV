package com.ifrs.DesafioCSV.exception;

import com.ifrs.DesafioCSV.domain.Publication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PublicationExcepitonNotFound  extends RuntimeException{
    public PublicationExcepitonNotFound(){
        super(String.format("Não existe este DOI:" ));
    }
}
