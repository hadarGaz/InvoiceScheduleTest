package com.assignment.invoiceSchedule.ScheduleService;

import com.assignment.invoiceSchedule.Exceptions.*;
import com.assignment.invoiceSchedule.Model.Invoice;
import com.assignment.invoiceSchedule.Model.InvoiceDTO;
import com.assignment.invoiceSchedule.Model.InvoiceStatusDTO;
import com.assignment.invoiceSchedule.Repository.InvoiceRepository;
import com.assignment.invoiceSchedule.RiskService.RiskService;
import java.util.Date;
import java.util.Optional;
import java.util.function.Supplier;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {

  @Autowired
  private InvoiceRepository invoiceRepo;

  @Autowired
  private RiskService riskService;


  public ResponseEntity addInvoice(InvoiceDTO invoiceDTO_to_add)
  {
      ifConditionIsTrueThrow((!riskService.isAmountValidate(invoiceDTO_to_add.getAmount())),() -> new AmountNotValidException());
      Invoice invoice_to_add = convertInvoiceDTOToInvoice(invoiceDTO_to_add);

      invoiceRepo.save(invoice_to_add);
      return new ResponseEntity<>(invoice_to_add, HttpStatus.CREATED);
  }

  public ResponseEntity getInvoiceById(long id)
  {
      Invoice invoice = getInvoiceFromRepoById(id);
      return new ResponseEntity<>(invoice, HttpStatus.OK);
  }

  public ResponseEntity scheduleInvoice(long id, Long schedule_date)
  {
      Invoice invoice = getInvoiceFromRepoById(id);
      scheduleDateHasPassed(schedule_date);

      ifConditionIsTrueThrow((invoice.getSchedule_date() != null),() -> new InvoiceWasScheduledException());

      invoiceRepo.updateScheduleDate(id, schedule_date);
      return new ResponseEntity<>("scheduling invoice id: " + invoice.getInvoice_id() + " to date "+ schedule_date +" was succeeded", HttpStatus.OK);
  }

  public ResponseEntity getScheduleInvoiceById(long id)
  {
      Invoice invoice = getInvoiceFromRepoById(id);
      scheduleDateNullable(invoice.getSchedule_date());

      return new ResponseEntity<>(invoice, HttpStatus.OK);
  }

  public ResponseEntity cancelScheduleInvoiceById(long id)
  {
      Invoice invoice = getInvoiceFromRepoById(id);
      scheduleDateValidation(invoice.getSchedule_date());

      invoiceRepo.cancelScheduleDate(id);
      return getResponseEntity("Cancel schedule invoice id: " + id + " was succeeded", HttpStatus.OK);
  }

  private void scheduleDateValidation(Long schedule_date) {
    scheduleDateNullable(schedule_date);
    scheduleDateHasPassed(schedule_date);
  }

  private void scheduleDateHasPassed(Long schedule_date) {
    ifConditionIsTrueThrow((new Date(schedule_date).before(new Date())),() -> new ScheduleDateHasPassedException());
  }
  private void scheduleDateNullable(Long schedule_date) {
    Optional.ofNullable(schedule_date).orElseThrow(() -> new NoScheduleDateException());
  }

  public ResponseEntity getInvoiceStatus(long id)
  {
    InvoiceStatusDTO invoiceStatusDTO = convertInvoiceToInvoiceStatusDTO(getInvoiceFromRepoById(id));
    return new ResponseEntity<>(invoiceStatusDTO, HttpStatus.OK);
  }

  private ResponseEntity getResponseEntity(String message , HttpStatus http_status) {
    return new ResponseEntity<>(message, http_status);
  }

  private Invoice getInvoiceFromRepoById(long id)
  {
    return invoiceRepo.findById(id).orElseThrow(() -> new InvoiceIdNotFoundException(id));
  }

  private <X extends Throwable> void ifConditionIsTrueThrow(boolean condition, Supplier<? extends X> exceptionSupplier) throws X {
    if (condition) {
      throw exceptionSupplier.get();
    }
  }

  private InvoiceStatusDTO convertInvoiceToInvoiceStatusDTO(Invoice invoice) {
    ModelMapper modelMapper = new ModelMapper();
    InvoiceStatusDTO invoiceStatusDTO = modelMapper.map(invoice, InvoiceStatusDTO.class);
    return invoiceStatusDTO;
  }

  private Invoice convertInvoiceDTOToInvoice(InvoiceDTO invoiceDTO) {
    ModelMapper modelMapper = new ModelMapper();
    Invoice invoice = modelMapper.map(invoiceDTO, Invoice.class);
    return invoice;
  }
}
