package com.vulinh.data.mapper;

import com.vulinh.data.dto.CustomerDTO;
import com.vulinh.data.dto.OrderDTO;
import com.vulinh.data.entity.Customer;
import com.vulinh.data.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

  CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

  CustomerDTO toCustomerDTO(Customer customer);

  @Mapping(target = "orders", ignore = true)
  CustomerDTO toCustomerDTOLazy(Customer customer);

  OrderDTO toOrderDTO(Order order);
}
