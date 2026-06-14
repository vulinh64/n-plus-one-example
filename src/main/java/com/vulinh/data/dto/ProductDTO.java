package com.vulinh.data.dto;

import module java.base;

import com.vulinh.utils.CollectionHelper;
import lombok.Builder;
import lombok.With;

@Builder
@With
public record ProductDTO(
    Long id, String name, Set<CategoryDTO> categories, Set<StoreDTO> availableStores) {

  public ProductDTO {
    categories = CollectionHelper.emptySetIfNull(categories);
    availableStores = CollectionHelper.emptySetIfNull(availableStores);
  }
}
