package com.assignment.invoiceSchedule.RiskService;

import org.springframework.stereotype.Service;

@Service
public class RiskService {

  private static final int RISK_SCORE = 80;

  public boolean isAmountValidate(double amount)
  {
    if(RISK_SCORE <= 90 && amount > 20000){
        return false;
    }
    return true;
  }
}
