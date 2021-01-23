package com.assignment.invoiceSchedule.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class InvoiceIdNotFoundException extends RuntimeException {

  public InvoiceIdNotFoundException(Long id) {
    super("Invoice with id " + id + " not found");
  }
}
