package com.tsinjo.demo.service;

import com.tsinjo.demo.domain.PaymentStatus;
import com.tsinjo.demo.repository.PaymentRepository;
import com.tsinjo.demo.scheduler.VolaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class VolaService {
    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate;

    @Value("${vola.api.url}")
    private String VOLA_API_URL;

    @Value("${vola.api.key}")
    private String API_KEY;

    @Scheduled(fixedRate = 30000)
    public void checkPendingPayments() {
        paymentRepository
                .findByStatus(PaymentStatus.VERIFYING)
                .forEach(payment -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Authorization", "Bearer " + API_KEY);
                    HttpEntity<?> entity = new HttpEntity<>(headers);

                    try {
                        ResponseEntity<VolaResponse> response = restTemplate.exchange(
                                VOLA_API_URL + payment.getId(),
                                HttpMethod.GET,
                                entity,
                                VolaResponse.class
                        );

                        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                            payment.setStatus(response.getBody().getStatus());
                            paymentRepository.save(payment);
                            log.info("Payment {} updated to {}", payment.getId(), response.getBody().getStatus());
                        } else {
                            log.warn("Failed to verify payment {}: {}", payment.getId(), response.getStatusCode());
                        }
                    } catch (Exception e) {
                        log.error("Error verifying payment {}: {}", payment.getId(), e.getMessage());
                    }
                });
    }
}