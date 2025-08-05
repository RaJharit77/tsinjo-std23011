package com.tsinjo.demo.endpoint.rest.controller;

import com.tsinjo.demo.domain.*;
import com.tsinjo.demo.model.DonationForm;
import com.tsinjo.demo.repository.DonationRepository;
import com.tsinjo.demo.repository.HelpRepository;
import com.tsinjo.demo.service.DonationService;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TsinjoController {
  private final DonationRepository donationRepository;
  private final HelpRepository helpRepository;
  private final DonationService donationService;

  @GetMapping("/")
  public String home(Model model) {
    List<Donation> donations = donationRepository.findAll();
    List<Help> helps = helpRepository.findAll();

    List<Object> timeline =
        Stream.concat(donations.stream(), helps.stream())
            .sorted(
                Comparator.comparing(
                        o ->
                            o instanceof Donation
                                ? ((Donation) o).getPayment().getDate()
                                : ((Help) o).getPayment().getDate())
                    .reversed())
            .toList();

    model.addAttribute("timeline", timeline);
    return "index";
  }

  @PostMapping("/donate")
  public String createDonation(DonationForm form) {
    Donor donor = new Donor();
    donor.setFullName(form.getFullName());
    donor.setEmail(form.getEmail());

    Payment payment = new Payment();
    payment.setId(generatePaymentId());
    payment.setDate(Instant.now());
    payment.setAmount(form.getAmount());
    payment.setMethod(form.getMethod());
    payment.setStatus(PaymentStatus.VERIFYING);

    donationService.createDonation(donor, payment);
    return "redirect:/";
  }

  private String generatePaymentId() {
    return "MP" + Instant.now().toString().replaceAll("[^0-9]", "").substring(0, 14);
  }
}
