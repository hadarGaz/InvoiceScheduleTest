package com.assignment.invoiceSchedule.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvoiceWasScheduledException extends RuntimeException {

  public InvoiceWasScheduledException() {

    super("This invoice was already scheduled");
  }
}
