package com.assignment.invoiceSchedule.Repository;

import com.assignment.invoiceSchedule.Model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

  @Modifying
  @Transactional
  @Query("update Invoice i set i.schedule_date = :scheduleDate where i.invoice_id = :id")
  void updateScheduleDate(@Param(value = "id") Long id, @Param(value = "scheduleDate") Long scheduleDate);

  @Modifying
  @Transactional
  @Query("update Invoice i set i.schedule_date = null where i.invoice_id = :id")
  void cancelScheduleDate(@Param(value = "id") Long id);

}
