package com.assignment.invoiceSchedule.Model;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;


@Entity
public class Invoice {

  private Long invoice_id;
  private double amount;

  private Long creation_date;
  private String description;
  private Long due_date;
  private String company_name;
  private String customer_email;
  private Long schedule_date;


  @Id
  @GeneratedValue
  public Long getInvoice_id() {
    return invoice_id;
  }

  public void setInvoice_id(Long invoice_id) {
    this.invoice_id = invoice_id;
  }

  public double getAmount() {
    return amount;
  }


  public Long getSchedule_date() {
    return schedule_date;
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


  public void setSchedule_date(Long schedule_date) {
    this.schedule_date = schedule_date;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Invoice invoice = (Invoice) o;
    return Double.compare(invoice.amount, amount) == 0 &&
        Objects.equals(invoice_id, invoice.invoice_id) &&
        Objects.equals(creation_date, invoice.creation_date) &&
        Objects.equals(description, invoice.description) &&
        Objects.equals(due_date, invoice.due_date) &&
        Objects.equals(company_name, invoice.company_name) &&
        Objects.equals(customer_email, invoice.customer_email) &&
        Objects.equals(schedule_date, invoice.schedule_date);
  }
}
