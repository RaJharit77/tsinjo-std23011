package com.tsinjo.demo.service;

import com.tsinjo.demo.domain.Payment;
import com.tsinjo.demo.domain.PaymentStatus;
import com.tsinjo.demo.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentVerificationService {
  private final PaymentRepository paymentRepository;
  private final VolaApiClient volaApiClient;

  @Scheduled(fixedRate = 30000)
  @Transactional
  public void verifyPendingPayments() {
    List<Payment> pendingPayments = paymentRepository.findByStatus(PaymentStatus.VERIFYING);

    if (pendingPayments.isEmpty()) {
      log.info("Aucun paiement en attente de vérification");
      return;
    }

    log.info("Vérification de {} paiement(s) en attente", pendingPayments.size());
    pendingPayments.forEach(this::verifySinglePayment);
  }

  private void verifySinglePayment(Payment payment) {
    try {
      PaymentStatus status = volaApiClient.getPaymentStatus(payment.getId());
      payment.setStatus(status);
      paymentRepository.save(payment);
      log.info("Statut mis à jour pour {}: {}", payment.getId(), status);

    } catch (Exception e) {
      log.error("Échec de vérification pour {}: {}", payment.getId(), e.getMessage());
    }
  }
}
