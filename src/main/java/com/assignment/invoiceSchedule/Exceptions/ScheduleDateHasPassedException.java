package com.assignment.invoiceSchedule.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ScheduleDateHasPassedException extends RuntimeException {

  public ScheduleDateHasPassedException() {
    super("The invoice schedule date has passed");
  }
}
