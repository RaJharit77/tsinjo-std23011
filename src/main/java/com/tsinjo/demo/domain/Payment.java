package com.tsinjo.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Data;

@Entity
@Data
public class Payment {
  @Id private String id;
  private Instant date;
  private BigDecimal amount;
  private String method;

  @Enumerated(EnumType.STRING)
  private PaymentStatus status;
}
