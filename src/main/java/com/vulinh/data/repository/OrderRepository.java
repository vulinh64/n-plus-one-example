package com.vulinh.data.repository;

import com.vulinh.data.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends BaseRepository<Order, Long> {}
