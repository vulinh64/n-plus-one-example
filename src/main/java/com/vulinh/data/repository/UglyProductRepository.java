package com.vulinh.data.repository;

import com.vulinh.data.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface UglyProductRepository extends ProductRepository {

  @Override
  @NonNull
  @EntityGraph(attributePaths = {"categories", "availableStores"})
  Page<Product> findAll(@NonNull Pageable pageable);
}
