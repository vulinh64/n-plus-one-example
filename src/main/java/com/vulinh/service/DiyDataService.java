package com.vulinh.service;

import module java.base;

import com.vulinh.data.dto.CustomerDTO;
import com.vulinh.data.entity.Customer;
import com.vulinh.data.entity.Order;
import com.vulinh.data.entity.QOrder;
import com.vulinh.data.mapper.CustomerMapper;
import com.vulinh.data.repository.CustomerRepository;
import com.vulinh.data.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiyDataService {

  static final CustomerMapper CUSTOMER_MAPPER = CustomerMapper.INSTANCE;

  private final CustomerRepository customerRepository;
  private final OrderRepository orderRepository;

  public Page<CustomerDTO> getCustomersDiy(Pageable pageable) {
    var customers = customerRepository.findAll(pageable);

    var orders =
        customers.isEmpty()
            ? Collections.<Long, List<Order>>emptyMap()
            : orderRepository
                .findAll(QOrder.order.customer.id.in(customers.map(Customer::getId).getContent()))
                .stream()
                .collect(Collectors.groupingBy(a -> a.getCustomer().getId()));

    return customers.map(
        customer ->
            CUSTOMER_MAPPER
                .toCustomerDTOLazy(customer)
                .withOrders(
                    orders.getOrDefault(customer.getId(), Collections.emptyList()).stream()
                        .map(CUSTOMER_MAPPER::toOrderDTO)
                        .toList()));
  }
}
