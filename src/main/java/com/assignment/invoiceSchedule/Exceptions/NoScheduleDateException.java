package com.assignment.invoiceSchedule.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoScheduleDateException extends RuntimeException {

  public NoScheduleDateException() {
    super("There is no schedule date for this invoice");
  }
}
