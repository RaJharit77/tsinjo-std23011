package com.tsinjo.demo.conf;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.tsinjo.demo.domain.Payment;
import com.tsinjo.demo.domain.PaymentStatus;
import com.tsinjo.demo.repository.PaymentRepository;
import com.tsinjo.demo.scheduler.VolaResponse;
import com.tsinjo.demo.service.VolaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class VolaServiceTest {
  @Mock private PaymentRepository paymentRepository;
  @Mock private RestTemplate restTemplate;
  @InjectMocks private VolaService volaService;

  @Test
  void whenPaymentSucceeds_thenUpdateStatus() {
    Payment payment = new Payment();
    payment.setId("MP123");
    payment.setStatus(PaymentStatus.VERIFYING);

    when(restTemplate.getForObject(anyString(), eq(VolaResponse.class)))
        .thenReturn(new VolaResponse("SUCCEEDED"));

    volaService.checkPendingPayments();

    assertEquals(PaymentStatus.SUCCEEDED, payment.getStatus());
    verify(paymentRepository).save(payment);
  }
}
