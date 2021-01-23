package com.assignment.invoiceSchedule.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AmountNotValidException extends RuntimeException {

  public AmountNotValidException() {

    super("Amount not valid for this user");
  }
}
