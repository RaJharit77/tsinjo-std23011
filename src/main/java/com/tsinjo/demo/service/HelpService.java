package com.tsinjo.demo.service;

import com.tsinjo.demo.domain.Help;
import com.tsinjo.demo.repository.HelpRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HelpService {
  private final HelpRepository helpRepository;

  public List<Help> getAllHelps() {
    return helpRepository.findAll();
  }
}
