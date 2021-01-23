package com.assignment.invoiceSchedule;

import static net.bytebuddy.matcher.ElementMatchers.is;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.assignment.invoiceSchedule.Exceptions.ErrorDetails;
import com.assignment.invoiceSchedule.Model.Invoice;
import com.assignment.invoiceSchedule.Model.InvoiceDTO;
import com.assignment.invoiceSchedule.Model.InvoiceStatusDTO;
import com.assignment.invoiceSchedule.Repository.InvoiceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InvoiceScheduleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class InvoiceScheduleApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@Autowired
	private InvoiceRepository invoiceRepo;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	private String invoice_id = "1";

	private InvoiceDTO genericInvoiceDTO = new InvoiceDTO(2300.00, new Long(1611014400), "dfdf", new Long(1611014400),
			"fearf4", "dhd@mmc.com");

	@Test
  public void addInvoice_whenAmountNotValid_thenReturnBadRequest() {
		InvoiceDTO invoiceDTO =
				new InvoiceDTO(23000.00, new Long(1611014400), "dfdf", new Long(1611014400),
						"fearf4", "dhd@mmc.com");

		HttpEntity<?> request = new HttpEntity<>(invoiceDTO);
		HttpEntity<String> response = restTemplate.exchange(
				getRootUrl() + "/invoiceSchedule/addInvoice",
				HttpMethod.POST,
				request,
				String.class);

		assertEquals(((ResponseEntity) response).getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(response.getBody(), "Amount not valid for this user");
	}

	@Test
	public void addInvoice_withValidAmount_thenReturnCreated(){


		HttpEntity<Invoice> response = addValidInvoice();
		Invoice invoice = response.getBody();


		InvoiceDTO invoiceDTO1 = convertInvoiceToInvoiceDto(invoice);

		assertEquals(((ResponseEntity) response).getStatusCode(), HttpStatus.CREATED);
		assertEquals(genericInvoiceDTO, invoiceDTO1);
		assertNotNull(invoice.getInvoice_id());
	}

	private InvoiceDTO convertInvoiceToInvoiceDto(Invoice invoice) {
		ModelMapper modelMapper = new ModelMapper();
		InvoiceDTO invoiceDTO = modelMapper.map(invoice, InvoiceDTO.class);
		return invoiceDTO;
	}

	private HttpEntity<Invoice> addValidInvoice()
	{
		HttpEntity<?> request = new HttpEntity<>(genericInvoiceDTO);
		HttpEntity<Invoice> response = restTemplate.exchange(
				getRootUrl() + "/invoiceSchedule/addInvoice",
				HttpMethod.POST,
				request,
				Invoice.class);

		return response;
	}

  @Test
  public void getInvoiceById_idDoesntExists_thenThrowNotFound() {

	  idDoesntExists_thenThrowNotFound("/invoiceSchedule/getInvoiceById?id=1", HttpMethod.GET);
  }

  @Test
  public void getInvoiceById_idExists_thenReturnOK(){

		invoiceRepo.deleteAll();
	  	Invoice invoice = addValidInvoice().getBody();

	  	HttpEntity<Invoice> response = httpRequest("/invoiceSchedule/getInvoiceById?id=" + invoice.getInvoice_id(),HttpMethod.GET, Invoice.class);

		assertEquals(((ResponseEntity) response).getStatusCode(), HttpStatus.OK);
	  	assertEquals(response.getBody(), invoice);
	}

	private void idDoesntExists_thenThrowNotFound(String url,HttpMethod httpMethod){
		invoiceRepo.deleteAll();

		HttpEntity<ErrorDetails> response = httpRequest(url,httpMethod, ErrorDetails.class);

		assertEquals(((ResponseEntity) response).getStatusCode(), HttpStatus.NOT_FOUND);
		assertEquals(response.getBody().getMessage(), "Invoice with id "+ invoice_id +" not found");
	}

	private HttpEntity httpRequest(String url,HttpMethod httpMethod, Class responseType)
	{
		return restTemplate.exchange(
				getRootUrl()+url,
				httpMethod,
				new HttpEntity<>(new HttpHeaders()),
				responseType);
	}

	@Test
	public void getInvoiceStatus_idDoesntExists_thenThrowNotFound(){

		idDoesntExists_thenThrowNotFound("/invoiceSchedule/getInvoiceStatus?id=" + invoice_id, HttpMethod.GET);
	}

	@Test
	public void getInvoiceStatus_idExists_withScheduleTrue_thenReturnOK(){
		invoiceRepo.deleteAll();
		Invoice invoice = addValidInvoice().getBody();
		//need to schedule ???????????????
	}

	@Test
	public void getInvoiceStatus_idExists_withScheduleFalse_thenReturnOK(){
		invoiceRepo.deleteAll();
		Invoice invoice = addValidInvoice().getBody();

		HttpEntity<InvoiceStatusDTO> response = httpRequest("/invoiceSchedule/getInvoiceStatus?id=" + invoice.getInvoice_id(),HttpMethod.GET, InvoiceStatusDTO.class);
		assertEquals(((ResponseEntity) response).getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody().isIs_it_scheduled(), false);
	}

	@Test
	public void getScheduleInvoiceById_idDoesntExists_thenThrowNotFound(){
		idDoesntExists_thenThrowNotFound("/invoiceSchedule/getScheduleInvoiceById?id=" + invoice_id, HttpMethod.GET);
	}

	@Test
	public void getScheduleInvoiceById_idExists_noScheduleDate_thenThrowBadRequest(){

	}

	@Test
	public void getScheduleInvoiceById_idExists_withScheduleDate_thenReturnOK(){

	}

	@Test
	public void scheduleInvoiceById_idDoesntExists_thenThrowNotFound(){
		idDoesntExists_thenThrowNotFound("/invoiceSchedule/scheduleInvoice?date=32&id="+ invoice_id,HttpMethod.POST);
	}

	@Test
	public void scheduleInvoiceById_scheduleDateHasPassed_thenThrowBadRequest(){
		invoiceRepo.deleteAll();

		Invoice invoice = addValidInvoice().getBody();
		Long schedule_date = getDate(-1);

		HttpEntity<ErrorDetails> response = httpRequest("/invoiceSchedule/scheduleInvoice?date="+schedule_date+"&id=" + invoice.getInvoice_id(),HttpMethod.POST, ErrorDetails.class);

		assertEquals(((ResponseEntity) response).getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(response.getBody().getMessage(), "The invoice schedule date has passed");
	}

	private Long getDate(int amount)
	{
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, amount);
		return cal.getTimeInMillis();
	}

	@Test
	public void scheduleInvoiceById_invoiceWasAlreadyScheduled_thenThrowBadRequest(){
		invoiceRepo.deleteAll();

		Invoice invoice = addValidInvoice().getBody();
		Long schedule_date = getDate(1);
		invoice.setSchedule_date(schedule_date);
		invoiceRepo.save(invoice);

		HttpEntity<ErrorDetails> response = httpRequest("/invoiceSchedule/scheduleInvoice?date="+schedule_date+"&id=" + invoice.getInvoice_id(),HttpMethod.POST, ErrorDetails.class);
		assertEquals(((ResponseEntity) response).getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(response.getBody().getMessage(), "This invoice was already scheduled");
	}

	@Test
	public void scheduleInvoiceById_validSchedule_thenReturnOK(){
		invoiceRepo.deleteAll();

		Invoice invoice = addValidInvoice().getBody();
		Long schedule_date = getDate(1);

		HttpEntity<String> response = httpRequest("/invoiceSchedule/scheduleInvoice?date="+schedule_date+"&id=" + invoice.getInvoice_id(),HttpMethod.POST, String.class);

		assertEquals(((ResponseEntity) response).getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody(), "scheduling invoice id: " + invoice.getInvoice_id() + " to date "+ schedule_date +" was succeeded");
	}

	@Test
	public void cancelScheduleInvoiceById_idDoesntExists_thenThrowNotFound(){
		idDoesntExists_thenThrowNotFound("/invoiceSchedule/cancelScheduleInvoiceById?&id="+ invoice_id,HttpMethod.POST);
	}

	@Test
	public void cancelScheduleInvoiceById_noScheduleDate_thenThrowNotFound(){
		invoiceRepo.deleteAll();

		Invoice invoice = addValidInvoice().getBody();

		HttpEntity<ErrorDetails> response = httpRequest("/invoiceSchedule/cancelScheduleInvoiceById?id=" + invoice.getInvoice_id(),HttpMethod.POST, ErrorDetails.class);

		assertEquals(((ResponseEntity) response).getStatusCode(), HttpStatus.NOT_FOUND);
		assertEquals(response.getBody().getMessage(), "There is no schedule date for this invoice");
	}

	@Test
	public void cancelScheduleInvoiceById_scheduleDateHasPassed_thenThrowNotFound(){
		invoiceRepo.deleteAll();

		Invoice invoice = addValidInvoice().getBody();
		Long schedule_date = getDate(-1);
		invoice.setSchedule_date(schedule_date);
		invoiceRepo.save(invoice);

		HttpEntity<ErrorDetails> response = httpRequest("/invoiceSchedule/cancelScheduleInvoiceById?id=" + invoice.getInvoice_id(),HttpMethod.POST, ErrorDetails.class);

		assertEquals(((ResponseEntity) response).getStatusCode(), HttpStatus.BAD_REQUEST);
		assertEquals(response.getBody().getMessage(), "The invoice schedule date has passed");
	}

	@Test
	public void cancelScheduleInvoiceById_validSchedule_thenReturnOK(){
		invoiceRepo.deleteAll();

		Invoice invoice = addValidInvoice().getBody();
		Long schedule_date = getDate(1);
		invoice.setSchedule_date(schedule_date);
		invoiceRepo.save(invoice);

		HttpEntity<String> response = httpRequest("/invoiceSchedule/cancelScheduleInvoiceById?id=" + invoice.getInvoice_id(),HttpMethod.POST, String.class);

		assertEquals(((ResponseEntity) response).getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody(), "Cancel schedule invoice id: "+ invoice.getInvoice_id() +" was succeeded");
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}



}
