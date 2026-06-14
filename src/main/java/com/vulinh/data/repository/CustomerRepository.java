package com.vulinh.data.repository;

import com.vulinh.data.entity.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends BaseRepository<Customer, Long> {}
