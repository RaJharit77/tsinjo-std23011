package com.tsinjo.demo.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Donation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne private Donor donor;

  @OneToOne(cascade = CascadeType.ALL)
  private Payment payment;
}
