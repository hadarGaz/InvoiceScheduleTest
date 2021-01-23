package com.assignment.invoiceSchedule.Exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({ScheduleDateHasPassedException.class, AmountNotValidException.class, InvoiceWasScheduledException.class})
  public ResponseEntity<?> badRequestException(RuntimeException ex, WebRequest request) {
    return getResponseEntity(ex, request, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({InvoiceIdNotFoundException.class, NoScheduleDateException.class})
  public ResponseEntity<?> resourceNotFoundException(RuntimeException ex, WebRequest request) {
    return getResponseEntity(ex, request, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
    return getResponseEntity(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private ResponseEntity<?> getResponseEntity(Exception ex, WebRequest request, HttpStatus internalServerError) {
    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(errorDetails, internalServerError);
  }
}
