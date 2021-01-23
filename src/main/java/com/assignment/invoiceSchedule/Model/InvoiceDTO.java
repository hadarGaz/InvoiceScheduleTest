package com.assignment.invoiceSchedule.Model;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

public class InvoiceDTO {

  private double amount;
  private Long creation_date;
  private String description;
  private Long due_date;
  private String company_name;
  private String customer_email;

  public InvoiceDTO() {
  }

  public InvoiceDTO(double amount, Long creation_date, String description, Long due_date,
                    String company_name, String customer_email) {
    this.amount = amount;
    this.creation_date = creation_date;
    this.description = description;
    this.due_date = due_date;
    this.company_name = company_name;
    this.customer_email = customer_email;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public Long getCreation_date() {
    return creation_date;
  }

  public void setCreation_date(Long creation_date) {
    this.creation_date = creation_date;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Long getDue_date() {
    return due_date;
  }

  public void setDue_date(Long due_date) {
    this.due_date = due_date;
  }

  public String getCompany_name() {
    return company_name;
  }

  public void setCompany_name(String company_name) {
    this.company_name = company_name;
  }

  public String getCustomer_email() {
    return customer_email;
  }

  public void setCustomer_email(String customer_email) {
    this.customer_email = customer_email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InvoiceDTO that = (InvoiceDTO) o;
    return Double.compare(that.amount, amount) == 0 &&
        Objects.equals(creation_date, that.creation_date) &&
        Objects.equals(description, that.description) &&
        Objects.equals(due_date, that.due_date) &&
        Objects.equals(company_name, that.company_name) &&
        Objects.equals(customer_email, that.customer_email);
  }
}
