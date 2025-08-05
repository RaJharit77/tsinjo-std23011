package com.tsinjo.demo.service;

import com.tsinjo.demo.domain.*;
import com.tsinjo.demo.repository.DonationRepository;
import com.tsinjo.demo.repository.DonorRepository;
import com.tsinjo.demo.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DonationService {
  private final DonationRepository donationRepository;
  private final DonorRepository donorRepository;
  private final PaymentRepository paymentRepository;

  public List<Donation> getAllDonations() {
    return donationRepository.findAll();
  }

  @Transactional
  public void createDonation(String fullName, String email, BigDecimal amount, String method) {
    Donor donor =
        donorRepository
            .findByEmail(email)
            .orElseGet(
                () -> {
                  Donor newDonor = new Donor();
                  newDonor.setFullName(fullName);
                  newDonor.setEmail(email);
                  return donorRepository.save(newDonor);
                });

    Payment payment = new Payment();
    payment.setId(generatePaymentId());
    payment.setDate(Instant.now());
    payment.setAmount(amount);
    payment.setMethod(method);
    payment.setStatus(PaymentStatus.VERIFYING);
    paymentRepository.save(payment);

    Donation donation = new Donation();
    donation.setDonor(donor);
    donation.setPayment(payment);
    donationRepository.save(donation);
  }

  @Transactional
  public void createDonation(Donor donor, Payment payment) {
    Donor savedDonor = donorRepository.save(donor);
    Payment savedPayment = paymentRepository.save(payment);

    Donation donation = new Donation();
    donation.setDonor(savedDonor);
    donation.setPayment(savedPayment);
    donationRepository.save(donation);
  }

  private String generatePaymentId() {
    return "MP" + Instant.now().toString().replaceAll("[^0-9]", "").substring(0, 14);
  }
}
