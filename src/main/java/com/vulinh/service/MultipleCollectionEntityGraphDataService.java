package com.vulinh.service;

import com.vulinh.data.dto.ProductDTO;
import com.vulinh.data.mapper.ProductMapper;
import com.vulinh.data.repository.UglyProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MultipleCollectionEntityGraphDataService {

  private final UglyProductRepository uglyProductRepository;

  @Transactional
  public Page<ProductDTO> getProductsMultipleCollectionEntityGraph(Pageable pageable) {
    return uglyProductRepository.findAll(pageable).map(ProductMapper.INSTANCE::toProductDTO);
  }
}
