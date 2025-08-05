package com.tsinjo.demo.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Help {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne private Beneficiary beneficiary;

  @OneToOne(cascade = CascadeType.ALL)
  private Payment payment;

  private String accidentDescription;
}
