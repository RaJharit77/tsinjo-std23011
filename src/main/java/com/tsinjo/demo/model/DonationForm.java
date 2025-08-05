package com.tsinjo.demo.model;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class DonationForm {
  private String fullName;
  private String email;
  private BigDecimal amount;
  private String method;
}
