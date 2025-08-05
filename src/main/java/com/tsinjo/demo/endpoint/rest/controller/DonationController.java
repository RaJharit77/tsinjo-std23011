package com.tsinjo.demo.endpoint.rest.controller;

import com.tsinjo.demo.domain.*;
import com.tsinjo.demo.service.DonationService;
import com.tsinjo.demo.service.HelpService;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class DonationController {
  private final DonationService donationService;
  private final HelpService helpService;

  @GetMapping("/")
  public String home(Model model) {
    List<Donation> donations = donationService.getAllDonations();
    List<Help> helps = helpService.getAllHelps();

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
  public String donate(
      @RequestParam String fullName,
      @RequestParam String email,
      @RequestParam BigDecimal amount,
      @RequestParam String method) {

    donationService.createDonation(fullName, email, amount, method);
    return "redirect:/";
  }
}
