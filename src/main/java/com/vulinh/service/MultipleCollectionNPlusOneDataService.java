package com.vulinh.service;

import com.vulinh.data.dto.ProductDTO;
import com.vulinh.data.mapper.ProductMapper;
import com.vulinh.data.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MultipleCollectionNPlusOneDataService {

  private final ProductRepository productRepository;

  @Transactional
  public Page<ProductDTO> getProductsMultipleCollectionNPlusOne(Pageable pageable) {
    return productRepository.findAll(pageable).map(ProductMapper.INSTANCE::toProductDTO);
  }
}
