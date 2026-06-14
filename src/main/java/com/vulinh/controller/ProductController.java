package com.vulinh.controller;

import com.vulinh.data.dto.ProductDTO;
import com.vulinh.service.MultipleCollectionEntityGraphDataService;
import com.vulinh.service.MultipleCollectionNPlusOneDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

  private final MultipleCollectionNPlusOneDataService multipleCollectionNPlusOneDataService;
  private final MultipleCollectionEntityGraphDataService multipleCollectionEntityGraphDataService;

  @GetMapping("/multiple-collection-n-plus-one")
  public Page<ProductDTO> getProductsMultipleCollectionNPlusOne(Pageable pageable) {
    RequestLogger.logRequest("/multiple-collection-n-plus-one");

    return multipleCollectionNPlusOneDataService.getProductsMultipleCollectionNPlusOne(pageable);
  }

  @GetMapping("/multiple-collection-entity-graph")
  public Page<ProductDTO> getProductsMultipleCollectionEntityGraph(Pageable pageable) {
    RequestLogger.logRequest("/multiple-collection-entity-graph");

    return multipleCollectionEntityGraphDataService.getProductsMultipleCollectionEntityGraph(
        pageable);
  }
}
