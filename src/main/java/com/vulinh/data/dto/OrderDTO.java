package com.vulinh.data.dto;

import module java.base;

import lombok.Builder;
import lombok.With;

@Builder
@With
public record OrderDTO(Long id, String orderCode, BigDecimal totalPrice) {}
