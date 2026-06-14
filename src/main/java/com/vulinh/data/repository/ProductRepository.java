package com.vulinh.data.repository;

import com.vulinh.data.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {}
