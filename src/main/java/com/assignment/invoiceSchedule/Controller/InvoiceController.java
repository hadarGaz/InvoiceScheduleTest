package com.assignment.invoiceSchedule.Controller;

import com.assignment.invoiceSchedule.Model.InvoiceDTO;
import com.assignment.invoiceSchedule.ScheduleService.InvoiceService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("invoiceSchedule")
public class InvoiceController {

  @Autowired
  private InvoiceService invoiceService;


  @GetMapping("getInvoiceById")
  public ResponseEntity getInvoiceById(@RequestParam("id") Long id)
  {
    return invoiceService.getInvoiceById(id);
  }

  @GetMapping("getInvoiceStatus")
  public ResponseEntity getInvoiceStatus(@RequestParam("id") Long id)
  {
    return invoiceService.getInvoiceStatus(id);
  }

  @PostMapping("addInvoice")
  public ResponseEntity<?> addInvoice(@Valid @RequestBody InvoiceDTO invoiceDTO_to_add)
  {
    return invoiceService.addInvoice(invoiceDTO_to_add);
  }

  @PostMapping("scheduleInvoice")
  public ResponseEntity<?> scheduleInvoice(@RequestParam Long id, @RequestParam("date") Long schedule_date)
  {
    return invoiceService.scheduleInvoice(id, schedule_date);
  }

  @GetMapping("getScheduleInvoiceById")
  public ResponseEntity<?> getScheduleInvoiceById(@RequestParam Long id)
  {
    return invoiceService.getScheduleInvoiceById(id);
  }

  @PostMapping("cancelScheduleInvoiceById")
  public ResponseEntity<?> cancelScheduleInvoiceById(@RequestParam Long id)
  {
    return invoiceService.cancelScheduleInvoiceById(id);
  }
}
