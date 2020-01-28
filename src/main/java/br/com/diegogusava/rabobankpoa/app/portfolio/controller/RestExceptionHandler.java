package br.com.diegogusava.rabobankpoa.app.portfolio.controller;

import br.com.diegogusava.rabobankpoa.app.portfolio.exception.AccountAccessDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(AccountAccessDeniedException.class)
    public ResponseEntity accountAccessDeniedException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

}
