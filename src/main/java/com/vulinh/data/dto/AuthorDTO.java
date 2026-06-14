package com.vulinh.data.dto;

import module java.base;

import com.vulinh.utils.CollectionHelper;
import lombok.Builder;
import lombok.With;

@Builder
@With
public record AuthorDTO(Long id, String name, List<BookDTO> books) {

  public AuthorDTO {
    books = CollectionHelper.emptyListIfNull(books);
  }
}
