package com.vulinh.controller;

import com.vulinh.data.dto.CustomerDTO;
import com.vulinh.service.BatchFetchDataService;
import com.vulinh.service.DiyDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

  private final BatchFetchDataService batchFetchDataService;
  private final DiyDataService diyDataService;

  @GetMapping("/batch-fetch")
  public Page<CustomerDTO> getCustomersBatchFetch(Pageable pageable) {
    RequestLogger.logRequest("/batch-fetch");

    return batchFetchDataService.getCustomersBatchFetch(pageable);
  }

  @GetMapping("/diy")
  public Page<CustomerDTO> getCustomersDiy(Pageable pageable) {
    RequestLogger.logRequest("/diy");

    return diyDataService.getCustomersDiy(pageable);
  }
}
