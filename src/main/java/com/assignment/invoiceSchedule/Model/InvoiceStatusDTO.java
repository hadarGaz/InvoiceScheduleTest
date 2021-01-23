package com.assignment.invoiceSchedule.Model;

public class InvoiceStatusDTO {

  private Long invoice_id;
  private double amount;
  private boolean is_it_scheduled;

  public InvoiceStatusDTO() {
  }

  public InvoiceStatusDTO(Long invoice_id, double amount, Long schedule_date) {
    this.invoice_id = invoice_id;
    this.amount = amount;
    if(schedule_date != null)
      is_it_scheduled = true;
    else
      is_it_scheduled = false;
  }

  public Long getInvoice_id() {
    return invoice_id;
  }

  public void setInvoice_id(Long invoice_id) {
    this.invoice_id = invoice_id;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public boolean isIs_it_scheduled() {
    return is_it_scheduled;
  }

  public void setIs_it_scheduled(boolean is_it_scheduled) {
    this.is_it_scheduled = is_it_scheduled;
  }
}
