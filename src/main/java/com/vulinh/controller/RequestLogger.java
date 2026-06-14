package com.vulinh.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class RequestLogger {

  static void logRequest(String endpoint) {
    log.info("Running request {}", endpoint);
  }
}
