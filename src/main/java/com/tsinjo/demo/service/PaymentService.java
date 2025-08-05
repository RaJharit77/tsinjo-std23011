package com.tsinjo.demo.service;

import com.tsinjo.demo.domain.Beneficiary;
import com.tsinjo.demo.domain.PaymentStatus;
import com.tsinjo.demo.repository.PaymentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
  private final PaymentRepository paymentRepository;
  private final VolaClient volaClient;

  @Scheduled(fixedRate = 30000)
  public void verifyPendingPayments() {
    List<Beneficiary.Payment> pendingPayments =
        paymentRepository.findByStatus(PaymentStatus.VERIFYING);

    if (pendingPayments.isEmpty()) {
      log.info("Aucun paiement en attente de vérification");
      return;
    }

    log.info("Vérification de {} paiements en attente", pendingPayments.size());
    pendingPayments.forEach(
        payment -> {
          PaymentStatus newStatus = volaClient.getPaymentStatus(payment.getId());
          payment.setStatus(newStatus);
          paymentRepository.save(payment);
          log.debug("Paiement {} mis à jour: {}", payment.getId(), newStatus);
        });
  }
}
