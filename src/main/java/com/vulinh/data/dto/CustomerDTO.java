package com.vulinh.data.dto;

import module java.base;

import com.vulinh.utils.CollectionHelper;
import lombok.Builder;
import lombok.With;

@Builder
@With
public record CustomerDTO(Long id, String name, String email, List<OrderDTO> orders) {

  public CustomerDTO {
    orders = CollectionHelper.emptyListIfNull(orders);
  }
}
