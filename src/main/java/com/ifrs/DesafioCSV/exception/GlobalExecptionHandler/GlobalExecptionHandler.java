package com.ifrs.DesafioCSV.exception.GlobalExecptionHandler;
import com.ifrs.DesafioCSV.exception.Response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExecptionHandler extends  ResponseEntityExceptionHandler{

    /*
        Interessante saber para quando ocorrer "Error creating bean with name 'handlerExceptionResolver' defined in class path resource"
        https://stackoverflow.com/questions/51991992/getting-ambiguous-exceptionhandler-method-mapped-for-methodargumentnotvalidexce/74552716#74552716
        Remova @ExceptionHandleranotações dos métodos que manipulam as mesmas exceções como em ResponseEntityExceptionHandler
     */

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String , String> validacaoError = new HashMap<>();
        List<ObjectError> validacaoErrorLista = ex.getBindingResult().getAllErrors();

        validacaoErrorLista.forEach((error) ->{
            String nome_do_campo = ((FieldError)error).getField();
            String validacao_mensagem = error.getDefaultMessage();
            validacaoError.put(nome_do_campo, validacao_mensagem);
        });
        return new ResponseEntity<>(validacaoError,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> hadleGlobalExcpetion(Exception exception , WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse(
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }




}
