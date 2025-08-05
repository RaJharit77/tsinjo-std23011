package com.tsinjo.demo.scheduler;

import com.tsinjo.demo.domain.PaymentStatus;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Data;

@Data
public class VolaResponse {
  private String id;
  private BigDecimal amount;
  private String currency;
  private PaymentStatus status;
  private Instant date;
}
